package latmod.emcc.config;

import ftb.lib.api.config.ConfigEntryBool;
import ftb.lib.api.config.ConfigEntryDouble;
import latmod.lib.annotations.NumberBounds;

public class EMCCConfigGeneral
{
	public static final ConfigEntryBool blacklist = new ConfigEntryBool("blacklist", true);
	
	@NumberBounds(min = 0D, max = 100D)
	public static final ConfigEntryDouble uu_block_enchant_power = new ConfigEntryDouble("uu_block_enchant_power", 3D);
}