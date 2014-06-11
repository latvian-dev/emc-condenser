package mods.emcc.gui;
import mods.emcc.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class ContainerCondenser extends Container
{
	public TileCondenser tile;
	public EntityPlayer player;
	
	public ContainerCondenser(EntityPlayer ep, TileCondenser t)
	{
		tile = t;
		player = ep;
		
		addSlotToContainer(new Slot(t, TileCondenser.UP_SLOT, 8, 9));
		
		for(int i = 0; i < TileCondenser.SIDE_SLOTS.length; i++)
		{
			int x = i % 9;
			int y = i / 9;
			
			addSlotToContainer(new Slot(t, TileCondenser.SIDE_SLOTS[i], 8 + x * 18, 36 + y * 18));
		}
		
		for(int y = 0; y < 3; y++) for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x + y * 9 + 9, 8 + x * 18, 140 + y * 18));
		
		for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x, 8 + x * 18, 198));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ep)
	{ return true; }
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
}