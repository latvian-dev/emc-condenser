package latmod.emcc.blacklist;

import com.google.gson.JsonObject;
import net.minecraft.item.*;

public class Blacklist
{
	public final BlacklistEntryList all;
	public final BlacklistEntryList targets;
	public final BlacklistEntryList fuels;
	public final BlacklistEntryList example;
	
	public Blacklist(JsonObject o)
	{
		all = new BlacklistEntryList();
		targets = new BlacklistEntryList();
		fuels = new BlacklistEntryList();
		example = new BlacklistEntryList();
	}
	
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
	
	public static class BlacklistEntryList
	{
	}
}