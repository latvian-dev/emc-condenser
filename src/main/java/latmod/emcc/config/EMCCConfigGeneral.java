package latmod.emcc.config;

import ftb.lib.api.config.*;
import latmod.lib.annotations.*;

public class EMCCConfigGeneral
{
	public static final ConfigEntryBool blacklist = new ConfigEntryBool("blacklist", true);
	
	@NumberBounds(min = 0D, max = 100D)
	public static final ConfigEntryDouble uu_block_enchant_power = new ConfigEntryDouble("uu_block_enchant_power", 3D);
	
	public static final ConfigEntryBool remove_no_emc_tooltip = new ConfigEntryBool("remove_no_emc_tooltip", true);
	
	@Flags(Flags.SYNC)
	public static final ConfigEntryBool force_vanilla_emc = new ConfigEntryBool("force_vanilla_emc", false);
}