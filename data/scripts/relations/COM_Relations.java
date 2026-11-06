package data.scripts.relations;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.FactionAPI;
import  data.scripts.COM_ModPlugin;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;

public class COM_Relations implements SectorGeneratorPlugin {
    @Override
    public static void generate(SectorAPI sector) {
        FactionAPI RHP = sector.getFaction("RHP"); // assigns a faction variable
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("RHP");
        RHP.setRelationship(Factions.LUDDIC_CHURCH, -0.5f);
	RHP.setRelationship(Factions.INDEPENDENT,-0.5f);
        RHP.setRelationship(Factions.LUDDIC_PATH, -0.5f);
        RHP.setRelationship(Factions.PERSEAN, -0.5f);
        RHP.setRelationship(Factions.PIRATES, 0.5f);
        RHP.setRelationship(Factions.PLAYER, -0.5f);
        RHP.setRelationship("sindrian_diktat", -0.5f);
        RHP.setRelationship("hegemony", -0.5f);
        RHP.setRelationship("tritachyon", -0.99f); 
        // mods
        RHP.setRelationship("hcok",-0.5f);    
        RHP.setRelationship("ironshell", -0.5f);       
        RHP.setRelationship("battlefleets_imperium", -0.5f); 
        RHP.setRelationship("sevencorp", -0.5f);	 
        RHP.setRelationship("xlu", -0.5f);	 
        RHP.setRelationship("GKSec", -0.5f);	 
        RHP.setRelationship("MVS", -0.5f);
        RHP.setRelationship("syndicate_asp", -0.5f);	   
        RHP.setRelationship("cmc", -0.5f);        
        RHP.setRelationship("SCY", -0.5f);     
        RHP.setRelationship("neutrinocorp", -0.5f);
        RHP.setRelationship("battlefleets_forcesofchaos", -0.5f);           
        RHP.setRelationship("xhanempire", -0.5f);	
        RHP.setRelationship("dassault_mikoyan", -0.5f);
        RHP.setRelationship("scalartech", -0.5f);     
        RHP.setRelationship("blackrock_driveyards", -0.5f);	   
        RHP.setRelationship("uaf", -0.5f);
        RHP.setRelationship("pn_colony", -0.5f);    
        RHP.setRelationship("junk_pirates", -0.55f);  
        RHP.setRelationship("ORA", -0.5f);          
        RHP.setRelationship("shadow_industry", -0.5f);	
        RHP.setRelationship("vic", -0.5f);     
        RHP.setRelationship("interstellarimperium", -0.5f);     
        RHP.setRelationship("prv", -0.5f);   
        RHP.setRelationship("new_galactic_order", -0.5f);	
        RHP.setRelationship("battlefleets_ork_pirates", -0.5f);
        RHP.setRelationship("diableavionics", -0.5f);      
        RHP.setRelationship("unitedpamed", -0.5f);     
        RHP.setRelationship("rb", -0.5f);    
        RHP.setRelationship("JYD", -0.5f);
	RHP.setRelationship("legionarry", -0.5f);      
        // RHP.setRelationship("Lte", 0.0f);   
        RHP.setRelationship("gmda", -0.5f);   
        // RHP.setRelationship("oculus", -0.25f);     
        // RHP.setRelationship("nomads", -0.25f); 
        // RHP.setRelationship("thulelegacy", -0.25f); 
        // RHP.setRelationship("infected", -0.99f); 
	RHP.setRelationship("fantasy_manufacturing", -0.5f);
	RHP.setRelationship("Goat_Aviation_Bureau", -0.5f);
	RHP.setRelationship("fob", -0.5f); 
	RHP.setRelationship("batavia", -0.5f); 

	
    }

}