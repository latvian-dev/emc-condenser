package latmod.emcc;
import latmod.core.mod.*;
import latmod.core.mod.tile.*;
import latmod.emcc.tile.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class EMCCConfig extends LMConfig
{
	public General general;
	public Recipes recipes;
	public Condenser condenser;
	public Tools tools;
	public Infusion infusion;
	
	public EMCCConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/EMC_Condenser.cfg");
		add(general = new General());
		add(recipes = new Recipes());
		add(condenser = new Condenser());
		add(tools = new Tools());
		add(infusion = new Infusion());
		save();
	}
	
	public class General extends Category
	{
		public boolean enableCustomEMC;
		public boolean enableAludelRecipes;
		public boolean enableBlacklist;
		public double ununblockEnchantPower;
		
		public General()
		{
			super("general");
			
			enableCustomEMC = getBool("enableCustomEMC", true);
			enableAludelRecipes = getBool("enableAludelRecipes", true);
			enableBlacklist = getBool("enableBlacklist", true);
			ununblockEnchantPower = getDouble("ununblockEnchantPower", 3D);
		}
	}
	
	public class Recipes extends Category
	{
		public int condenserRecipeDifficulty;
		public int miniumToNetherStar;
		public boolean infuseMiniumStar;
		public boolean infuseUUBlock;
		public boolean infuseEnchBottle;
		
		public Recipes()
		{
			super("recipes");
			
			condenserRecipeDifficulty = getInt("condenserRecipeDifficulty", 2, 0, 2,
					"This changed the item used in EMC Condenser's crafting recipe",
					"0 - UnUnSeptium Block",
					"1 - Nether Star",
					"2 - Minium Star");
			
			miniumToNetherStar = getInt("miniumToNetherStar", 1, 0, 2,
					"0 - Minium Star can't be converted back to Nether Star",
					"1 - Minium Star + Glowstone Dust > Nether Star",
					"2 - Minium Star in furnace > Nether Star");
			
			infuseMiniumStar = getBool("infuseMiniumStar", true);
			infuseUUBlock = getBool("infuseUUBlock", true);
			infuseEnchBottle = getBool("infuseEnchBottle", true);
		}
	}
	
	public class Condenser extends Category
	{
		public int condenserSleepDelay;
		public int condenserLimitPerTick;
		public InvMode forcedInvMode;
		public LMSecurity.Level forcedSecurity;
		public RedstoneMode forcedRedstoneControl;
		public SafeMode forcedSafeMode;
		public RepairTools forcedRepairItems;
		
		public Condenser()
		{
			super("condenser");
			
			condenserSleepDelay = getInt("condenserSleepDelay", 10, 0, 32767,
					"Longer delay - Less condenser updates",
					"Min value - 0, instant condensing",
					"Max value - 32767, that's more than 1.5 days in Minecraft",
					"Default value - 10 (0.5 seconds)");
			
			condenserLimitPerTick = getInt("condenserLimitPerTick", 8, -1, 2048,
					"How many items can be condensed every <condenserSleepDelay> ticks",
					"Max = 2048, Min = 1",
					"-1 = Condense all (first release version)");
			
			int i_forcedInvMode = getInt("forcedInvMode", -1, -1, 3,
					"-1 - Choosed by user",
					"0 - Items can be go both ways",
					"1 - Items can only go in",
					"2 - Items can only go out",
					"3 - Automatization disabled");
			forcedInvMode = (i_forcedInvMode == -1) ? null : InvMode.VALUES[i_forcedInvMode];
			
			int i_forcedSecurity = getInt("forcedSecurity", -1, -1, 3,
					"-1 - Choosed by user",
					"0 - Public",
					"1 - Private",
					"2 - Whitelist",
					"3 - Blacklist");
			forcedSecurity = (i_forcedSecurity == -1) ? null : LMSecurity.Level.VALUES[i_forcedSecurity];
			
			int i_forcedRedstoneControl = getInt( "forcedRedstoneControl", -1, -1, 2,
					"-1 - Choosed by user",
					"0 - No Redstone Control",
					"1 - Required High signal",
					"2 - Required Low signal");
			forcedRedstoneControl = (i_forcedRedstoneControl == -1) ? null : RedstoneMode.VALUES[i_forcedRedstoneControl];
			
			int i_forcedSafeMode = getInt("forcedSafeMode", -1, -1, 1,
					"-1 - Choosed by user",
					"0 - Safe Mode always off",
					"1 - Safe Mode always on");
			forcedSafeMode = (i_forcedSafeMode == -1) ? null : SafeMode.VALUES[i_forcedSafeMode];
			
			int i_forcedRepairItems = getInt("forcedRepairItems", -1, -1, 1,
					"-1 - Choosed by user",
					"0 - Repair Items always off",
					"1 - Repair Items always on");
			forcedRepairItems = (i_forcedRepairItems == -1) ? null : RepairTools.VALUES[i_forcedRepairItems];
		}
	}
	
	public class Tools extends Category
	{
		public boolean enableWrench;
		public boolean enableBattery;
		public boolean enableSword;
		public boolean enablePick;
		public boolean enableShovel;
		public boolean enableAxe;
		public boolean enableHoe;
		public boolean enableSmasher;
		
		public double lifeStone_1hp;
		public double lifeStone_food;
		public double blackHoleStone_item;
		public double blackHoleStone_range;
		public double toolEmcPerDamage;
		
		public Tools()
		{
			super("tools");
			
			enableWrench = getBool("enableWrench", true);
			enableBattery = getBool("enableBattery", true);
			enableSword = getBool("enableSword", true);
			enablePick = getBool("enablePick", true);
			enableShovel = getBool("enableShovel", true);
			enableAxe = getBool("enableAxe", true);
			enableHoe = getBool("enableHoe", true);
			enableSmasher = getBool("enableSmasher", true);
			
			lifeStone_1hp = getDouble("lifeStone_1hp", 24D);
			lifeStone_food = getDouble("lifeStone_food", 128D);
			blackHoleStone_item = getDouble("blackHoleStone_item", 48D);
			blackHoleStone_range = getDouble("blackHoleStone_range", 4D);
			toolEmcPerDamage = getDouble("toolEmcPerDamage", 64D);
		}
	}
	
	public class Infusion extends Category
	{
		//Enchantment
		
		public int fire; // Flame for bow 
		public int area; // Thorns
		public int fortune; // Looting for swords
		public int unbreaking;
		public int silkTouch;
		public int sharpness; // Efficiency for tools
		public int knockback; // Punch for bow
		public int infinity;
		
		public Infusion()
		{
			super("infusion");
			setCategoryDesc(
					"Infusion in UnUnSeptium Infuser",
					"X Items [1 - 64] required to infuse, 0 - disabled");
			
			fire = getInt("fire", 16, 0, 64, "Blaze rods, default: 16");
			area = getInt("area", 8, 0, 64, "UnUnSeptium Blocks, default: 8");
			fortune = getInt("fortune", 8, 0, 64, "Gold Ingots [1 lvl], default: 8");
			unbreaking = getInt("unbreaking", 8, 0, 64, "Obsidian [1 lvl], default: 8");
			silkTouch = getInt("silkTouch", 32, 0, 64, "String, default: 32");
			sharpness = getInt("sharpness", 16, 0, 64, "Iron Ingots [1 lvl], default: 16");
			knockback = getInt("knockback", 4, 0, 64, "Piston [1 lvl], default: 4");
			infinity = getInt("infinity", 16, 0, 64, "Diamonds, default: 16");
		}
	}
}