package com.latmod.emc_condenser.block;

import com.feed_the_beast.ftbl.lib.tile.EnumSaveType;
import com.feed_the_beast.ftbl.lib.tile.TileBase;
import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emc_condenser.util.EMCValue;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileConstructor extends TileBase implements ITickable
{
	public long emc = 0;
	public ItemStack target = ItemStack.EMPTY;

	public final ItemStackHandler output = new ItemStackHandler(16)
	{
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			return stack;
		}
	};

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
			return (T) output;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.writeItemHandler(nbt, "Output", output);

		if (emc > 0)
		{
			nbt.setLong("EMC", emc);
		}

		if (!target.isEmpty())
		{
			nbt.setTag("Target", target.serializeNBT());
		}
	}

	@Override
	public void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.readItemHandler(nbt, "Output", output);
		emc = nbt.getLong("EMC");
		target = nbt.hasKey("Target") ? new ItemStack(nbt.getCompoundTag("Target")) : ItemStack.EMPTY;
	}

	@Override
	public void update()
	{
		if (isServerSide() && !target.isEmpty())
		{
			EMCValue targetEMC = EMCValues.getConstructionEMC(target);

			if (!targetEMC.isEmpty())
			{
				for (EnumFacing facing : EnumFacing.VALUES)
				{
					TileEntity tileEntity = world.getTileEntity(pos.offset(facing));

					if (tileEntity instanceof TileDestructor)
					{
						TileDestructor destructor = (TileDestructor) tileEntity;

						if (destructor.emc > 0)
						{
							emc += destructor.emc;
							markDirty();
							destructor.emc = 0;
							destructor.markDirty();
						}
					}
				}

				if (emc >= targetEMC.value && ItemHandlerHelper.insertItem(output, target, true).isEmpty())
				{
					emc -= targetEMC.value;
					ItemHandlerHelper.insertItem(output, target.copy(), false);
					markDirty();
				}
			}
		}

		checkIfDirty();
	}

	@Override
	public boolean shouldDrop()
	{
		return emc > 0 || !target.isEmpty() || InvUtils.getFirstItemIndex(output, ItemStack.EMPTY) != -1;
	}

	public void setTarget(EntityPlayer player)
	{
		target = ItemHandlerHelper.copyStackWithSize(player.inventory.getItemStack(), 1);
		markDirty();
	}
}