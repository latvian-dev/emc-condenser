package com.latmod.emcc.client.gui;

import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emcc.block.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCondenser extends Container
{
	public final EntityPlayer player;
	public final TileCondenser condenser;

	public ContainerCondenser(EntityPlayer p, TileCondenser t)
	{
		player = p;
		condenser = t;

		addSlotToContainer(new Slot(condenser, TileCondenser.SLOT_TARGET, 8, 9));

		for (int i = 0; i < TileCondenser.INPUT_SLOTS.length; i++)
		{
			int x = i % 9;
			int y = i / 9;

			addSlotToContainer(new Slot(condenser, TileCondenser.INPUT_SLOTS[i], 8 + x * 18, 32 + y * 18));
		}

		for (int i = 0; i < TileCondenser.OUTPUT_SLOTS.length; i++)
		{
			int x = i % 9;
			int y = i / 9;

			addSlotToContainer(new Slot(condenser, TileCondenser.OUTPUT_SLOTS[i], 8 + x * 18, 107 + y * 18));
		}

		InvUtils.addPlayerSlots(this, player, 8, 154, false);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack is = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();

			int maxSlot = TileCondenser.SLOT_COUNT - TileCondenser.OUTPUT_SLOTS.length;

			if (index < maxSlot)
			{
				if (!mergeItemStack(is1, maxSlot, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!mergeItemStack(is1, 0, maxSlot, false))
			{
				return ItemStack.EMPTY;
			}

			if (is1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return is;
	}
}