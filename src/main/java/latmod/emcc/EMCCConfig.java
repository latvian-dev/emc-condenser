package latmod.emcc;
import cpw.mods.fml.common.event.*;
import latmod.core.*;
import latmod.core.base.*;
import latmod.core.tile.*;
import latmod.emcc.tile.*;

public class EMCCConfig extends LMConfig
{
	public General general;
	public IDS ids;
	public Recipes recipes;
	public Condenser condenser;
	public Tools tools;
	
	public EMCCConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/EMC_Condenser.cfg");
		add(general = new General());
		add(ids = new IDS());
		add(recipes = new Recipes());
		add(condenser = new Condenser());
		add(tools = new Tools());
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
	
	public class IDS extends Category
	{
		public int b_blocks = 1314;
		
		public int i_materials = 13462;
		public int i_emcStorage = 13463;
		
		public int i_wrench = 13464;
		public int i_sword = 13465;
		public int i_pick = 13466;
		public int i_shovel = 13467;
		public int i_axe = 13468;
		public int i_hoe = 13469;
		public int i_smasher = 13470;
		
		public IDS()
		{
			super("ids");
			
			b_blocks = blockID("machines", b_blocks);
			
			i_materials = itemID("materials", i_materials);
			i_emcStorage = itemID("emcStorage", i_emcStorage);
			
			i_wrench = itemID("wrench", i_wrench);
			i_sword = itemID("sword", i_sword);
			i_pick = itemID("pickaxe", i_pick);
			i_shovel = itemID("shovel", i_shovel);
			i_axe = itemID("axe", i_axe);
			i_hoe = itemID("hoe", i_hoe);
			i_smasher = itemID("smasher", i_smasher);
		}
		
		public int blockID(String s, int def)
		{ return getInt("block." + s, def); }
		
		public int itemID(String s, int def)
		{ return getInt("item." + s, def); }
	}
	
	public class Recipes extends Category
	{
		public int condenserRecipeDifficulty;
		public boolean infuseMiniumStar;
		public boolean infuseUUBlock;
		public int miniumToNetherStar;
		
		public Recipes()
		{
			super("recipes");
			
			condenserRecipeDifficulty = getInt("condenserRecipeDifficulty", 2, 0, 2,
					"This changed the item used in EMC Condenser's crafting recipe",
					"0 - UnUnSeptium Block",
					"1 - Nether Star",
					"2 - Minium Star");
			
			infuseMiniumStar = getBool("infuseMiniumStar", true);
			infuseUUBlock = getBool("infuseUUBlock", true);
			
			miniumToNetherStar = getInt("miniumToNetherStar", 1, 0, 2,
					"0 - Minium Star can't be converted back to Nether Star",
					"1 - Minium Star + Glowstone Dust > Nether Star",
					"2 - Minium Star in furnace > Nether Star");
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
		public boolean enableToolPowers;
		
		public double lifeStone_1hp;
		public double lifeStone_food;
		public double voidStone_item;
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
			enableToolPowers = getBool("enableToolPowers", true);
			
			lifeStone_1hp = getDouble("lifeStone_1hp", 24D);
			lifeStone_food = getDouble("lifeStone_food", 128D);
			voidStone_item = getDouble("voidStone_item", 48D);
			toolEmcPerDamage = getDouble("toolEmcPerDamage", 64D);
		}
	}
}