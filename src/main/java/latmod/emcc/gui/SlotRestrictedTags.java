package latmod.emcc.gui;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class SlotRestrictedTags extends Slot
{
	public SlotRestrictedTags(IInventory inv, int id, int x, int y)
	{ super(inv, id, x, y); }
	
	public boolean isItemValid(ItemStack is)
	{ return is.getItem() == Item.nameTag; }
}