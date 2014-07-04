package latmod.emcc.tile;
import latmod.core.InvUtils;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class InventoryRestricted implements IInventory
{
	public final ItemStack[] items;
	public final TileCondenser tile;
	
	public InventoryRestricted(TileCondenser t)
	{
		items = new ItemStack[3];
		tile = t;
	}
	
	@Override
	public int getSizeInventory()
	{ return items.length; }

	@Override
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }

	@Override
	public ItemStack decrStackSize(int i, int j)
	{ return InvUtils.decrStackSize(this, i, j); }

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{ return InvUtils.getStackInSlotOnClosing(this, i); }

	@Override
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; }

	@Override
	public String getInvName()
	{ return ""; }

	@Override
	public boolean isInvNameLocalized()
	{ return false; }

	@Override
	public int getInventoryStackLimit()
	{ return 1; }

	@Override
	public void onInventoryChanged()
	{ tile.onRestrictedInvChanged(); }

	@Override
	public boolean isUseableByPlayer(EntityPlayer ep)
	{ return true; }

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return is.getItem() == Item.nameTag; }
}