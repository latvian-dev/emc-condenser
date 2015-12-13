package latmod.emcc.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.emcc.emc.VanillaEMC;
import latmod.lib.config.ConfigFile;

public class EMCCConfig
{
	public static VanillaEMC localEMCValues;
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("emcc", new File(FTBLib.folderConfig, "EMC_Condenser/config.json"));
		configFile.configGroup.setName("EMCCondenser");
		configFile.add(EMCCConfigGeneral.group.addAll(EMCCConfigGeneral.class));
		configFile.add(EMCCConfigCondenser.group.addAll(EMCCConfigCondenser.class));
		configFile.add(EMCCConfigTools.group.addAll(EMCCConfigTools.class));
		configFile.add(EMCCConfigEnchanting.group.addAll(EMCCConfigEnchanting.class));
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}