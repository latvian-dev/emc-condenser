package latmod.emcc.gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class SlotRestricted extends Slot
{
	public SlotRestricted(IInventory inv, int id, int x, int y)
	{ super(inv, id, x, y); }
	
	public boolean isItemValid(ItemStack is)
	{ return false; }
	
	public boolean canTakeStack(EntityPlayer ep)
	{ return false; }
}