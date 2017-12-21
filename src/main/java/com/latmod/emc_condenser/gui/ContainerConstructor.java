package com.latmod.emc_condenser.gui;

import com.feed_the_beast.ftblib.lib.util.InvUtils;
import com.latmod.emc_condenser.EMCC;
import com.latmod.emc_condenser.block.TileConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerConstructor extends Container
{
	public static final ResourceLocation ID = new ResourceLocation(EMCC.MOD_ID, "constructor");

	public final EntityPlayer player;
	public final TileConstructor constructor;

	public ContainerConstructor(EntityPlayer ep, TileConstructor c)
	{
		player = ep;
		constructor = c;

		addSlotToContainer(new SlotItemHandler(constructor.output, 0, 44, 9));
		addSlotToContainer(new SlotItemHandler(constructor.output, 1, 62, 9));
		addSlotToContainer(new SlotItemHandler(constructor.output, 2, 80, 9));
		addSlotToContainer(new SlotItemHandler(constructor.output, 3, 98, 9));
		addSlotToContainer(new SlotItemHandler(constructor.output, 4, 116, 9));

		addSlotToContainer(new SlotItemHandler(constructor.output, 5, 116, 27));
		addSlotToContainer(new SlotItemHandler(constructor.output, 6, 116, 45));
		addSlotToContainer(new SlotItemHandler(constructor.output, 7, 116, 63));

		addSlotToContainer(new SlotItemHandler(constructor.output, 8, 116, 81));
		addSlotToContainer(new SlotItemHandler(constructor.output, 9, 98, 81));
		addSlotToContainer(new SlotItemHandler(constructor.output, 10, 80, 81));
		addSlotToContainer(new SlotItemHandler(constructor.output, 11, 62, 81));
		addSlotToContainer(new SlotItemHandler(constructor.output, 12, 44, 81));

		addSlotToContainer(new SlotItemHandler(constructor.output, 13, 44, 63));
		addSlotToContainer(new SlotItemHandler(constructor.output, 14, 44, 45));
		addSlotToContainer(new SlotItemHandler(constructor.output, 15, 44, 27));

		InvUtils.addPlayerSlots(this, player, 8, 110, false);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		return InvUtils.transferStackInSlot(this, index, constructor.output.getSlots());
	}

	@Override
	public boolean enchantItem(EntityPlayer player, int id)
	{
		if (id == 0)
		{
			constructor.setTarget(player);
			return true;
		}

		return false;
	}
}