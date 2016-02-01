package latmod.emcc.config;

import ftb.lib.LMSecurityLevel;
import ftb.lib.api.tile.*;
import latmod.lib.config.*;
import latmod.lib.util.*;

public class EMCCConfigCondenser
{
	public static final ConfigEntryInt sleep_delay = new ConfigEntryInt("sleep_delay", new IntBounds(10, 0, Short.MAX_VALUE)).setInfo("Longer delay - Less condenser updates");
	public static final ConfigEntryInt limit_per_tick = new ConfigEntryInt("limit_per_tick", new IntBounds(8, -1, 2048)).setInfo("How many items can be condensed every <sleepDelay> ticks");
	public static final ConfigGroup forced = new ConfigGroup("forced").setInfo("Forced values that player can't change");
	
	public static class Forced
	{
		public static final ConfigEntryEnum<InvMode> inv_mode = new ConfigEntryEnum<>("inv_mode", InvMode.class, InvMode.VALUES, null, true);
		public static final ConfigEntryEnum<LMSecurityLevel> security = new ConfigEntryEnum<>("security", LMSecurityLevel.class, LMSecurityLevel.VALUES_3, null, true);
		public static final ConfigEntryEnum<RedstoneMode> redstone_control = new ConfigEntryEnum<>("redstone_control", RedstoneMode.class, RedstoneMode.VALUES, null, true);
		public static final ConfigEntryEnum<EnumEnabled> safe_mode = new ConfigEntryEnum<>("safe_mode", EnumEnabled.class, EnumEnabled.VALUES, null, true);
	}
}