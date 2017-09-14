package com.latmod.emc_condenser;

import com.feed_the_beast.ftbl.lib.EnumIO;
import com.feed_the_beast.ftbl.lib.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.EnumRedstoneMode;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EMCC.MOD_ID)
@Config(modid = EMCC.MOD_ID, category = "config")
public class EMCCConfig
{
	public static final General general = new General();
	public static final Condenser condenser = new Condenser();
	public static final CondenserForced condenser_forced = new CondenserForced();
	public static final Enchanting enchanting = new Enchanting();
	public static final Tools tools = new Tools();

	public static class General
	{
		public boolean blacklist = true;

		@Config.RangeDouble(min = 0D, max = 100D)
		public double uu_block_enchant_power = 3D;
	}

	public static class Condenser
	{
		@Config.RangeInt(min = 0, max = Short.MAX_VALUE)
		@Config.Comment("Longer delay - Less condenser updates")
		public int sleep_delay = 10;

		@Config.RangeInt(min = -1, max = 2048)
		@Config.Comment("How many items can be condensed every <sleep_delay> ticks")
		public int limit_per_tick = 8;
	}

	public static class CondenserForced
	{
		public EnumIO inv_mode = EnumIO.IO;
		public EnumPrivacyLevel security = EnumPrivacyLevel.PUBLIC;
		public EnumRedstoneMode redstone_control = EnumRedstoneMode.DISABLED;
		public boolean safe_mode = true;

		public boolean force_inv_mode = false;
		public boolean force_security = false;
		public boolean force_redstone_control = false;
		public boolean force_safe_mode = false;
	}

	public static class Enchanting
	{
		@Config.RangeInt(min = 0, max = 50)
		public int fire = 20; // Flame for bow

		@Config.RangeInt(min = 0, max = 50)
		public int fortune = 25; // Looting for swords

		@Config.RangeInt(min = 0, max = 50)
		public int unbreaking = 20;

		@Config.RangeInt(min = 0, max = 50)
		public int silk_touch = 40;

		@Config.RangeInt(min = 0, max = 50)
		public int sharpness = 15; // Efficiency for tools

		@Config.RangeInt(min = 0, max = 50)
		public int knockback = 15; // Punch for bow

		@Config.RangeInt(min = 0, max = 50)
		public int infinity = 50;
	}

	public static class Tools
	{
		@Config.RangeDouble(min = 1D, max = 100D)
		public double strength_multiplier = 1.5D;

		@Config.RangeInt(min = 0)
		public int tool_emc_per_damage = 64;

		@Config.RangeInt(min = 0)
		public int life_stone_1hp = 24;

		@Config.RangeInt(min = 0)
		public int life_stone_food = 128;

		@Config.RangeInt(min = 0)
		public int life_stone_extinguish = 64;

		@Config.RangeInt(min = 0)
		public int black_hole_stone_item = 48;

		@Config.RangeDouble(min = 0, max = 64)
		public double black_hole_stone_range = 4;
	}

	public static void sync()
	{
		ConfigManager.sync(EMCC.MOD_ID, Config.Type.INSTANCE);
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(EMCC.MOD_ID))
		{
			sync();
		}
	}
}