package data.scripts.hullmods;

import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class com_SSF extends BaseHullMod {

    private static final float SUPPLY_PER_DAY = 34f;
    private static final float SUPPLY_PER_DAY_SMOD = 41f;

    // Must match hull_mods.csv id
    private static final String HULLMOD_ID = "com_stellar_supply";

    // Fleet memory key prefix (per-ship accumulator stored on the fleet)
    private static final String ACCUM_PREFIX = "$" + HULLMOD_ID + "_dayAccum_";

    @Override
    public void advanceInCampaign(FleetMemberAPI member, float amount) {
        if (amount <= 0f) return;
        if (member == null || member.getFleetData() == null) return;
        if (member.isMothballed()) return;

        CampaignFleetAPI fleet = member.getFleetData().getFleet();
        if (fleet == null) return;

        if (!fleet.isPlayerFleet()) return;

        // Only produce while not in hyperspace:
        if (fleet.isInHyperspace()) return;

        CargoAPI cargo = fleet.getCargo();
        if (cargo == null) return;

        /*// STOP production if supply storage is overfull (ABOVE capacity, not AT capacity)
        float currentFuel = cargo.getFuel();
        float maxFuel = cargo.getMaxFuel();
        if (currentFuel > maxFuel) return;*/

        // Determine S-mod state using the correct hullmod id
        boolean isSMod = member.getVariant() != null
                && member.getVariant().getSMods() != null
                && member.getVariant().getSMods().contains(HULLMOD_ID);

        float perDay = isSMod ? SUPPLY_PER_DAY_SMOD : SUPPLY_PER_DAY;

        // Use fleet memory (MemoryAPI) to store fractional-day progress per ship
        MemoryAPI mem = fleet.getMemoryWithoutUpdate();
        if (mem == null) return;

        String shipId = member.getId();
        if (shipId == null) return;

        String key = ACCUM_PREFIX + shipId;

        float accumDays = 0f;
        if (mem.contains(key)) {
            Object v = mem.get(key);
            if (v instanceof Float) accumDays = (Float) v;
            else if (v instanceof Number) accumDays = ((Number) v).floatValue();
        }

        accumDays += amount;

        int wholeDays = (int) Math.floor(accumDays);
        if (wholeDays > 0) {
            cargo.addFuel(perDay * (float) wholeDays);
            accumDays -= (float) wholeDays;
        }

        mem.set(key, accumDays);
    }
}