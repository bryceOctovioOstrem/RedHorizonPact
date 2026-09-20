package data.scripts.hullmods; // Declare the package path that matches your folder structure.

import com.fs.starfarer.api.campaign.CampaignFleetAPI; // Import fleet API to access the player's fleet in campaign.
import com.fs.starfarer.api.campaign.CargoAPI; // Import cargo API so we can add fuel to fleet cargo.
import com.fs.starfarer.api.campaign.rules.MemoryAPI; // Import MemoryAPI because fleet memory is a MemoryAPI, not a Map.
import com.fs.starfarer.api.combat.BaseHullMod; // Import BaseHullMod so we can implement a hullmod.
import com.fs.starfarer.api.fleet.FleetMemberAPI; // Import FleetMemberAPI to access per-ship campaign info.

public class StellarOmniFactory extends BaseHullMod { // Define the hullmod class.

    private static final float FUEL_PER_DAY = 50f; // Fuel produced per day per ship (normal).
    private static final float FUEL_PER_DAY_SMOD = 65f; // Fuel produced per day per ship (S-modded).

    private static final String HULLMOD_ID = "stellar_refinery"; // Must match the hullmod id in hull_mods.csv exactly.

    private static final String FLEET_ACCUM_PREFIX = "$" + HULLMOD_ID + "_dayAccum_"; // Prefix for per-ship accumulator keys stored in fleet memory.

    @Override
    public void advanceInCampaign(FleetMemberAPI member, float amount) { // Called repeatedly while time passes in the campaign.

        if (amount <= 0f) return; // If no time advanced, do nothing.

        if (member == null) return; // Safety: if member is null, do nothing.

        if (member.getFleetData() == null) return; // Safety: if the ship isn't in a fleet, do nothing.

        if (member.isMothballed()) return; // Requirement: do not produce if this ship is mothballed.

        CampaignFleetAPI fleet = member.getFleetData().getFleet(); // Get the fleet containing this ship.

        if (fleet == null) return; // Safety: if fleet is null, do nothing.

        if (!fleet.isPlayerFleet()) return; // Only let the player's fleet produce fuel (prevents NPC generation).

        if (fleet.isInHyperspace()) return; // Requirement: do not produce while the fleet is in hyperspace.

        CargoAPI cargo = fleet.getCargo(); // Get the fleet's cargo (fuel is stored here).

        if (cargo == null) return; // Safety: if cargo is missing, do nothing.

        String shipId = member.getId(); // Get this fleet member's unique id string.

        if (shipId == null) return; // Safety: if id is missing (rare), do nothing.

        String key = FLEET_ACCUM_PREFIX + shipId; // Build a unique memory key for this specific ship's day accumulator.

        MemoryAPI mem = fleet.getMemoryWithoutUpdate(); // Get the fleet's persistent memory (this is a MemoryAPI).

        if (mem == null) return; // Safety: if memory is missing, do nothing.

        float accumDays = 0f; // Default: no stored fractional days.

        if (mem.contains(key)) { // If we have a stored accumulator for this ship...
            Object v = mem.get(key); // Read the stored value object.
            if (v instanceof Float) accumDays = (Float) v; // Use it directly if it's a Float.
            else if (v instanceof Number) accumDays = ((Number) v).floatValue(); // Or safely convert if it's another Number type.
        } // End accumulator load.

        accumDays += amount; // Add elapsed time (in days) to this ship's accumulator.

        boolean isSMod = false; // Default: assume not S-modded.

        if (member.getVariant() != null) { // Safety: only check the variant if it exists.
            isSMod = member.getVariant().getSMods() != null // Ensure the S-mod set/list exists...
                    && member.getVariant().getSMods().contains(HULLMOD_ID); // ...and contains this hullmod id.
        } // End S-mod check.

        float perDay = isSMod ? FUEL_PER_DAY_SMOD : FUEL_PER_DAY; // Choose daily production rate.

        int wholeDays = (int) Math.floor(accumDays); // Convert accumulated fractional days into whole days we can pay out.

        if (wholeDays > 0) { // Only pay out if at least one full day has passed.
            float fuelToAdd = perDay * (float) wholeDays; // Compute fuel owed for those full days for THIS ship.
            cargo.addFuel(fuelToAdd); // Add that fuel to the player's fleet cargo.
            accumDays -= (float) wholeDays; // Subtract the paid whole days, leaving only the fractional remainder.
        } // End payout.

        mem.set(key, accumDays); // Store the fractional remainder back into fleet memory under this ship's key.
    } // End advanceInCampaign.

} // End class.
