package latmod.emcc.config;

import ftb.lib.FTBLib;
import ftb.lib.api.config.*;
import latmod.emcc.emc.VanillaEMC;

import java.io.File;

public class EMCCConfig
{
	public static final ConfigFile configFile = new ConfigFile("emcc");
	public static VanillaEMC localEMCValues;
	
	public static void load()
	{
		configFile.setFile(new File(FTBLib.folderConfig, "LatMod/EMC_Condenser/config.json"));
		configFile.setDisplayName("EMCCondenser");
		configFile.addGroup("general", EMCCConfigGeneral.class);
		configFile.addGroup("condenser", EMCCConfigCondenser.class);
		configFile.addGroup("condenser_forced", EMCCConfigCondenser.Forced.class);
		configFile.addGroup("enchanting", EMCCConfigEnchanting.class);
		configFile.addGroup("tools", EMCCConfigTools.class);
		configFile.addGroup("tools_enabled", EMCCConfigTools.Enabled.class);
		
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}