package com.latmod.emc_condenser.util;

import com.feed_the_beast.ftbl.lib.item.ItemEntry;
import com.feed_the_beast.ftbl.lib.util.CommonUtils;
import com.feed_the_beast.ftbl.lib.util.FileUtils;
import com.feed_the_beast.ftbl.lib.util.JsonUtils;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
import com.google.gson.JsonObject;
import com.latmod.emc_condenser.EMCCConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Blacklist
{
	private static Ingredient ALL = Ingredient.EMPTY;
	private static Ingredient TARGETS = Ingredient.EMPTY;
	private static Ingredient FUELS = Ingredient.EMPTY;
	private static final Map<ItemEntry, Boolean> TARGET_CACHE = new HashMap<>();
	private static final Map<ItemEntry, Boolean> FUEL_CACHE = new HashMap<>();
	public static JsonObject json;

	public static boolean load()
	{
		try
		{
			File file = new File(CommonUtils.folderConfig, "emc_condenser_blacklist.json");

			if (!file.exists())
			{
				FileUtils.save(file, StringUtils.readStringList(new InputStreamReader(Blacklist.class.getResourceAsStream("/assets/emc_condenser/default_blacklist.json"))));
			}

			json = JsonUtils.fromJson(file).getAsJsonObject();
			load(json);
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			json = new JsonObject();
			return false;
		}
	}

	public static void load(JsonObject o)
	{
		TARGET_CACHE.clear();
		FUEL_CACHE.clear();
		ALL = CommonUtils.getIngredient(o.get("all"));
		TARGETS = CommonUtils.getIngredient(o.get("targets"));
		FUELS = CommonUtils.getIngredient(o.get("fuels"));
	}

	public static boolean isBlacklistedTarget(ItemStack stack)
	{
		if (!EMCCConfig.general.blacklist)
		{
			return false;
		}
		else if (stack.isEmpty())
		{
			return true;
		}

		ItemEntry entry = ItemEntry.get(stack);
		Boolean value = TARGET_CACHE.get(entry);

		if (value == null)
		{
			value = TARGETS.test(stack) || ALL.test(stack);
			TARGET_CACHE.put(entry, value);
		}

		return value;
	}

	public static boolean isBlacklistedFuel(ItemStack stack)
	{
		if (!EMCCConfig.general.blacklist)
		{
			return false;
		}
		else if (stack.isEmpty())
		{
			return true;
		}

		ItemEntry entry = ItemEntry.get(stack);
		Boolean value = FUEL_CACHE.get(entry);

		if (value == null)
		{
			value = FUELS.test(stack) || ALL.test(stack);
			FUEL_CACHE.put(entry, value);
		}

		return value;
	}
}