package latmod.emcc.blacklist;

import com.google.gson.*;
import latmod.emcc.api.ItemEntry;
import net.minecraft.item.*;
import net.minecraft.util.IJsonSerializable;

import java.util.*;

public class Blacklist implements IJsonSerializable
{
	public final BlacklistEntryList all;
	public final BlacklistEntryList targets;
	public final BlacklistEntryList fuels;
	
	public Blacklist()
	{
		all = new BlacklistEntryList();
		targets = new BlacklistEntryList();
		fuels = new BlacklistEntryList();
	}
	
	public void func_152753_a(JsonElement e)
	{
		JsonObject o = e.getAsJsonObject();
		all.func_152753_a(o.get("all"));
		targets.func_152753_a(o.get("targets"));
		fuels.func_152753_a(o.get("fuels"));
	}
	
	public JsonElement getSerializableElement()
	{
		JsonObject o = new JsonObject();
		o.add("all", all.getSerializableElement());
		o.add("targets", targets.getSerializableElement());
		o.add("fuels", fuels.getSerializableElement());
		return o;
	}
	
	public static class BlacklistEntryList implements IJsonSerializable
	{
		private final Set<String> oreNames;
		private final Set<ItemEntry> regNames;
		
		public BlacklistEntryList()
		{
			oreNames = new HashSet<>();
			regNames = new HashSet<>();
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
		
		public void func_152753_a(JsonElement p_152753_1_)
		{
		}
		
		public JsonElement getSerializableElement()
		{
			JsonObject o = new JsonObject();
			return o;
		}
	}
}