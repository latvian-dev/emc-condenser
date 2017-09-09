package com.latmod.emcc;

import com.feed_the_beast.ftbl.lib.util.CommonUtils;
import com.feed_the_beast.ftbl.lib.util.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.latmod.emcc.config.EMCCConfigGeneral;
import ftb.lib.api.item.ItemEntry;
import ftb.lib.api.item.ODItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IJsonSerializable;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Blacklist implements IJsonSerializable
{
	public static final Blacklist INSTANCE = new Blacklist();

	public void load()
	{
		clear();

		File file = new File(CommonUtils.folderConfig, "emc_condenser_blacklist.json");
		JsonElement e = JsonUtils.fromJson(file);

		if (e.isJsonObject())
		{
			fromJson(e);
		}
		else
		{
			addDefaults();
			JsonUtils.toJson(file, getSerializableElement());
		}

		all.reloadList();
		targets.reloadList();
		fuels.reloadList();
	}

	public final BlacklistEntryList all;
	public final BlacklistEntryList targets;
	public final BlacklistEntryList fuels;

	public Blacklist()
	{
		all = new BlacklistEntryList();
		targets = new BlacklistEntryList();
		fuels = new BlacklistEntryList();
	}

	public void clear()
	{
		all.clear();
		targets.clear();
		fuels.clear();
	}

	public void addDefaults()
	{
		all.addRegistryName(Items.EXPERIENCE_BOTTLE, 0);

		all.addOreName("oreIron");
		all.addOreName("oreGold");
		all.addOreName("oreCoal");
		all.addOreName("oreRedstone");
		all.addOreName("oreLapis");
		all.addOreName("oreDiamond");
		all.addOreName("oreEmerald");
		all.addOreName("oreQuartz");
		all.addOreName("oreTin");
		all.addOreName("oreCopper");
		all.addOreName("oreUranium");
		all.addOreName("oreNickel");
		all.addOreName("oreLead");
		all.addOreName("oreSilver");
		all.addOreName("oreRuby");
		all.addOreName("oreSapphire");
		all.addOreName("oreAluminum");
		all.addOreName("oreFzDarkIron");

		targets.addRegistryName(Items.NETHER_STAR, 0);
		targets.addRegistryName(Items.ENCHANTED_BOOK, -1);

		fuels.addOreName("cobblestone");
		fuels.addOreName("stone");
		fuels.addOreName("foodSalt");
	}

	@Override
	public void fromJson(JsonElement e)
	{
		JsonObject o = e.getAsJsonObject();
		all.func_152753_a(o.get("all"));
		targets.func_152753_a(o.get("targets"));
		fuels.func_152753_a(o.get("fuels"));
	}

	@Override
	public JsonElement getSerializableElement()
	{
		JsonObject o = new JsonObject();
		o.add("all", all.getSerializableElement());
		o.add("targets", targets.getSerializableElement());
		o.add("fuels", fuels.getSerializableElement());
		return o;
	}

	public boolean isBlacklistedFuel(ItemStack is)
	{
		if (!EMCCConfig.general.blacklist)
		{
			return false;
		}
		if (fuels.isBlacklistedRegName(is) || all.isBlacklistedRegName(is))
		{
			return true;
		}

		int[] oreNames = OreDictionary.getOreIDs(is);

		if (oreNames.length > 0)
		{
			if (fuels.isBlacklistedOre(oreNames) || all.isBlacklistedOre(oreNames))
			{
				return true;
			}
		}

		return false;
	}

	public boolean isBlacklistedTarget(ItemStack is)
	{
		if (is.getItem() == EMCCItems.WRENCH)
		{
			return true;
		}

		if (!EMCCConfigGeneral.blacklist.getAsBoolean())
		{
			return false;
		}

		if (targets.isBlacklistedRegName(is) || all.isBlacklistedRegName(is))
		{
			return true;
		}

		List<String> oreNames = ODItems.getOreNames(is);

		if (oreNames != null && oreNames.size() > 0)
		{
			if (targets.isBlacklistedOre(oreNames) || all.isBlacklistedOre(oreNames))
			{
				return true;
			}
		}

		return false;
	}

	public static class BlacklistEntryList implements IJsonSerializable
	{
		private final Set<String> oreNames;
		private final Set<ItemEntry> regNames;

		public BlacklistEntryList()
		{
			oreNames = new LinkedHashSet<>();
			regNames = new LinkedHashSet<>();
		}

		public void clear()
		{
			oreNames.clear();
			regNames.clear();
		}

		public void addOreName(String s)
		{
			if (s != null && !s.isEmpty())
			{
				oreNames.add(s);
			}
		}

		public void addRegistryName(Item item, int dmg)
		{
			regNames.add(new ItemEntry(item, dmg));
		}

		public boolean isBlacklistedRegName(ItemStack is)
		{
			return regNames.contains(is);
		}

		public boolean isBlacklistedOre(int[] l)
		{
			if (l.length == 0)
			{
				return false;
			}
			else if (l.length == 1)
			{
				return oreNames.contains(OreDictionary.getOreName(l[0]));
			}

			for (int i : l)
			{
				if (oreNames.contains(OreDictionary.getOreName(i)))
				{
					return true;
				}
			}

			return false;
		}

		public void reloadList()
		{
		}

		@Override
		public void func_152753_a(JsonElement p_152753_1_)
		{
		}

		@Override
		public JsonElement getSerializableElement()
		{
			JsonObject o = new JsonObject();
			return o;
		}
	}
}