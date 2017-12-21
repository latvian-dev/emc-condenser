package com.latmod.emc_condenser.gui;

import com.feed_the_beast.ftblib.lib.util.InvUtils;
import com.latmod.emc_condenser.EMCC;
import com.latmod.emc_condenser.block.TileDestructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDestructor extends Container
{
	public static final ResourceLocation ID = new ResourceLocation(EMCC.MOD_ID, "destructor");

	public final EntityPlayer player;
	public final TileDestructor destructor;

	public ContainerDestructor(EntityPlayer ep, TileDestructor t)
	{
		player = ep;
		destructor = t;

		addSlotToContainer(new SlotItemHandler(destructor.input, 0, 80, 9));
		addSlotToContainer(new SlotItemHandler(destructor.input, 1, 109, 16));
		addSlotToContainer(new SlotItemHandler(destructor.input, 2, 116, 45));
		addSlotToContainer(new SlotItemHandler(destructor.input, 3, 109, 74));
		addSlotToContainer(new SlotItemHandler(destructor.input, 4, 80, 81));
		addSlotToContainer(new SlotItemHandler(destructor.input, 5, 51, 74));
		addSlotToContainer(new SlotItemHandler(destructor.input, 6, 44, 45));
		addSlotToContainer(new SlotItemHandler(destructor.input, 7, 51, 16));

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
		return InvUtils.transferStackInSlot(this, index, destructor.input.getSlots());
	}
}