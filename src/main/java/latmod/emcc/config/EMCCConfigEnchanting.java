package latmod.emcc.config;

import latmod.lib.config.ConfigEntryInt;
import latmod.lib.util.IntBounds;

public class EMCCConfigEnchanting
{
	private static final ConfigEntryInt get(String s, int def)
	{ return new ConfigEntryInt(s, new IntBounds(def, 0, 50)); }
	
	public static final ConfigEntryInt fire = get("fire", 20); // Flame for bow 
	public static final ConfigEntryInt fortune = get("fortune", 25); // Looting for swords
	public static final ConfigEntryInt unbreaking = get("unbreaking", 20);
	public static final ConfigEntryInt silk_touch = get("silk_touch", 40);
	public static final ConfigEntryInt sharpness = get("sharpness", 15); // Efficiency for tools
	public static final ConfigEntryInt knockback = get("knockback", 15); // Punch for bow
	public static final ConfigEntryInt infinity = get("infinity", 50);
}