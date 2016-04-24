package latmod.emcc.config;

import ftb.lib.api.config.ConfigEntryBool;
import ftb.lib.api.config.ConfigEntryDouble;
import latmod.lib.annotations.NumberBounds;

public class EMCCConfigTools
{
	@NumberBounds(min = 0D)
	public static final ConfigEntryDouble tool_emc_per_damage = new ConfigEntryDouble("tool_emc_per_damage", 64D);
	
	@NumberBounds(min = 0D)
	public static final ConfigEntryDouble life_stone_1hp = new ConfigEntryDouble("life_stone_1hp", 24D);
	
	@NumberBounds(min = 0D)
	public static final ConfigEntryDouble life_stone_food = new ConfigEntryDouble("life_stone_food", 128D);
	
	@NumberBounds(min = 0D)
	public static final ConfigEntryDouble life_stone_extinguish = new ConfigEntryDouble("life_stone_extinguish", 64D);
	
	@NumberBounds(min = 0D)
	public static final ConfigEntryDouble black_hole_stone_item = new ConfigEntryDouble("black_hole_stone_item", 48D);
	
	@NumberBounds(min = 0D, max = 64D)
	public static final ConfigEntryDouble black_hole_stone_range = new ConfigEntryDouble("black_hole_stone_range", 4D);
	
	public static final ConfigEntryBool wrench = new ConfigEntryBool("wrench", true);
	public static final ConfigEntryBool battery = new ConfigEntryBool("battery", true);
	public static final ConfigEntryBool sword = new ConfigEntryBool("sword", true);
	public static final ConfigEntryBool bow = new ConfigEntryBool("bow", true);
	public static final ConfigEntryBool tools = new ConfigEntryBool("tools", true);
}