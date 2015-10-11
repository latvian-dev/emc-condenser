package latmod.emcc.config;

import latmod.lib.config.*;
import latmod.lib.util.IntBounds;

public class EMCCConfigEnchanting
{
	public static final ConfigGroup group = new ConfigGroup("enchanting");
	
	private static final ConfigEntryInt get(String s, int def)
	{ return new ConfigEntryInt(s, new IntBounds(def, 0, 50)); }
	
	public static final ConfigEntryInt fire = get("fire", 20); // Flame for bow 
	public static final ConfigEntryInt fortune = get("fortune", 25); // Looting for swords
	public static final ConfigEntryInt unbreaking = get("unbreaking", 20);
	public static final ConfigEntryInt silk_touch = get("silk_touch", 40);
	public static final ConfigEntryInt sharpness = get("sharpness", 15); // Efficiency for tools
	public static final ConfigEntryInt knockback = get("knockback", 15); // Punch for bow
	public static final ConfigEntryInt infinity = get("infinity", 50);
	
	public static void load(ConfigFile f)
	{
		/*c.setCategoryComment("Infusion in Anvil", "1 ~ 50 - required to infuse", "0 - disabled");
		fire = getLevel(c, "fire", 20, "Blaze rod");
		fortune = getLevel(c, "fortune", 25, "Gold Ingot [1 lvl]");
		unbreaking = getLevel(c, "unbreaking", 20, "Obsidian [1 lvl]");
		silk_touch = getLevel(c, "silk_touch", 40, "String");
		sharpness = getLevel(c, "sharpness", 15, "Iron Sword [1 lvl]");
		knockback = getLevel(c, "knockback", 15, "Piston [1 lvl]");
		infinity = getLevel(c, "infinity", 50, "Diamond");*/
		group.add(fire);
		group.add(fortune);
		group.add(unbreaking);
		group.add(silk_touch);
		group.add(sharpness);
		group.add(knockback);
		group.add(infinity);
		f.add(group);
	}
}