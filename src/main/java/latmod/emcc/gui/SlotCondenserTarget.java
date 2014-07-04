package latmod.emcc.gui;
import net.minecraft.inventory.*;

public class SlotCondenserTarget extends Slot
{
	public SlotCondenserTarget(IInventory inv, int id, int x, int y)
	{ super(inv, id, x, y); }
	
	public int getSlotStackLimit()
	{ return 1; }
}