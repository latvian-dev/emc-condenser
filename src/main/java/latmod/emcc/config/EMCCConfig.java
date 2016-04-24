package latmod.emcc.config;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigFile;
import ftb.lib.api.config.ConfigRegistry;

import java.io.File;

public class EMCCConfig
{
	public static final ConfigFile configFile = new ConfigFile("emcc");
	
	public static void load()
	{
		configFile.setFile(new File(FTBLib.folderConfig, "LatMod/EMC_Condenser/config.json"));
		configFile.setDisplayName("EMCCondenser");
		configFile.addGroup("general", EMCCConfigGeneral.class);
		configFile.addGroup("condenser", EMCCConfigCondenser.class);
		configFile.addGroup("condenser_forced", EMCCConfigForced.class).setInfo(new String[] {"Forced values that player can't change"});
		configFile.addGroup("enchanting", EMCCConfigEnchanting.class);
		configFile.addGroup("tools", EMCCConfigTools.class);
		
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}