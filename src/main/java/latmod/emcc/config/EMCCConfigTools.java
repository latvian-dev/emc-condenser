package latmod.emcc.config;

import latmod.lib.config.*;
import latmod.lib.util.FloatBounds;

public class EMCCConfigTools
{
	public static final ConfigGroup group = new ConfigGroup("tools");
	public static final ConfigEntryBool enableWrench = new ConfigEntryBool("enableWrench", true);
	public static final ConfigEntryBool enableBattery = new ConfigEntryBool("enableBattery", true);
	public static final ConfigEntryBool enableSword = new ConfigEntryBool("enableSword", true);
	public static final ConfigEntryBool enableBow = new ConfigEntryBool("enableBow", true);
	public static final ConfigEntryBool enableTools = new ConfigEntryBool("enableTools", true);
	public static final ConfigEntryFloat toolEmcPerDamage = new ConfigEntryFloat("toolEmcPerDamage", new FloatBounds(64F));
	public static final ConfigEntryFloat lifeStone_1hp = new ConfigEntryFloat("lifeStone_1hp", new FloatBounds(24F));
	public static final ConfigEntryFloat lifeStone_food = new ConfigEntryFloat("lifeStone_food", new FloatBounds(128F));
	public static final ConfigEntryFloat lifeStone_extinguish = new ConfigEntryFloat("lifeStone_extinguish", new FloatBounds(64F));
	public static final ConfigEntryFloat blackHoleStone_item = new ConfigEntryFloat("blackHoleStone_item", new FloatBounds(48F));
	public static final ConfigEntryFloat blackHoleStone_range = new ConfigEntryFloat("blackHoleStone_range", new FloatBounds(4F));
}