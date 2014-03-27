package mods.lm.emcc;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import mods.lm.core.*;

public class EMCCConfig
{
	public static Configuration config;
	
	public static final void load(FMLPreInitializationEvent e)
	{
		config = LatCore.loadConfig(e, "/LatMod/EMC_Cond.cfg");
	}
	
	public static final void save()
	{ config.save(); }
	
	public static boolean getBool(String s, boolean b)
	{ return config.get(Configuration.CATEGORY_GENERAL, s, b ? 1 : 0).getInt(b ? 1 : 0) == 1; }
	
	public static int getFromGeneralConfig(String s, int def)
	{ return config.get(Configuration.CATEGORY_GENERAL, s, def).getInt(); }

	public static int getFromGeneralConfig(String s, int def, int min, int max)
	{
		int i = getFromGeneralConfig(s, def);
		int j = i;
		if(i < min) i = min;
		if(i > max) i = max;
		if(j != i) config.getCategory(Configuration.CATEGORY_GENERAL).get(s).set(i);
		return i;
	}
	
	public static boolean getFromGeneralConfig(String s, boolean def)
	{ return getFromGeneralConfig(s, def ? 1 : 0, 0, 1) == 1; }
	
	public static void setGeneralComment(String s, String... s1)
	{ LMUtils.setPropertyComment(config, Configuration.CATEGORY_GENERAL, s, s1); }
	
	private static int blockID = 1313;
	public static int getBlockID(String s)
	{ return config.get(Configuration.CATEGORY_BLOCK, s, ++blockID).getInt(); }
	
	private static int itemID = 13461;
	public static int getItemID(String s)
	{ return config.get(Configuration.CATEGORY_ITEM, s, ++itemID).getInt(); }
}