package latmod.emcc.config;

import ftb.lib.api.config.ConfigEntryInt;
import latmod.lib.annotations.Info;
import latmod.lib.annotations.NumberBounds;

public class EMCCConfigCondenser
{
	@Info("Longer delay - Less condenser updates")
	@NumberBounds(min = 0, max = Short.MAX_VALUE)
	public static final ConfigEntryInt sleep_delay = new ConfigEntryInt("sleep_delay", 10);
	
	@Info("How many items can be condensed every <sleep_delay> ticks")
	@NumberBounds(min = -1, max = 2048)
	public static final ConfigEntryInt limit_per_tick = new ConfigEntryInt("limit_per_tick", 8);
}