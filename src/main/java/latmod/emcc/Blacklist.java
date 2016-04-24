package latmod.emcc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ftb.lib.FTBLib;
import ftb.lib.api.item.ItemEntry;
import ftb.lib.api.item.ODItems;
import latmod.emcc.config.EMCCConfigGeneral;
import latmod.lib.LMJsonUtils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IJsonSerializable;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Blacklist implements IJsonSerializable
{
	public static final Blacklist instance = new Blacklist();
	
	public void load()
	{
		clear();
		
		File file = new File(FTBLib.folderConfig, "/LatMod/EMC_Condenser/blacklist.json");
		JsonElement e = LMJsonUtils.fromJson(file);
		
		if(e.isJsonObject())
		{
			func_152753_a(e);
		}
		else
		{
			addDefaults();
			LMJsonUtils.toJson(file, getSerializableElement());
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
		all.addRegistryName(Items.experience_bottle, 0);
		
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
		
		targets.addRegistryName(Items.nether_star, 0);
		targets.addRegistryName(Items.enchanted_book, -1);
		
		fuels.addOreName(ODItems.COBBLE);
		fuels.addOreName(ODItems.STONE);
		fuels.addOreName("foodSalt");
	}
	
	@Override
	public void func_152753_a(JsonElement e)
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
		if(!EMCCConfigGeneral.blacklist.getAsBoolean()) return false;
		if(fuels.isBlacklistedRegName(is) || all.isBlacklistedRegName(is)) return true;
		
		List<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(fuels.isBlacklistedOre(oreNames) || all.isBlacklistedOre(oreNames)) return true;
		}
		
		return false;
	}
	
	public boolean isBlacklistedTarget(ItemStack is)
	{
		if(is.getItem() == EMCCItems.i_wrench) return true;
		
		if(!EMCCConfigGeneral.blacklist.getAsBoolean()) return false;
		
		if(targets.isBlacklistedRegName(is) || all.isBlacklistedRegName(is)) return true;
		
		List<String> oreNames = ODItems.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(targets.isBlacklistedOre(oreNames) || all.isBlacklistedOre(oreNames)) return true;
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
			if(s != null && !s.isEmpty())
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
		
		public boolean isBlacklistedOre(List<String> l)
		{
			if(l == null || l.isEmpty()) return false;
			else if(l.size() == 1) return oreNames.contains(l.get(0));
			
			for(String s : l)
			{
				if(oreNames.contains(s))
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