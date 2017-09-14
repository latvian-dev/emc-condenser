package com.latmod.emc_condenser.client.gui;

import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emc_condenser.EMCC;
import com.latmod.emc_condenser.block.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCondenser extends Container
{
	public static final ResourceLocation ID = new ResourceLocation(EMCC.MOD_ID, "condenser");

	public final EntityPlayer player;
	public final TileCondenser condenser;

	public ContainerCondenser(EntityPlayer ep, TileCondenser t)
	{
		player = ep;
		condenser = t;

		addSlotToContainer(new SlotItemHandler(condenser.target, 0, 8, 9));

		for (int i = 0; i < condenser.input.getSlots(); i++)
		{
			int x = i % 9;
			int y = i / 9;

			addSlotToContainer(new SlotItemHandler(condenser.input, i, 8 + x * 18, 32 + y * 18));
		}

		for (int i = 0; i < condenser.output.getSlots(); i++)
		{
			int x = i % 9;
			int y = i / 9;

			addSlotToContainer(new SlotItemHandler(condenser.output, i, 8 + x * 18, 107 + y * 18));
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

			int maxSlot = condenser.target.getSlots() + condenser.input.getSlots();

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

	@Override
	public boolean enchantItem(EntityPlayer player, int id)
	{
		if (condenser.handleButton(player, id))
		{
			condenser.checkForced();
			condenser.markDirty();
			return true;
		}

		return false;
	}
}