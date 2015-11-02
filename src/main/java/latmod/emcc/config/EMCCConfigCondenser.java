package latmod.emcc.config;

import latmod.emcc.tile.SafeMode;
import latmod.ftbu.tile.*;
import latmod.ftbu.util.LMSecurityLevel;
import latmod.lib.config.*;
import latmod.lib.util.IntBounds;

public class EMCCConfigCondenser
{
	public static final ConfigGroup group = new ConfigGroup("condenser");
	
	public static final ConfigEntryInt sleepDelay = new ConfigEntryInt("sleepDelay", new IntBounds(10, 0, Short.MAX_VALUE));
	public static final ConfigEntryInt limitPerTick = new ConfigEntryInt("limitPerTick", new IntBounds(8, -1, 2048));
	public static final ConfigEntryInt forcedInvMode = new ConfigEntryInt("forcedInvMode", new IntBounds(-1, -1, 3));
	public static final ConfigEntryInt forcedSecurity = new ConfigEntryInt("forcedSecurity", new IntBounds(-1, -1, 2));
	public static final ConfigEntryInt forcedRedstoneControl = new ConfigEntryInt("forcedRedstoneControl", new IntBounds(-1, -1, 2));
	public static final ConfigEntryInt forcedSafeMode = new ConfigEntryInt("forcedSafeMode", new IntBounds(-1, -1, 1));
	
	public static void load(ConfigFile f)
	{
		group.add(sleepDelay);
		group.add(limitPerTick);
		group.add(forcedInvMode);
		group.add(forcedSecurity);
		group.add(forcedRedstoneControl);
		group.add(forcedSafeMode);
		f.add(group);
		
		/*c.setComment("condenserSleepDelay",
				"Longer delay - Less condenser updates",
				"Min value - 0, instant condensing",
				"Max value - 32767, that's more than 1.5 days in Minecraft",
				"Default value - 10 (0.5 seconds)");
		
		c.setComment("condenserLimitPerTick",
				"How many items can be condensed every <condenserSleepDelay> ticks",
				"Max = 2048, Min = 1",
				"-1 = Condense all (first release version)");
		
		c.setComment("forcedInvMode",
				"-1 - User defined",
				"0 - Items can be go both ways",
				"1 - Items can only go in",
				"2 - Items can only go out",
				"3 - Automatization disabled");
		
		c.setComment("forcedSecurity",
				"-1 - User defined",
				"0 - Public",
				"1 - Private",
				"2 - Friends");
		
		c.setComment("forcedRedstoneControl",
				"-1 - User defined",
				"0 - No Redstone Control",
				"1 - Required High signal",
				"2 - Required Low signal");
		
		c.setComment("forcedSafeMode",
				"-1 - User defined",
				"0 - Safe Mode always off",
				"1 - Safe Mode always on");*/
	}
	
	public static InvMode forcedInvMode()
	{ return (forcedInvMode.get() == -1) ? null : InvMode.VALUES[forcedInvMode.get()]; }
	
	public static LMSecurityLevel forcedSecurity()
	{ return (forcedSecurity.get() == -1) ? null : LMSecurityLevel.VALUES_3[forcedInvMode.get()]; }
	
	public static RedstoneMode forcedRedstoneControl()
	{ return (forcedRedstoneControl.get() == -1) ? null : RedstoneMode.VALUES[forcedInvMode.get()]; }
	
	public static SafeMode forcedSafeMode()
	{ return (forcedSafeMode.get() == -1) ? null : SafeMode.VALUES[forcedInvMode.get()]; }
}