package latmod.emcc.api;

import ftb.lib.api.item.LMInvUtils;
import latmod.lib.*;
import net.minecraft.item.*;

/**
 * Created by LatvianModder on 10.04.2016.
 */
public class ItemEntry implements IIDObject
{
	private final String ID;
	public final Item item;
	public final int damage;
	
	public ItemEntry(Item i, int dmg)
	{
		item = i;
		damage = dmg;
		ID = LMInvUtils.getRegName(item) + '@' + damage;
	}
	
	public ItemEntry(ItemStack is)
	{
		this(is.getItem(), is.getItemDamage());
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof ItemStack)
		{
			ItemStack is = (ItemStack) o;
			return is != null && (damage == -1 || is.getItemDamage() == damage) && is.getItem() == item;
		}
		
		return LMUtils.getID(o).equals(ID);
	}
	
	public String getID()
	{ return ID; }
	
	public int hashCode()
	{ return ID.hashCode(); }
}