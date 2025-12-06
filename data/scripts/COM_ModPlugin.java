package data.scripts;
import java.awt.Color;
import java.util.List;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
//import data.strings.descriptions;
import data.scripts.relations.COM_Relations;
//import data.scripts.campaign.econ.DR_industries;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.InstallableItemEffect;
import com.fs.starfarer.api.impl.campaign.econ.impl.ItemEffectsRepo;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.util.Misc;

public class COM_ModPlugin extends BaseModPlugin {

    @Override
    public void onNewGame() {
       ZinovievGen(); // star ststem generator
       MorozovGen(); 
       ZinovievIIGen();
       Zinoviev_STATION();
      ChunqiaoGen();
      DniproviaGen();
      BinhdoGen();
		
   }
        private void ZinovievGen() {
        StarSystemAPI system = Global.getSector().createStarSystem("zinoviev");
        PlanetAPI star = system.initStar("Zinoviev", "star_red_supergiant", 
        700,
        -1000,// x location
        -2000, 
        250);

    system.addAsteroidBelt(star, 200, 
    5000f, 
    4000f, 
    1000, 
    250, 
    Terrain.ASTEROID_BELT, 
    "Astroid Belt");

	system.addRingBand(star, "misc", "rings_asteroids0",
    4000f, // width of the area of pattern
    0, 
    Color.red, // sets the color of somthing
    4000f, // png zoom
    5000f, // radius
    226f, 
    null, 
    null);

    SectorEntityToken com_nebula = Misc.addNebulaFromPNG("data/campaign/terrain/com_nebula.png",
				  0, 0, 
				  system, 
				  "terrain", "nebula", 
				  4, 4, StarAge.AVERAGE); 

        //system.addAsteroidBelt(star, 100, 10000f, 250f, 1000, 250, Terrain.ASTEROID_BELT, "Astroid Belt");
	//system.addRingBand(star, "misc", "rings_asteroids0", 256f, 0, Color.white, 256f, 10000f, 226f, null, null);
        SectorEntityToken relay = system.addCustomEntity("Zinoviev_relay",null, "comm_relay_makeshift","RHP");
        relay.setCircularOrbitPointingDown(star, 230, 2500, 265f);
         JumpPointAPI jumpPoint = Global.getFactory().createJumpPoint("jumpOne", " Jupitor Jump-point"); // add new jump point
		//jumpPoint.setCircularOrbit( system.getEntityById(star), 120, 9000, 200);
		//system.addEntity(jumpPoint);
        system.autogenerateHyperspaceJumpPoints(false, true);
        system.updateAllOrbits();
     }

