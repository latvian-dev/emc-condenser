package com.latmod.emc_condenser.util;

import com.feed_the_beast.ftblib.lib.item.ItemEntry;
import com.feed_the_beast.ftblib.lib.util.CommonUtils;
import com.feed_the_beast.ftblib.lib.util.FileUtils;
import com.feed_the_beast.ftblib.lib.util.JsonUtils;
import com.feed_the_beast.ftblib.lib.util.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EMCValues
{
	private static final EMCValues ALL = new EMCValues();
	private static final EMCValues CONSTRUCTION = new EMCValues();
	private static final EMCValues DESTRUCTION = new EMCValues();
	private static final Map<ItemEntry, EMCValue> CONSTRUCTION_CACHE = new HashMap<>();
	private static final Map<ItemEntry, EMCValue> DESTRUCTION_CACHE = new HashMap<>();
	public static JsonObject json;

	public static boolean load()
	{
		try
		{
			File file = new File(CommonUtils.folderConfig, "emc_condenser_values.json");

			if (!file.exists())
			{
				FileUtils.save(file, StringUtils.readStringList(new InputStreamReader(EMCValues.class.getResourceAsStream("/assets/emc_condenser/default_values.json"))));
			}

			json = JsonUtils.fromJson(file).getAsJsonObject();
			return load(json);
		}
		catch (Exception ex)
		{
			System.err.println(ex.toString());
			json = new JsonObject();
			return false;
		}
	}

	public static boolean load(JsonObject json)
	{
		CONSTRUCTION_CACHE.clear();
		DESTRUCTION_CACHE.clear();
		boolean all = !json.has("all") || load(ALL, json.get("all").getAsJsonArray());
		boolean construction = !json.has("construction") || load(CONSTRUCTION, json.get("construction").getAsJsonArray());
		boolean destruction = !json.has("destruction") || load(DESTRUCTION, json.get("destruction").getAsJsonArray());
		return all || construction || destruction;
	}

	private static boolean load(EMCValues values, JsonArray a)
	{
		values.values.clear();
		boolean ret = true;

		for (JsonElement e : a)
		{
			JsonObject o = e.getAsJsonObject();
			int value = o.has("emc") ? o.get("emc").getAsInt() : 0;

			if (value > 0)
			{
				try
				{
					Ingredient ingredient = CommonUtils.getIngredient(o.has("ingredient") ? o.get("ingredient") : o);

					if (ingredient != Ingredient.EMPTY)
					{
						values.values.add(new EMCValue(ingredient, value));
					}
				}
				catch (Exception ex)
				{
					System.err.println(ex.toString());
					ret = false;
				}
			}
		}

		return ret;
	}

	public static EMCValue getConstructionEMC(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return EMCValue.EMPTY;
		}

		ItemEntry entry = ItemEntry.get(stack);
		EMCValue emc = CONSTRUCTION_CACHE.get(entry);

		if (emc == null)
		{
			emc = CONSTRUCTION.getEMC(stack);

			if (emc.isEmpty())
			{
				emc = ALL.getEMC(stack);
			}

			CONSTRUCTION_CACHE.put(entry, emc);
		}

		return emc;
	}

	public static EMCValue getDestructionEMC(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return EMCValue.EMPTY;
		}

		ItemEntry entry = ItemEntry.get(stack);
		EMCValue emc = DESTRUCTION_CACHE.get(entry);

		if (emc == null)
		{
			emc = DESTRUCTION.getEMC(stack);

			if (emc.isEmpty())
			{
				emc = ALL.getEMC(stack);
			}

			DESTRUCTION_CACHE.put(entry, emc);
		}

		return emc;
	}

	private final List<EMCValue> values = new ArrayList<>();

	private EMCValue getEMC(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return EMCValue.EMPTY;
		}

		for (EMCValue value : values)
		{
			if (!value.isEmpty() && value.ingredient.apply(stack))
			{
				return value;
			}
		}

		return EMCValue.EMPTY;
	}
}