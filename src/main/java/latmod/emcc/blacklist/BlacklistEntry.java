package latmod.emcc.blacklist;
import java.util.List;

import latmod.core.LatCore;
import latmod.core.util.FastList;
import net.minecraft.item.ItemStack;

import com.google.gson.annotations.Expose;

public class BlacklistEntry
{
	@Expose public List<String> ore_dictionary;
	@Expose public List<String> unlocazlied_name;
	private FastList<ItemEntry> un_list;
	
	public static class ItemEntry
	{
		public String item;
		public int damage;
		
		public ItemEntry(String s, int i)
		{
			item = s;
			damage = i;
		}
		
		public boolean equals(Object o)
		{
			ItemStack is = (ItemStack)o;
			return is != null && is.getUnlocalizedName().equals(item) && (damage == LatCore.ANY || is.getItemDamage() == damage);
		}
	}
	
	public BlacklistEntry()
	{
		ore_dictionary = new FastList<String>();
		unlocazlied_name = new FastList<String>();
		un_list = new FastList<ItemEntry>();
	}
	
	public void addOreName(String s)
	{ ore_dictionary.add(s); }
	
	public void addUName(String s, Integer dmg)
	{ unlocazlied_name.add(s + (dmg == null ? "" : ("@" + dmg))); }
	
	public boolean isBlacklistedOre(FastList<String> s)
	{ return !ore_dictionary.isEmpty() && s.containsAny(ore_dictionary); }

	public boolean isBlacklistedUN(ItemStack is)
	{ return !un_list.isEmpty() && un_list.contains(is); }
	
	public void reloadList()
	{
		un_list.clear();
		
		if(!unlocazlied_name.isEmpty()) for(String s : unlocazlied_name)
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
					dmg = LatCore.ANY;
			}
			
			un_list.add(new ItemEntry(uName, dmg));
		}
	}
}