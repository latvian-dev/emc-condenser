package latmod.emcc.gui;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot
{
	public SlotOutput(IInventory inv, int id, int x, int y)
	{ super(inv, id, x, y); }
	
	public boolean isItemValid(ItemStack is)
	{ return false; }
}