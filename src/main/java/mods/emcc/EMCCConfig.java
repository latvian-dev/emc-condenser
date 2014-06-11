package mods.emcc;
import cpw.mods.fml.common.event.*;
import mods.emcc.tile.TileCondenser;
import net.minecraftforge.common.*;
import latmod.core.*;

public class EMCCConfig
{
	public static Configuration config;
	
	public static int isCondenserIInventory;
	public static int condenserSleepDelay;
	public static int recipeDifficulty;
	public static boolean enableSecurity;
	public static boolean enableRedstoneControl;
	public static boolean enableSafeMode;
	public static boolean infuseMiniumStar;
	public static boolean infuseUUBlock;
	public static int condenserLimitPerTick;
	public static boolean enableBattery;

	public static final void load(FMLPreInitializationEvent e)
	{
		config = LatCore.loadConfig(e, "/LatMod/EMC_Condenser.cfg");

		String CAT_GENERAL = "general";
		
		isCondenserIInventory = getInt(CAT_GENERAL, "isCondenserIInventory", 3, 0, 3,
				"0 - Automatization disabled",
				"1 - Items can be only pumped out",
				"2 - Items can be only pumped in",
				"3 - Items can be pumped in both ways");
		
		condenserSleepDelay = getInt(CAT_GENERAL, "condenserSleepDelay", 4, 0, 32767,
				"Longer delay - Less lag. I hope.",
				"Min value - 0, instant condensing",
				"Max value - 32767, that's more than 1.5 days in Minecraft");
		
		recipeDifficulty = getInt(CAT_GENERAL, "recipeDifficulty", 0, 0, 2,
				"This changed the item used in EMC Condenser's crafting recipe",
				"0 - UnUnSeptium Block",
				"1 - Nether Star",
				"2 - Minium Star");
		
		enableSecurity = getBool(CAT_GENERAL, "enableSecurity", true);
		enableRedstoneControl = getBool(CAT_GENERAL, "enableRedstoneControl", true);
		enableSafeMode = getBool(CAT_GENERAL, "enableSafeMode", true);
		
		infuseMiniumStar = getBool(CAT_GENERAL, "infuseMiniumStar", true);
		infuseUUBlock = getBool(CAT_GENERAL, "infuseUUBlock", true);
		
		condenserLimitPerTick = getInt(CAT_GENERAL, "condenserLimitPerTick", 64, -1, TileCondenser.SIDE_SLOTS.length * 64);
		
		enableBattery = getBool(CAT_GENERAL, "enableBattery", true);
		
		if(config.hasChanged())
		config.save();
	}

	public static boolean getBool(String cat, String s, boolean def, String... comment)
	{
		boolean b1 = config.get(cat, s, def).getBoolean(def);
		if(comment.length > 0) setComment(cat, s, comment); return b1;
	}

	public static int getInt(String cat, String s, int def, String... comment)
	{
		int i1 = config.get(cat, s, def).getInt();
		if(comment.length > 0) setComment(cat, s, comment); return i1;
	}

	public static int getInt(String cat, String s, int def, int min, int max, String... comment)
	{
		int i = getInt(cat, s, def);
		int j = i;
		if(i < min) i = min;
		if(i > max) i = max;
		if(comment.length > 0) setComment(cat, s, comment);
		return i;
	}
	
	public static double getDouble(String cat, String s, double def, String... comment)
	{
		double d1 = config.get(cat, s, def).getDouble(def);
		if(comment.length > 0) setComment(cat, s, comment); return d1;
	}

	public static void setComment(String cat, String s, String... s1)
	{ LMUtils.setPropertyComment(config, cat, s, s1); }
	
	private static int blockID = 1313;
	public static int getBlockID(String s)
	{ return config.get("ids", "block." + s, ++blockID).getInt(); }
	
	private static int itemID = 13461;
	public static int getItemID(String s)
	{ return config.get("ids", "item." + s, ++itemID).getInt(); }
}