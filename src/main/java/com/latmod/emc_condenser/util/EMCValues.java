package com.latmod.emc_condenser.util;

import com.feed_the_beast.ftbl.lib.item.ItemEntry;
import com.feed_the_beast.ftbl.lib.util.CommonUtils;
import com.feed_the_beast.ftbl.lib.util.FileUtils;
import com.feed_the_beast.ftbl.lib.util.JsonUtils;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
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
	private static final List<EMCValue> LIST = new ArrayList<>();
	private static final Map<ItemEntry, EMCValue> CACHE = new HashMap<>();
	public static JsonArray json;

	public static boolean load()
	{
		try
		{
			File file = new File(CommonUtils.folderConfig, "emc_condenser_values.json");

			if (!file.exists())
			{
				FileUtils.save(file, StringUtils.readStringList(new InputStreamReader(EMCValues.class.getResourceAsStream("/assets/emc_condenser/default_values.json"))));
			}

			json = JsonUtils.fromJson(file).getAsJsonArray();
			load(json);
			return true;
		}
		catch (Exception ex)
		{
			System.err.println(ex.toString());
			json = new JsonArray();
			return false;
		}
	}

	public static void load(JsonArray a)
	{
		LIST.clear();
		CACHE.clear();

		for (JsonElement e : a)
		{
			JsonObject o = e.getAsJsonObject();
			int value = o.has("emc") ? o.get("emc").getAsInt() : 0;

			if (value > 0)
			{
				o.remove("emc");
				Ingredient ingredient = CommonUtils.getIngredient(o);

				if (ingredient != Ingredient.EMPTY)
				{
					LIST.add(new EMCValue(ingredient, value));
				}
			}
		}
	}

	public static EMCValue getEMC(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return EMCValue.EMPTY;
		}

		ItemEntry entry = ItemEntry.get(stack);
		EMCValue emc = CACHE.get(entry);

		if (emc == null)
		{
			emc = EMCValue.EMPTY;

			for (EMCValue value : LIST)
			{
				if (value.ingredient.apply(stack))
				{
					emc = value;
					break;
				}
			}

			CACHE.put(entry, emc);
		}

		return emc;
	}
}