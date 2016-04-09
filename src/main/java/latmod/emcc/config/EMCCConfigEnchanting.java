package latmod.emcc.config;

import ftb.lib.api.config.ConfigEntryInt;

public class EMCCConfigEnchanting
{
	private static final ConfigEntryInt get(String s, int def)
	{
		ConfigEntryInt c = new ConfigEntryInt(s, def);
		c.setBounds(0, 50);
		return c;
	}
	
	public static final ConfigEntryInt fire = get("fire", 20); // Flame for bow 
	public static final ConfigEntryInt fortune = get("fortune", 25); // Looting for swords
	public static final ConfigEntryInt unbreaking = get("unbreaking", 20);
	public static final ConfigEntryInt silk_touch = get("silk_touch", 40);
	public static final ConfigEntryInt sharpness = get("sharpness", 15); // Efficiency for tools
	public static final ConfigEntryInt knockback = get("knockback", 15); // Punch for bow
	public static final ConfigEntryInt infinity = get("infinity", 50);
}