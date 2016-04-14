package latmod.emcc.config;

import ftb.lib.PrivacyLevel;
import ftb.lib.api.config.*;
import ftb.lib.api.tile.*;
import latmod.lib.annotations.*;
import latmod.lib.util.EnumEnabled;

public class EMCCConfigCondenser
{
	@Info("Longer delay - Less condenser updates")
	@NumberBounds(min = 0, max = Short.MAX_VALUE)
	public static final ConfigEntryInt sleep_delay = new ConfigEntryInt("sleep_delay", 10);
	
	@Info("How many items can be condensed every <sleepDelay> ticks")
	@NumberBounds(min = -1, max = 2048)
	public static final ConfigEntryInt limit_per_tick = new ConfigEntryInt("limit_per_tick", 8);
	
	@Info("Forced values that player can't change")
	public static final ConfigGroup forced = new ConfigGroup("forced");
	
	public static class Forced
	{
		public static final ConfigEntryEnum<InvMode> inv_mode = new ConfigEntryEnum<>("inv_mode", InvMode.VALUES, null, true);
		public static final ConfigEntryEnum<PrivacyLevel> security = new ConfigEntryEnum<>("security", PrivacyLevel.VALUES_3, null, true);
		public static final ConfigEntryEnum<RedstoneMode> redstone_control = new ConfigEntryEnum<>("redstone_control", RedstoneMode.VALUES, null, true);
		public static final ConfigEntryEnum<EnumEnabled> safe_mode = new ConfigEntryEnum<>("safe_mode", EnumEnabled.values(), null, true);
	}
}