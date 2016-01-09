package latmod.emcc.blacklist;

import com.google.gson.annotations.Expose;
import ftb.lib.item.*;
import latmod.lib.LMListUtils;
import net.minecraft.item.*;

import java.util.*;

public class BlacklistEntry
{
	@Expose
	public List<String> ore_dictionary;
	@Expose
	public List<String> registry_name;
	private final ArrayList<ItemEntry> reg_list;
	
	public static class ItemEntry
	{
		public Item item;
		public int damage;
		
		public ItemEntry(Item s, int i)
		{
			item = s;
			damage = i;
		}
		
		public boolean equals(Object o)
		{
			ItemStack is = (ItemStack) o;
			return is != null && (damage == -1 || is.getItemDamage() == damage) && is.getItem() == item;
		}
	}
	
	public BlacklistEntry()
	{
		ore_dictionary = new ArrayList<>();
		registry_name = new ArrayList<>();
		reg_list = new ArrayList<>();
	}
	
	public void addOreName(String s)
	{ ore_dictionary.add(s); }
	
	public void addRegistryName(String s, Integer dmg)
	{ registry_name.add(s + (dmg == null ? "" : ("@" + dmg))); }
	
	public boolean isBlacklistedOre(List<String> s)
	{ return !ore_dictionary.isEmpty() && LMListUtils.containsAny(s, ore_dictionary); }

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
				uName = s1[ 0 ];
				dmg = Integer.parseInt(s1[ 1 ]);
				if(dmg == -1) dmg = ODItems.ANY;
			}
			
			Item i = LMInvUtils.getItemFromRegName(uName);
			if(i != null) reg_list.add(new ItemEntry(i, dmg));
		}
	}
}