    private void MorozovGen() {
        StarSystemAPI system = Global.getSector().getStarSystem("zinoviev");
        SectorEntityToken star = system.getStar();
        PlanetAPI planet = system.addPlanet("Morozov", star, "Morozov", "tundra", 
        0, 
        100, //world sprite size
        2800,// orbit distance
        50); //rotation speed
        

                MarketAPI market = Global.getFactory().createMarket( // function to initalize market
                "Morozov_market", //market id
                planet.getName(), //market display name, usually the planet's name
                7 //market size
        );
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        planet.setMarket(market); // sets planet market
        market.setPrimaryEntity(planet); // attatches market to planet
        planet.setFaction("RHP"); // sets what faction the planet belongs to 
        market.setFactionId("RHP"); 
        market.setPlanetConditionMarketOnly(false); //We are going to turn this into a proper colony and not just a "surface only".
        market.addIndustry(Industries.POPULATION);
        market.addCondition(Conditions.POPULATION_7);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        
        market.addCondition(Conditions.COLD);
        market.addCondition(Conditions.ORE_ULTRARICH);
        market.addCondition(Conditions.RARE_ORE_ULTRARICH);
        market.addCondition(Conditions.HABITABLE);
        market.addCondition(Conditions.FARMLAND_POOR);
        market.addCondition(Conditions.POLLUTION);
        market.addCondition(Conditions.INDUSTRIAL_POLITY);
        market.addCondition(Conditions.ORGANIZED_CRIME);
        market.addCondition(Conditions.METEOR_IMPACTS);

        market.addIndustry(Industries.HIGHCOMMAND);
        //market.addIndustry(Industries.HEAVYBATTERIES); //GroundDefenses
        market.addIndustry(Industries.FUELPROD);
        market.addIndustry(Industries.ORBITALWORKS);
        market.addIndustry(Industries.LIGHTINDUSTRY);
        market.addIndustry(Industries.SPACEPORT);

        market.addIndustry(Industries.STARFORTRESS_HIGH);


        market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.BETA_CORE);
		market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.GAMMA_CORE);
		//market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.BETA_CORE);
		market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.BETA_CORE);

         market.getTariff().modifyFlat("generator", 1.0f);
        //planet.setMarket(market);
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        globalEconomy.addMarket(
                market, //The market to add obviously!
                true //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );
        //planet.setCustomDescriptionId("opertunidad"); // adds planet description
		//planet.setInteractionImage("illustrations", "Maistre_planet");//adds illustration
        system.updateAllOrbits();
        planet.setCustomDescriptionId("com_Morozov_desc"); // adds planet description
    }
    public void factionRelations() {
        COM_Relations.generate(Global.getSector());

    }   

        private void ZinovievIIGen() {
		//There's only one StarSector, so it's accessed this way:
        StarSystemAPI system = Global.getSector().getStarSystem("zinoviev");
        SectorEntityToken star = system.getStar();
		//There are many star systems, though, and they are retrieved using their id, which happens to often be their name.
        PlanetAPI planet = system.addPlanet("ZinovievII", star, "ZinovievII", "gas_giant", 240, 120, 8000, 120);	
        MarketAPI market = Global.getFactory().createMarket(
			"Zinoviev_market", //market id
			planet.getName(), //market display name, usually the planet's name
			0 //market size
		);
        market.addCondition(Conditions.VOLATILES_DIFFUSE);
		
		
	
		Misc.initConditionMarket(planet);
				
        system.updateAllOrbits();
    }
    private void Zinoviev_STATION() {
        StarSystemAPI system = Global.getSector().getStarSystem("zinoviev");
        SectorEntityToken star = system.getStar();
		SectorEntityToken ZinovievStation = system.addCustomEntity("Zinoviev_station", "Peoples Liberated  Station of the Zinoviev System ", "station_mining00", "RHP");
		//ZinovievStation.setCustomDescriptionId("ZinovievStation");
		//ZinovievStation.setInteractionImage("illustrations", "Louis_lab");
		ZinovievStation.setCircularOrbitWithSpin(star, 220, 5000, 160, 3, 5);

        ZinovievStation.setFaction("RHP");
        MarketAPI market = Global.getFactory().createMarket(
        "ZinovievStation", //market id
        ZinovievStation.getName(), //market display name, usually the planet's name
                6 //market size
        );
        ZinovievStation.setMarket(market);
         market.setPlanetConditionMarketOnly(false); //We are going to turn this into a proper colony and not just a "surface only".
        market.setFactionId("RHP");
        market.addIndustry(Industries.POPULATION);
        market.addCondition(Conditions.POPULATION_6);
        market.setSize(6);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        market.setPrimaryEntity(ZinovievStation);
        market.addCondition(Conditions.ORE_ULTRARICH);
        market.addCondition(Conditions.RARE_ORE_ULTRARICH);

        // industries
        market.addIndustry(Industries.MEGAPORT);
        market.addIndustry(Industries.MILITARYBASE);
        market.addIndustry(Industries.HEAVYBATTERIES);
        market.addIndustry(Industries.MINING);
        market.addIndustry(Industries.HEAVYINDUSTRY);
        market.addIndustry(Industries.REFINING);
		market.addIndustry(Industries.BATTLESTATION);
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);


        market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.BATTLESTATION).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.MINING).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.BETA_CORE);
		market.getIndustry(Industries.REFINING).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.GAMMA_CORE);

        ZinovievStation.setCustomDescriptionId("com_station_desc");

        market.getTariff().modifyFlat("generator", 1.0f);
        //planet.setMarket(market);
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        globalEconomy.addMarket(
                market, //The market to add obviously!
                true //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );
        system.updateAllOrbits();
    }
    private void ChunqiaoGen() {
        StarSystemAPI system = Global.getSector().createStarSystem("Chunqiao");
        PlanetAPI star = system.initStar("Chunqiao", "star_red_dwarf", 
        100,
        6100,// x location
        -3000,// y location
        250);
        SectorEntityToken relay = system.addCustomEntity("Chunqiao_relay",null, "comm_relay_makeshift","RHP");
        relay.setCircularOrbitPointingDown(star, 230, 2500, 265f);
         JumpPointAPI jumpPoint = Global.getFactory().createJumpPoint("jumpOne", " Jupitor Jump-point"); // add new jump point
		//jumpPoint.setCircularOrbit( system.getEntityById(star), 120, 9000, 200);
		//system.addEntity(jumpPoint);
        system.autogenerateHyperspaceJumpPoints(true, true);
        StarSystemGenerator.addSystemwideNebula(system, StarAge.YOUNG);
        system.updateAllOrbits();
     }

    private void DniproviaGen() {
        StarSystemAPI system = Global.getSector().getStarSystem("Chunqiao");
        SectorEntityToken star = system.getStar();
        PlanetAPI planet = system.addPlanet("Dniprovia", star, "Dniprovia", "tundra", 
        0, 
        80, //world sprite size
        3000,// orbit distance
        150); //rotation speed
        

                MarketAPI market = Global.getFactory().createMarket( // function to initalize market
                "Dniprovia_I_market", //market id
                planet.getName(), //market display name, usually the planet's name
                5 //market size
        );
        planet.setCustomDescriptionId("com_Dniprovia_desc"); // adds planet description
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        planet.setMarket(market); // sets planet market
        market.setPrimaryEntity(planet); // attatches market to planet
        planet.setFaction("RHP"); // sets what faction the planet belongs to 
        market.setFactionId("RHP"); 
        market.setPlanetConditionMarketOnly(false); //We are going to turn this into a proper colony and not just a "surface only".
        market.addIndustry(Industries.POPULATION);
        market.addCondition(Conditions.POPULATION_5);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        
        market.addCondition(Conditions.COLD);
        market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
        market.addCondition(Conditions.ORE_SPARSE);
        market.addCondition(Conditions.ORGANICS_COMMON);
        market.addCondition(Conditions.RARE_ORE_RICH);
        market.addCondition(Conditions.POLLUTION);
        market.addCondition(Conditions.FRONTIER);
        market.addCondition(Conditions.HABITABLE);
        

        market.addIndustry(Industries.MILITARYBASE);
        market.addIndustry(Industries.GROUNDDEFENSES); //GroundDefenses
        market.addIndustry(Industries.MINING);
        market.addIndustry(Industries.HEAVYINDUSTRY);
        market.addIndustry(Industries.SPACEPORT);

        market.addIndustry(Industries.BATTLESTATION_HIGH);


        market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.GROUNDDEFENSES).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.MINING).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.BETA_CORE);
		market.getIndustry(Industries.BATTLESTATION_HIGH).setAICoreId(Commodities.BETA_CORE);
		//market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.GAMMA_CORE);

         market.getTariff().modifyFlat("generator", 1.0f);
        //planet.setMarket(market);
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        globalEconomy.addMarket(
                market, //The market to add obviously!
                true //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );
        //planet.setCustomDescriptionId("opertunidad"); // adds planet description
		//planet.setInteractionImage("illustrations", "Maistre_planet");//adds illustration
        system.updateAllOrbits();

    }

    private void BinhdoGen() {
        StarSystemAPI system = Global.getSector().getStarSystem("Chunqiao");
        SectorEntityToken star = system.getStar();
        PlanetAPI planet = system.addPlanet("Binhdo", star, "Binhdo", "jungle", 
        0, 
        80, //world sprite size
        1000,// orbit distance
        50); //rotation speed
        

                MarketAPI market = Global.getFactory().createMarket( // function to initalize market
                "Binhdo_market", //market id
                planet.getName(), //market display name, usually the planet's name
                3 //market size
        );
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        planet.setMarket(market); // sets planet market
        planet.setCustomDescriptionId("com_binhdo_desc"); // adds planet description
        market.setPrimaryEntity(planet); // attatches market to planet
        planet.setFaction("RHP"); // sets what faction the planet belongs to 
        market.setFactionId("RHP"); 
        market.setPlanetConditionMarketOnly(false); //We are going to turn this into a proper colony and not just a "surface only".
        market.addIndustry(Industries.POPULATION);
        market.addCondition(Conditions.POPULATION_3);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        
        market.addCondition(Conditions.HOT);
        market.addCondition(Conditions.FARMLAND_POOR);
        market.addCondition(Conditions.ORE_SPARSE);
        market.addCondition(Conditions.RARE_ORE_SPARSE);
        market.addCondition(Conditions.POLLUTION);
        market.addCondition(Conditions.EXTREME_WEATHER);
        market.addCondition(Conditions.HABITABLE);
        

        market.addIndustry(Industries.MILITARYBASE);
        market.addIndustry(Industries.GROUNDDEFENSES); //GroundDefenses
        market.addIndustry(Industries.FARMING);
        market.addIndustry(Industries.SPACEPORT);

        market.addIndustry(Industries.ORBITALSTATION_HIGH);


        market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.GROUNDDEFENSES).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.FARMING).setAICoreId(Commodities.GAMMA_CORE);
        //market.getIndustry(Industries.ORGANICS_COMMONG).setAICoreId(Commodities.GAMMA_CORE);
		market.getIndustry(Industries.ORBITALSTATION_HIGH).setAICoreId(Commodities.BETA_CORE);
		//market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.GAMMA_CORE);
        market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.GAMMA_CORE);

         market.getTariff().modifyFlat("generator", 1.0f);
        //planet.setMarket(market);
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
        globalEconomy.addMarket(
                market, //The market to add obviously!
                true //The "withJunkAndChatter" flag. It will add space debris in orbit and radio chatter sound effects.*
        );
        //planet.setCustomDescriptionId("opertunidad"); // adds planet description
		//planet.setInteractionImage("illustrations", "Maistre_planet");//adds illustration
        system.updateAllOrbits();
    }
        public void factionRelations() {
        COM_Relations.generate(Global.getSector());

    }
    
}