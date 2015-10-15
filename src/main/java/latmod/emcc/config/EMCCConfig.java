package latmod.emcc.config;
import java.io.File;

import latmod.emcc.EMCC;
import latmod.emcc.emc.VanillaEMC;
import latmod.ftbu.api.config.ConfigListRegistry;
import latmod.ftbu.util.LatCoreMC;
import latmod.lib.config.ConfigFile;

public class EMCCConfig
{
	public static VanillaEMC localEMCValues;
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile(EMCC.mod.modID, new File(LatCoreMC.configFolder, "/LatMod/EMC_Condenser.json"), true);
		EMCCConfigGeneral.load(configFile);
		EMCCConfigCondenser.load(configFile);
		EMCCConfigTools.load(configFile);
		EMCCConfigEnchanting.load(configFile);
		ConfigListRegistry.add(configFile);
		configFile.load();
	}
	
	/*
	public static void readVanillaEMC()
	{
		byte[] b1 = tag.getByteArray("B");
		EMCHandler.instance().vanillaEMC.fromBytes(b1);
	}*/
	
	/*public static void sendVanillaEMC()
	{
		if(EMCCConfig.General.forceVanillaEMC || !EMCHandler.hasEE3()) try
		{ byte[] b1 = EMCHandler.instance().vanillaEMC.toBytes(); tag.setByteArray("B", b1); }
		catch(Exception e) { e.printStackTrace(); }
	}*/
}