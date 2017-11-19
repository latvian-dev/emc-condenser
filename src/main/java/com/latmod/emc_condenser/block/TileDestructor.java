package com.latmod.emc_condenser.block;

import com.feed_the_beast.ftbl.lib.tile.EnumSaveType;
import com.feed_the_beast.ftbl.lib.tile.TileBase;
import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emc_condenser.EMCCConfig;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileDestructor extends TileBase implements ITickable
{
	public int emc = 0;
	public int cooldown = 0;
	public final ItemStackHandler input = new ItemStackHandler(8);

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) input;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.writeItemHandler(nbt, "Input", input);

		if (emc > 0)
		{
			nbt.setInteger("EMC", emc);
		}

		if (cooldown > 0)
		{
			nbt.setShort("Cooldown", (short) cooldown);
		}
	}

	@Override
	public void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.readItemHandler(nbt, "Input", input);
		emc = nbt.getInteger("EMC");
		cooldown = nbt.getShort("Cooldown");
	}

	@Override
	public void update()
	{
		if (cooldown > 0)
		{
			cooldown--;
			return;
		}

		cooldown = EMCCConfig.destructor.cooldown;

		if (isServerSide())
		{
			for (int i = input.getSlots() - 1; i >= 0; i--)
			{
				ItemStack in = input.getStackInSlot(i);

				if (!in.isEmpty())
				{
					int iev = EMCValues.getDestructionEMC(in).value;

					if (iev > 0 && emc + iev <= EMCCConfig.destructor.max_emc)
					{
						emc += iev;
						in.shrink(1);
						markDirty();
						break;
					}
				}
			}
		}

		checkIfDirty();
	}

	@Override
	public boolean shouldDrop()
	{
		return emc > 0 || InvUtils.getFirstItemIndex(input, ItemStack.EMPTY) != -1;
	}
}