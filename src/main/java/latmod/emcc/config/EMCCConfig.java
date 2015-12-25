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
		configFile = new ConfigFile("emcc", new File(FTBLib.folderConfig, "EMC_Condenser/config.json"));
		configFile.configGroup.setName("EMCCondenser");
		configFile.add(new ConfigGroup("condenser").addAll(EMCCConfigCondenser.class));
		configFile.add(new ConfigGroup("enchanting").addAll(EMCCConfigEnchanting.class));
		configFile.add(new ConfigGroup("general").addAll(EMCCConfigGeneral.class));
		configFile.add(new ConfigGroup("tools").addAll(EMCCConfigTools.class));
		
		EMCCConfigCondenser.forced.addAll(EMCCConfigCondenser.Forced.class);
		EMCCConfigTools.enable.addAll(EMCCConfigTools.Enable.class);
		
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}