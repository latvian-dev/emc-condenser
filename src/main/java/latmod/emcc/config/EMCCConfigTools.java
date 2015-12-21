package latmod.emcc.config;

import latmod.lib.config.*;
import latmod.lib.util.DoubleBounds;

public class EMCCConfigTools
{
	public static final ConfigGroup enable = new ConfigGroup("enable");
	public static final ConfigEntryDouble tool_emc_per_damage = new ConfigEntryDouble("tool_emc_per_damage", new DoubleBounds(64D, 0D, Double.POSITIVE_INFINITY));
	public static final ConfigEntryDouble life_stone_1hp = new ConfigEntryDouble("life_stone_1hp", new DoubleBounds(24D, 0D, Double.POSITIVE_INFINITY));
	public static final ConfigEntryDouble life_stone_food = new ConfigEntryDouble("life_stone_food", new DoubleBounds(128D, 0D, Double.POSITIVE_INFINITY));
	public static final ConfigEntryDouble life_stone_extinguish = new ConfigEntryDouble("life_stone_extinguish", new DoubleBounds(64D, 0D, Double.POSITIVE_INFINITY));
	public static final ConfigEntryDouble black_hole_stone_item = new ConfigEntryDouble("black_hole_stone_item", new DoubleBounds(48D, 0D, Double.POSITIVE_INFINITY));
	public static final ConfigEntryDouble black_hole_stone_range = new ConfigEntryDouble("black_hole_stone_range", new DoubleBounds(4D, 0D, 64D));
	
	public static class Enable
	{
		public static final ConfigEntryBool wrench = new ConfigEntryBool("wrench", true);
		public static final ConfigEntryBool battery = new ConfigEntryBool("battery", true);
		public static final ConfigEntryBool sword = new ConfigEntryBool("sword", true);
		public static final ConfigEntryBool bow = new ConfigEntryBool("bow", true);
		public static final ConfigEntryBool tools = new ConfigEntryBool("tools", true);
	}
}