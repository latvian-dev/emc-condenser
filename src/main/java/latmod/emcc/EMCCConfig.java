package latmod.emcc;
import latmod.core.*;
import latmod.core.tile.enums.*;
import latmod.emcc.tile.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class EMCCConfig extends LMConfig
{
	public EMCCConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/EMC_Condenser.cfg");
		General.load(get("general"));
		Recipes.load(get("recipes"));
		Condenser.load(get("condenser"));
		Tools.load(get("tools"));
		Infusion.load(get("infusion"));
		save();
	}
	
	public static class General
	{
		public static boolean enableBlacklist;
		public static double ununblockEnchantPower;
		
		public static void load(Category c)
		{
			enableBlacklist = c.getBool("enableBlacklist", true);
			ununblockEnchantPower = c.getDouble("ununblockEnchantPower", 3D);
		}
	}
	
	public static class Recipes
	{
		public static int condenserRecipeDifficulty;
		public static int miniumToNetherStar;
		public static boolean infuseMiniumStar;
		public static boolean infuseEnchBottle;
		public static int infusedUUBlocks;
		public static int infusedUUIngots;
		
		public static void load(Category c)
		{
			condenserRecipeDifficulty = c.getInt("condenserRecipeDifficulty", 2, 0, 2,
					"This changed the item used in EMC Condenser's crafting recipe",
					"0 - UnUnSeptium Block",
					"1 - Nether Star",
					"2 - Minium Star");
			
			miniumToNetherStar = c.getInt("miniumToNetherStar", 1, 0, 2,
					"0 - Minium Star can't be converted back to Nether Star",
					"1 - Minium Star + Glowstone Dust > Nether Star",
					"2 - Minium Star in furnace > Nether Star");
			
			infuseMiniumStar = c.getBool("infuseMiniumStar", true);
			infuseEnchBottle = c.getBool("infuseEnchBottle", true);
			infusedUUBlocks = c.getInt("infusedUUBlocks", 8, 0, 64);
			
			infusedUUIngots = c.getInt("infusedUUIngots", 8, -1, 64,
					"0 - Recipe Disabled",
					"-1 - Smelt UnUnSeptium in furnace",
					"1-64 - Ingots per 1 UnUnSeptium in Aludel");
		}
	}
	
	public static class Condenser
	{
		public static int condenserSleepDelay;
		public static int condenserLimitPerTick;
		public static InvMode forcedInvMode;
		public static LMSecurity.Level forcedSecurity;
		public static RedstoneMode forcedRedstoneControl;
		public static SafeMode forcedSafeMode;
		public static RepairTools forcedRepairItems;
		
		public static void load(Category c)
		{
			condenserSleepDelay = c.getInt("condenserSleepDelay", 10, 0, 32767,
					"Longer delay - Less condenser updates",
					"Min value - 0, instant condensing",
					"Max value - 32767, that's more than 1.5 days in Minecraft",
					"Default value - 10 (0.5 seconds)");
			
			condenserLimitPerTick = c.getInt("condenserLimitPerTick", 8, -1, 2048,
					"How many items can be condensed every <condenserSleepDelay> ticks",
					"Max = 2048, Min = 1",
					"-1 = Condense all (first release version)");
			
			int i_forcedInvMode = c.getInt("forcedInvMode", -1, -1, 3,
					"-1 - Choosed by user",
					"0 - Items can be go both ways",
					"1 - Items can only go in",
					"2 - Items can only go out",
					"3 - Automatization disabled");
			forcedInvMode = (i_forcedInvMode == -1) ? null : InvMode.VALUES[i_forcedInvMode];
			
			int i_forcedSecurity = c.getInt("forcedSecurity", -1, -1, 3,
					"-1 - Choosed by user",
					"0 - Public",
					"1 - Private",
					"2 - Whitelist",
					"3 - Blacklist");
			forcedSecurity = (i_forcedSecurity == -1) ? null : LMSecurity.Level.VALUES[i_forcedSecurity];
			
			int i_forcedRedstoneControl = c.getInt( "forcedRedstoneControl", -1, -1, 2,
					"-1 - Choosed by user",
					"0 - No Redstone Control",
					"1 - Required High signal",
					"2 - Required Low signal");
			forcedRedstoneControl = (i_forcedRedstoneControl == -1) ? null : RedstoneMode.VALUES[i_forcedRedstoneControl];
			
			int i_forcedSafeMode = c.getInt("forcedSafeMode", -1, -1, 1,
					"-1 - Choosed by user",
					"0 - Safe Mode always off",
					"1 - Safe Mode always on");
			forcedSafeMode = (i_forcedSafeMode == -1) ? null : SafeMode.VALUES[i_forcedSafeMode];
			
			int i_forcedRepairItems = c.getInt("forcedRepairItems", -1, -1, 1,
					"-1 - Choosed by user",
					"0 - Repair Items always off",
					"1 - Repair Items always on");
			forcedRepairItems = (i_forcedRepairItems == -1) ? null : RepairTools.VALUES[i_forcedRepairItems];
		}
	}
	
	public static class Tools
	{
		public static boolean enableWrench;
		public static boolean enableBattery;
		public static boolean enableSword;
		public static boolean enableTools;
		public static boolean enableBow;
		
		public static double lifeStone_1hp;
		public static double lifeStone_food;
		public static double blackHoleStone_item;
		public static double blackHoleStone_range;
		public static double toolEmcPerDamage;
		
		public static void load(Category c)
		{
			enableWrench = c.getBool("enableWrench", true);
			enableBattery = c.getBool("enableBattery", true);
			enableSword = c.getBool("enableSword", true);
			enableTools = c.getBool("enableTools", true);
			enableBow = c.getBool("enableBow", true);
			
			lifeStone_1hp = c.getDouble("lifeStone_1hp", 24D);
			lifeStone_food = c.getDouble("lifeStone_food", 128D);
			blackHoleStone_item = c.getDouble("blackHoleStone_item", 48D);
			blackHoleStone_range = c.getDouble("blackHoleStone_range", 4D);
			toolEmcPerDamage = c.getDouble("toolEmcPerDamage", 64D);
		}
	}
	
	public static class Infusion
	{
		//Enchantment
		
		public static int fire; // Flame for bow 
		public static int area; // Thorns
		public static int fortune; // Looting for swords
		public static int unbreaking;
		public static int silkTouch;
		public static int sharpness; // Efficiency for tools
		public static int knockback; // Punch for bow
		public static int infinity;
		
		public static void load(Category c)
		{
			c.setCategoryDesc(
					"Infusion in UnUnSeptium Infuser",
					"X Items [1 - 256] required to infuse, 0 - disabled");
			
			fire = c.getInt("fire", 16, 0, 256, "Blaze rods, default: 16");
			area = c.getInt("area", 8, 0, 256, "UnUnSeptium Blocks, default: 8");
			fortune = c.getInt("fortune", 8, 0, 256, "Gold Ingots [1 lvl], default: 8");
			unbreaking = c.getInt("unbreaking", 8, 0, 256, "Obsidian [1 lvl], default: 8");
			silkTouch = c.getInt("silkTouch", 32, 0, 256, "String, default: 32");
			sharpness = c.getInt("sharpness", 16, 0, 256, "Iron Ingots [1 lvl], default: 16");
			knockback = c.getInt("knockback", 4, 0, 256, "Piston [1 lvl], default: 4");
			infinity = c.getInt("infinity", 16, 0, 256, "Diamonds, default: 16");
		}
	}
}