package mods.lm.emcc;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.*;
import latmod.core.*;

public class EMCCConfig
{
	public static Configuration config;

	public static class General
	{
		public static boolean isCondenserIInventory;
		public static int condenserSleepDelay;

		public static void load()
		{
			String CAT_GENERAL = "general";

			isCondenserIInventory = getBool(CAT_GENERAL, "isCondenserIInventory", false);
			condenserSleepDelay = getInt(CAT_GENERAL, "condenserSleepDelay", 4);
			if(condenserSleepDelay < 1) condenserSleepDelay = 1;
		}
	}
	
	public static class Crafting
	{
		public static boolean smeltFleshToLeather;
		public static boolean craftPortal;
		public static boolean craftCircuitBoard;

		public static void load()
		{
			String CAT_CRAFTING = "crafting";

			smeltFleshToLeather = getBool(CAT_CRAFTING, "smeltFleshToLeather", true);
			craftPortal = getBool(CAT_CRAFTING, "craftPortal", true);
			craftCircuitBoard = getBool(CAT_CRAFTING, "craftCircuitBoard", true);
		}
	}

	public static final void load(FMLPreInitializationEvent e)
	{
		config = LatCore.loadConfig(e, "/LatMod/EMC_Condenser.cfg");

		General.load();
		Crafting.load();

		if(config.hasChanged())
		config.save();
	}

	public static boolean getBool(String cat, String s, boolean def, String... comment)
	{
		boolean b1 = config.get(cat, s, def).getBoolean(def);
		setComment(cat, s, comment); return b1;
	}

	public static int getInt(String cat, String s, int def, String... comment)
	{
		int i1 = config.get(cat, s, def).getInt();
		setComment(cat, s, comment); return i1;
	}

	public static double getDouble(String cat, String s, double def, String... comment)
	{
		double d1 = config.get(cat, s, def).getDouble(def);
		setComment(cat, s, comment); return d1;
	}

	public static int getInt(String cat, String s, int def, int min, int max, String... comment)
	{
		int i = getInt(cat, s, def);
		int j = i;
		if(i < min) i = min;
		if(i > max) i = max;
		setComment(cat, s, comment);
		return i;
	}

	public static void setComment(String cat, String s, String... s1)
	{ LMUtils.setPropertyComment(config, cat, s, s1); }
	
	private static int blockID = 1313;
	public static int getBlockID(String s)
	{ return config.get("block_id", s, ++blockID).getInt(); }
	
	private static int itemID = 13461;
	public static int getItemID(String s)
	{ return config.get("item_id", s, ++itemID).getInt(); }
}