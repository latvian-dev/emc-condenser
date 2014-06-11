package mods.emcc;
import latmod.core.*;
import cpw.mods.fml.common.event.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.oredict.*;

public class EMCCBlacklist
{
	public Configuration config;
	
	public String[] defaultOres =
	{
		"oreIron",
		"oreGold",
		"oreCoal",
		"oreRedstone",
		"oreLapis",
		"oreEmerald",
		"oreQuartz",
		"oreTin",
		"oreCopper",
		"oreUranium",
		"oreNickel",
		"oreLead",
		"oreSilver",
		"oreSapphire",
		"oreRuby",
	};
	
	public final FastList<String> targets_od = new FastList<String>();
	public final FastList<ItemEntry> targets_ul = new FastList<ItemEntry>();
	
	public final FastList<String> fuels_od = new FastList<String>();
	public final FastList<ItemEntry> fuels_ul = new FastList<ItemEntry>();
	
	public final FastList<String> all_od = new FastList<String>();
	public final FastList<ItemEntry> all_ul = new FastList<ItemEntry>();
	
	public static class ItemEntry
	{
		public String item;
		public int damage;
		
		public ItemEntry(String s, int i)
		{
			item = s;
			damage = i;
		}
		
		public int hashCode()
		{ return item.hashCode() ^ (damage * 32); }
		
		public boolean equals(Object o)
		{
			ItemStack is = (ItemStack)o;
			return is != null && is.getUnlocalizedName().equals(item) && (damage == -1 || is.getItemDamage() == damage);
		}
	}
	
	public EMCCBlacklist()
	{
	}
	
	public void preInit(FMLPreInitializationEvent e)
	{
		config = LatCore.loadConfig(e, "/LatMod/EMC_Condenser_Blacklist.cfg");
		
		loadInto("blacklist_targets", targets_od, new String[0], targets_ul);
		loadInto("blacklist_fuels", fuels_od, new String[0], fuels_ul);
		loadInto("blacklist_all", all_od, defaultOres, all_ul);
		
		if(config.hasChanged())
			config.save();
	}
	
	private void loadInto(String cat, FastList<String> od, String[] defOD, FastList<ItemEntry> ul)
	{
		String[] bod = config.get(cat, "ore_dictionary", defOD).getStringList();
		if(bod != null && bod.length > 0) od.addAll(bod);
		
		String[] bul = config.get(cat, "unlocalized", new String[0]).getStringList();
		if(bul != null && bul.length > 0)
		{
			for(String s : bul)
			{
				String[] s1 = s.split(" ");
				
				String name = null;
				int damage = 0;
				
				if(s1 != null && s1.length == 2)
				{
					name = s1[0];
					damage = Integer.parseInt(s1[1]);
				}
				else
				{
					name = s;
				}
				
				if(name != null)
					ul.add(new ItemEntry(name, damage));
			}
		}
	}
	
	//private static boolean isOreDisabled(String s)
	//{ return config.get("ore_dictionary", s, true).getBoolean(true); }
	
	public boolean isBlacklistedFuel(ItemStack is)
	{
		if(fuels_ul.contains(is) || all_ul.contains(is)) return true;
		
		FastList<String> oreNames = OreHelper.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(fuels_od.containsAny(oreNames) || all_od.containsAny(oreNames))
				return true;
		}
		
		return false;
	}
	
	public boolean isBlacklistedTarget(ItemStack is)
	{
		if(targets_ul.contains(is) || all_ul.contains(is)) return true;
		
		FastList<String> oreNames = OreHelper.getOreNames(is);
		
		if(oreNames != null && oreNames.size() > 0)
		{
			if(targets_od.containsAny(oreNames) || all_od.containsAny(oreNames))
				return true;
		}
		
		return false;
	}
	
}