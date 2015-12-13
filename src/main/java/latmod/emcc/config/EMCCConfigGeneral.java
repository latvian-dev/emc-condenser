package latmod.emcc.config;

import latmod.lib.config.*;
import latmod.lib.util.DoubleBounds;

public class EMCCConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	public static final ConfigEntryBool blacklist = new ConfigEntryBool("blacklist", true);
	public static final ConfigEntryDouble uu_block_enchant_power = new ConfigEntryDouble("uu_block_enchant_power", new DoubleBounds(3D, 0D, 100D));
	public static final ConfigEntryBool remove_no_emc_tooltip = new ConfigEntryBool("remove_no_emc_tooltip", true);
	public static final ConfigEntryBool force_vanilla_recipes = new ConfigEntryBool("force_vanilla_recipes", false).sync();
	public static final ConfigEntryBool force_vanilla_emc = new ConfigEntryBool("force_vanilla_emc", false).sync();
}