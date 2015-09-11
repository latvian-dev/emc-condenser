package latmod.emcc;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import latmod.emcc.emc.*;
import latmod.emcc.tile.SafeMode;
import latmod.ftbu.core.*;
import latmod.ftbu.core.tile.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class EMCCConfig extends LMConfig implements IServerConfig
{
	public static VanillaEMC localEMCValues;
	
	public EMCCConfig(FMLPreInitializationEvent e)
	{ super(e, "/LatMod/EMC_Condenser.cfg"); }
	
	public void load()
	{
		General.load(get("general"));
		Condenser.load(get("condenser"));
		Tools.load(get("tools"));
		Enchanting.load(get("enchanting"));
	}
	
	public void readConfig(NBTTagCompound tag)
	{
		int[] b = tag.getIntArray("C");
		General.forceVanillaRecipes = b[0] == 1;
		General.forceVanillaEMC = b[1] == 1;
		
		if(tag.hasKey("B"))
		{
			try
			{
				byte[] b1 = tag.getByteArray("B");
				EMCHandler.instance().vanillaEMC.fromBytes(b1);
			}
			catch(Exception e)
			{ e.printStackTrace(); }
		}
	}
	
	public void writeConfig(NBTTagCompound tag, EntityPlayerMP ep)
	{
		tag.setIntArray("C", new int[]
		{
			General.forceVanillaRecipes ? 1 : 0,
			General.forceVanillaEMC ? 1 : 0,
		});
		
		if(EMCCConfig.General.forceVanillaEMC || !EMCHandler.hasEE3()) try
		{ byte[] b1 = EMCHandler.instance().vanillaEMC.toBytes(); tag.setByteArray("B", b1); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public static class General
	{
		public static boolean enableBlacklist;
		public static float ununblockEnchantPower;
		public static boolean removeNoEMCTooltip;
		public static int ticksToInfuse;
		public static boolean forceVanillaRecipes;
		public static boolean forceVanillaEMC;
		
		public static void load(Category c)
		{
			enableBlacklist = c.getBool("enableBlacklist", true);
			ununblockEnchantPower = c.getFloat("ununblockEnchantPower", 3F, 0F, 100F);
			removeNoEMCTooltip = c.getBool("removeNoEMCTooltip", true);
			ticksToInfuse = c.getInt("ticksToInfuse", 400, -1, 32767);
			forceVanillaRecipes = c.getBool("forceVanillaRecipes", false);
			forceVanillaEMC = c.getBool("forceVanillaEMC", false);
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
		
		public static void load(Category c)
		{
			condenserSleepDelay = c.getInt("condenserSleepDelay", 10, 0, Short.MAX_VALUE);
			c.setComment("condenserSleepDelay",
					"Longer delay - Less condenser updates",
					"Min value - 0, instant condensing",
					"Max value - 32767, that's more than 1.5 days in Minecraft",
					"Default value - 10 (0.5 seconds)");
			
			condenserLimitPerTick = c.getInt("condenserLimitPerTick", 8, -1, 2048);
			c.setComment("condenserLimitPerTick",
					"How many items can be condensed every <condenserSleepDelay> ticks",
					"Max = 2048, Min = 1",
					"-1 = Condense all (first release version)");
			
			int i_forcedInvMode = c.getInt("forcedInvMode", -1, -1, 3);
			c.setComment("forcedInvMode",
					"-1 - User defined",
					"0 - Items can be go both ways",
					"1 - Items can only go in",
					"2 - Items can only go out",
					"3 - Automatization disabled");
			forcedInvMode = (i_forcedInvMode == -1) ? null : InvMode.VALUES[i_forcedInvMode];
			
			int i_forcedSecurity = c.getInt("forcedSecurity", -1, -1, 2);
			c.setComment("forcedSecurity",
					"-1 - User defined",
					"0 - Public",
					"1 - Private",
					"2 - Friends");
			forcedSecurity = (i_forcedSecurity == -1) ? null : LMSecurity.Level.VALUES[i_forcedSecurity];
			
			int i_forcedRedstoneControl = c.getInt( "forcedRedstoneControl", -1, -1, 2);
			c.setComment("forcedRedstoneControl",
					"-1 - User defined",
					"0 - No Redstone Control",
					"1 - Required High signal",
					"2 - Required Low signal");
			forcedRedstoneControl = (i_forcedRedstoneControl == -1) ? null : RedstoneMode.VALUES[i_forcedRedstoneControl];
			
			int i_forcedSafeMode = c.getInt("forcedSafeMode", -1, -1, 1);
			c.setComment("forcedSafeMode",
					"-1 - User defined",
					"0 - Safe Mode always off",
					"1 - Safe Mode always on");
			forcedSafeMode = (i_forcedSafeMode == -1) ? null : SafeMode.VALUES[i_forcedSafeMode];
		}
	}
	
	public static class Tools
	{
		public static boolean enableWrench;
		public static boolean enableBattery;
		public static boolean enableSword;
		public static boolean enableBow;
		public static boolean enableTools;
		
		public static float toolEmcPerDamage;
		public static float lifeStone_1hp;
		public static float lifeStone_food;
		public static float lifeStone_extinguish;
		public static float blackHoleStone_item;
		public static float blackHoleStone_range;
		
		public static void load(Category c)
		{
			enableWrench = c.getBool("enableWrench", true);
			enableBattery = c.getBool("enableBattery", true);
			enableSword = c.getBool("enableSword", true);
			enableBow = c.getBool("enableBow", true);
			enableTools = c.getBool("enableTools", true);
			
			toolEmcPerDamage = c.getFloat("toolEmcPerDamage", 64F);
			lifeStone_1hp = c.getFloat("lifeStone_1hp", 24F);
			lifeStone_food = c.getFloat("lifeStone_food", 128F);
			lifeStone_extinguish = c.getFloat("lifeStone_extinguish", 64F);
			blackHoleStone_item = c.getFloat("blackHoleStone_item", 48F);
			blackHoleStone_range = c.getFloat("blackHoleStone_range", 4F);
		}
	}
	
	public static class Enchanting // Enchantment
	{
		public static int fire; // Flame for bow 
		public static int fortune; // Looting for swords
		public static int unbreaking;
		public static int silk_touch;
		public static int sharpness; // Efficiency for tools
		public static int knockback; // Punch for bow
		public static int infinity;
		
		public static void load(Category c)
		{
			c.setCategoryComment("Infusion in Anvil", "1 ~ 50 - required to infuse", "0 - disabled");
			
			fire = getLevel(c, "fire", 20, "Blaze rod");
			fortune = getLevel(c, "fortune", 25, "Gold Ingot [1 lvl]");
			unbreaking = getLevel(c, "unbreaking", 20, "Obsidian [1 lvl]");
			silk_touch = getLevel(c, "silk_touch", 40, "String");
			sharpness = getLevel(c, "sharpness", 15, "Iron Sword [1 lvl]");
			knockback = getLevel(c, "knockback", 15, "Piston [1 lvl]");
			infinity = getLevel(c, "infinity", 50, "Diamond");
		}
		
		private static int getLevel(Category c, String s, int def, String comment)
		{
			int i = c.getInt(s, def, 0, 50);
			c.setComment(s, comment + ", default: " + def);
			return i;
		}
	}
}