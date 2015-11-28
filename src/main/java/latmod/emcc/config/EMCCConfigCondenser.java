package latmod.emcc.config;

import latmod.emcc.tile.SafeMode;
import latmod.ftbu.tile.*;
import latmod.ftbu.util.LMSecurityLevel;
import latmod.lib.config.*;
import latmod.lib.util.IntBounds;

public class EMCCConfigCondenser
{
	public static final ConfigGroup group = new ConfigGroup("condenser");
	public static final ConfigEntryInt sleepDelay = new ConfigEntryInt("sleepDelay", new IntBounds(10, 0, Short.MAX_VALUE)).setInfo("Longer delay - Less condenser updates");
	public static final ConfigEntryInt limitPerTick = new ConfigEntryInt("limitPerTick", new IntBounds(8, -1, 2048)).setInfo("How many items can be condensed every <sleepDelay> ticks");
	public static final ConfigEntryEnum<InvMode> forcedInvMode = new ConfigEntryEnum<InvMode>("forcedInvMode", InvMode.class, InvMode.VALUES, null, true);
	public static final ConfigEntryEnum<LMSecurityLevel> forcedSecurity = new ConfigEntryEnum<LMSecurityLevel>("forcedSecurity", LMSecurityLevel.class, LMSecurityLevel.VALUES_3, null, true);
	public static final ConfigEntryEnum<RedstoneMode> forcedRedstoneControl = new ConfigEntryEnum<RedstoneMode>("forcedRedstoneControl", RedstoneMode.class, RedstoneMode.VALUES, null, true);
	public static final ConfigEntryEnum<SafeMode> forcedSafeMode = new ConfigEntryEnum<SafeMode>("forcedSafeMode", SafeMode.class, SafeMode.VALUES, null, true);
}