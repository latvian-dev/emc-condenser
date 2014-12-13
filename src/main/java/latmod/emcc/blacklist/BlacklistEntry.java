package latmod.emcc.blacklist;
import java.util.List;

import latmod.core.*;
import net.minecraft.item.ItemStack;

import com.google.gson.annotations.Expose;

public class BlacklistEntry
{
	@Expose public List<String> ore_dictionary;
	@Expose public List<String> registry_name;
	private FastList<ItemEntry> reg_list;
	
	public static class ItemEntry
	{
		public String name;
		public int damage;
		
		public ItemEntry(String s, int i)
		{
			name = s;
			damage = i;
		}
		
		public boolean equals(Object o)
		{
			ItemStack is = (ItemStack)o;
			return is != null && (damage == -1 || is.getItemDamage() == damage) && LatCoreMC.getRegName(is).equals(name);
		}
	}
	
	public BlacklistEntry()
	{
		ore_dictionary = new FastList<String>();
		registry_name = new FastList<String>();
		reg_list = new FastList<ItemEntry>();
	}
	
	public void addOreName(String s)
	{ ore_dictionary.add(s); }
	
	public void addRegistryName(String s, Integer dmg)
	{ registry_name.add(s + (dmg == null ? "" : ("@" + dmg))); }
	
	public boolean isBlacklistedOre(FastList<String> s)
	{ return !ore_dictionary.isEmpty() && s.containsAny(ore_dictionary); }

	public boolean isBlacklistedRegName(ItemStack is)
	{ return !reg_list.isEmpty() && reg_list.contains(is); }
	
	public void reloadList()
	{
		reg_list.clear();
		
		if(!registry_name.isEmpty()) for(String s : registry_name)
		{
			String[] s1 = s.split("@");
			
			String uName = null;
			int dmg = 0;
			
			if(s1 == null || s1.length != 2)
			{
				uName = s;
				dmg = 0;
			}
			else
			{
				uName = s1[0];
				dmg = Integer.parseInt(s1[1]);
				
				if(dmg == -1)
					dmg = LatCoreMC.ANY;
			}
			
			reg_list.add(new ItemEntry(uName, dmg));
		}
	}
}