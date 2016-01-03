package latmod.emcc.config;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.emcc.emc.VanillaEMC;
import latmod.lib.config.*;

import java.io.File;

public class EMCCConfig
{
	public static VanillaEMC localEMCValues;
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("emcc", new File(FTBLib.folderConfig, "LatMod/EMC_Condenser/config.json"));
		configFile.configGroup.setName("EMCCondenser");
		configFile.add(new ConfigGroup("condenser").addAll(EMCCConfigCondenser.class, null, false));
		configFile.add(new ConfigGroup("enchanting").addAll(EMCCConfigEnchanting.class, null, false));
		configFile.add(new ConfigGroup("general").addAll(EMCCConfigGeneral.class, null, false));
		configFile.add(new ConfigGroup("tools").addAll(EMCCConfigTools.class, null, false));
		
		EMCCConfigCondenser.forced.addAll(EMCCConfigCondenser.Forced.class, null, false);
		EMCCConfigTools.enable.addAll(EMCCConfigTools.Enable.class, null, false);
		
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}