package latmod.emcc;
import cpw.mods.fml.common.event.*;
import latmod.core.base.LMConfig;

public class EMCCConfig extends LMConfig
{
	public General general;
	
	public EMCCConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/EMC_Condenser.cfg");
		add(general = new General());
	}
	
	public class General extends Category
	{
		public int isCondenserIInventory;
		public int condenserSleepDelay;
		public int recipeDifficulty;
		public int forcedSecurity;
		public int forcedRedstoneControl;
		public int forcedSafeMode;
		public boolean infuseMiniumStar;
		public boolean infuseUUBlock;
		public int condenserLimitPerTick;
		public boolean enableBattery;
		public boolean enableClearBuffer;
		public int miniumToNetherStar;
		public boolean infuseNameTag;
		
		public General()
		{
			super("general");
			
			isCondenserIInventory = getInt("isCondenserIInventory", 3, 0, 3,
					"0 - Automatization disabled",
					"1 - Items can be only pumped out",
					"2 - Items can be only pumped in",
					"3 - Items can be pumped in both ways");
			
			condenserSleepDelay = getInt("condenserSleepDelay", 20, 0, 32767,
					"Longer delay - Less lag. I hope.",
					"Min value - 0, instant condensing",
					"Max value - 32767, that's more than 1.5 days in Minecraft");
			
			recipeDifficulty = getInt("recipeDifficulty", 2, 0, 2,
					"This changed the item used in EMC Condenser's crafting recipe",
					"0 - UnUnSeptium Block",
					"1 - Nether Star",
					"2 - Minium Star");
			
			forcedSecurity = getInt("forcedSecurity", -1, -1, 2,
					"-1 - Choosed by user",
					"0 - Public",
					"1 - Private",
					"2 - Restricted");
			
			forcedRedstoneControl = getInt("forcedRedstoneControl", -1, -1, 2,
					"-1 - Choosed by user",
					"0 - No Redstone Control",
					"1 - Required High signal",
					"2 - Required Low signal");
			
			forcedSafeMode = getInt("forcedSafeMode", -1, -1, 1,
					"-1 - Choosed by user",
					"0 - Safe Mode always off",
					"1 - Safe Mode always on");
			
			infuseMiniumStar = getBool("infuseMiniumStar", true);
			infuseUUBlock = getBool("infuseUUBlock", true);
			
			condenserLimitPerTick = getInt("condenserLimitPerTick", 64, -1, 2880);
			
			enableBattery = getBool("enableBattery", true);
			enableClearBuffer = getBool("enableClearBuffer", true);
			
			miniumToNetherStar = getInt("miniumToNetherStar", 1, 0, 2,
					"0 - Minium Star can't be converted back to Nether Star",
					"1 - Minium Star + Glowstone Dust > Nether Star",
					"2 - Minium Star in furnace > Nether Star");
			
			infuseNameTag = getBool("infuseNameTag", true,
					"If true, 4x Paper + any Slimeball > Name Tag");
		}
	}
}