package latmod.emcc.config;

import ftb.lib.api.config.ConfigSyncRegistry;
import latmod.lib.config.*;
import latmod.lib.util.*;

public class EMCCConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	
	public static final ConfigEntryBool blacklist = new ConfigEntryBool("blacklist", true);
	public static final ConfigEntryFloat uuBlockEnchantPower = new ConfigEntryFloat("uuBlockEnchantPower", new FloatBounds(3F, 0F, 100F));
	public static final ConfigEntryBool removeNoEMCTooltip = new ConfigEntryBool("removeNoEMCTooltip", true);
	public static final ConfigEntryInt ticksToInfuse = new ConfigEntryInt("ticksToInfuse", new IntBounds(400, -1, 32767));
	public static final ConfigEntryBool forceVanillaRecipes = new ConfigEntryBool("forceVanillaRecipes", false);
	public static final ConfigEntryBool forceVanillaEMC = new ConfigEntryBool("forceVanillaEMC", false);
	
	public static void load(ConfigFile f)
	{
		group.add(blacklist);
		group.add(uuBlockEnchantPower);
		group.add(removeNoEMCTooltip);
		group.add(ticksToInfuse);
		group.add(forceVanillaRecipes);
		group.add(forceVanillaEMC);
		f.add(group);
		
		ConfigSyncRegistry.add(forceVanillaRecipes);
		ConfigSyncRegistry.add(forceVanillaEMC);
	}
}