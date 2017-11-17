package com.latmod.emc_condenser.block;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.lib.config.ConfigBoolean;
import com.feed_the_beast.ftbl.lib.config.ConfigEnum;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import com.feed_the_beast.ftbl.lib.tile.EnumSaveType;
import com.feed_the_beast.ftbl.lib.tile.TileBase;
import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.feed_the_beast.ftbl.lib.util.misc.EnumIO;
import com.feed_the_beast.ftbl.lib.util.misc.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.util.misc.EnumRedstoneMode;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.latmod.emc_condenser.EMCCConfig;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileConstructor extends TileBase implements ITickable
{
	public static final int BUTTON_TRANSFER_ITEMS = 0;
	public static final int BUTTON_SECURITY_LEFT = 1;
	public static final int BUTTON_SECURITY_RIGHT = 2;
	public static final int BUTTON_REDSTONE_MODE_LEFT = 3;
	public static final int BUTTON_REDSTONE_MODE_RIGHT = 4;
	public static final int BUTTON_INVENTORY_MODE_LEFT = 5;
	public static final int BUTTON_INVENTORY_MODE_RIGHT = 6;
	public static final int BUTTON_SAFE_MODE = 7;

	public UUID ownerId;
	public int storedEMC = 0;
	public int cooldown = 0;

	public final ConfigBoolean safe_mode = new ConfigBoolean(true);
	public final ConfigEnum<EnumRedstoneMode> redstone_mode = new ConfigEnum<>(EnumRedstoneMode.NAME_MAP);
	public final ConfigEnum<EnumIO> inv_mode = new ConfigEnum<>(EnumIO.NAME_MAP);
	public final ConfigEnum<EnumPrivacyLevel> security = new ConfigEnum<>(EnumPrivacyLevel.NAME_MAP);

	public final ItemStackHandler target, input, output;

	public TileConstructor()
	{
		target = new ItemStackHandler(1);
		input = new ItemStackHandler(36);
		output = new ItemStackHandler(18);
		checkForced();
	}

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
			if (facing == EnumFacing.UP)
			{
				return (T) target;
			}
			else if (facing == EnumFacing.DOWN)
			{
				return (T) output;
			}

			return (T) input;
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.writeItemHandler(nbt, "Target", target);
		InvUtils.writeItemHandler(nbt, "Input", input);
		InvUtils.writeItemHandler(nbt, "Output", output);

		if (storedEMC > 0)
		{
			nbt.setInteger("StoredEMC", storedEMC);
		}

		if (safe_mode.getBoolean())
		{
			nbt.setBoolean("SafeMode", true);
		}

		EnumRedstoneMode.NAME_MAP.writeToNBT(nbt, "RedstoneMode", type, redstone_mode.getValue());
		EnumIO.NAME_MAP.writeToNBT(nbt, "InvMode", type, inv_mode.getValue());

		if (cooldown > 0)
		{
			nbt.setShort("Cooldown", (short) cooldown);
		}
	}

	@Override
	public void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		InvUtils.readItemHandler(nbt, "Target", target);
		InvUtils.readItemHandler(nbt, "Input", input);
		InvUtils.readItemHandler(nbt, "Output", output);
		storedEMC = nbt.getInteger("StoredEMC");
		safe_mode.setBoolean(nbt.getBoolean("SafeMode"));
		redstone_mode.setValue(EnumRedstoneMode.NAME_MAP.readFromNBT(nbt, "RedstoneMode", type));
		inv_mode.setValue(EnumIO.NAME_MAP.readFromNBT(nbt, "InvMode", type));
		cooldown = nbt.getShort("Cooldown");
		checkForced();
	}

	@Override
	public void update()
	{
		if (!isServerSide())
		{
			return;
		}

		if (cooldown > 0)
		{
			cooldown--;
			return;
		}

		cooldown = EMCCConfig.condenser.sleep_delay;

		/*FIXME if (redstone_mode.getValue().cancel(redstonePowered))
		{
			return;
		}*/

		int limit = EMCCConfig.condenser.limit_per_tick;
		if (limit == -1)
		{
			limit = input.getSlots() * 64;
		}

		for (int i = 0; i < input.getSlots(); i++)
		{
			ItemStack in = input.getStackInSlot(i);

			if (!in.isEmpty())
			{
				if (in.getItem() instanceof IEmcStorageItem)
				{
					IEmcStorageItem storageItem = (IEmcStorageItem) in.getItem();

					double ev = storageItem.getStoredEmc(in);

					storedEMC += ev;
					storageItem.setStoredEmc(in, 0);
					markDirty();
					continue;
				}

				int iev = EMCValues.getDestructionEMC(in).value;

				if (iev > 0)
				{
					if (safe_mode.getAsBoolean() && in.getCount() == 1)
					{
						continue;
					}

					int s = Math.min((safe_mode.getAsBoolean() && in.getCount() > 1) ? (in.getCount() - 1) : in.getCount(), limit);

					if (s <= 0)
					{
						continue;
					}

					limit -= s;
					storedEMC += iev * s;
					in.shrink(s);
					input.setStackInSlot(i, in.isEmpty() ? ItemStack.EMPTY : in);
					markDirty();
				}
			}

			if (limit <= 0)
			{
				break;
			}
		}

		ItemStack tar = target.getStackInSlot(0);
		if (storedEMC > 0 && !tar.isEmpty())
		{
			if (tar.getItem() instanceof IEmcStorageItem)
			{
				IEmcStorageItem storageItem = (IEmcStorageItem) tar.getItem();

				if (storageItem.canChargeEmc(tar))
				{
					int ev = storageItem.getStoredEmc(tar);
					int a = Math.min(Math.min(storedEMC, storageItem.getEmcTransferLimit(tar)), storageItem.getMaxStoredEmc(tar) - ev);

					if (a > 0)
					{
						storageItem.setStoredEmc(tar, ev + a);
						storedEMC -= a;
						markDirty();
					}
				}
			}
			else
			{
				int ev = EMCValues.getConstructionEMC(tar).value;

				if (ev > 0)
				{
					int d1 = storedEMC / ev;

					if (d1 > 0)
					{
						for (int d = 0; d < d1; d++)
						{
							if (ItemHandlerHelper.insertItem(output, ItemHandlerHelper.copyStackWithSize(tar, 1), false).isEmpty())
							{
								storedEMC -= ev;
								markDirty();
							}
							else
							{
								break;
							}
						}
					}
				}
			}
		}
	}

	public void checkForced()
	{
		if (EMCCConfig.condenser_forced.force_redstone_control && redstone_mode.getValue() != EMCCConfig.condenser_forced.redstone_control)
		{
			redstone_mode.setValue(EMCCConfig.condenser_forced.redstone_control);
			markDirty();
		}

		if (EMCCConfig.condenser_forced.force_security && security.getValue() != EMCCConfig.condenser_forced.security)
		{
			security.setValue(EMCCConfig.condenser_forced.security);
			markDirty();
		}

		if (EMCCConfig.condenser_forced.force_safe_mode && safe_mode.getBoolean() != EMCCConfig.condenser_forced.safe_mode)
		{
			safe_mode.setBoolean(EMCCConfig.condenser_forced.safe_mode);
			markDirty();
		}

		if (EMCCConfig.condenser_forced.force_inv_mode && inv_mode.getValue() != EMCCConfig.condenser_forced.inv_mode)
		{
			inv_mode.setValue(EMCCConfig.condenser_forced.inv_mode);
			markDirty();
		}
	}

	public boolean handleButton(EntityPlayer player, int button)
	{
		switch (button)
		{
			case BUTTON_SAFE_MODE:
			{
				safe_mode.toggle();
				return true;
			}
			case BUTTON_REDSTONE_MODE_LEFT:
			case BUTTON_REDSTONE_MODE_RIGHT:
			{
				redstone_mode.onClicked(button == BUTTON_REDSTONE_MODE_LEFT ? MouseButton.LEFT : MouseButton.RIGHT);
				return true;
			}
			case BUTTON_INVENTORY_MODE_LEFT:
			case BUTTON_INVENTORY_MODE_RIGHT:
			{
				inv_mode.onClicked(button == BUTTON_INVENTORY_MODE_LEFT ? MouseButton.LEFT : MouseButton.RIGHT);
				return true;
			}
			case BUTTON_SECURITY_LEFT:
			case BUTTON_SECURITY_RIGHT:
			{
				if (ownerId.equals(player.getGameProfile().getId()))
				{
					security.onClicked(button == BUTTON_SECURITY_LEFT ? MouseButton.LEFT : MouseButton.RIGHT);
					return true;
				}
				else
				{
					IForgePlayer owner = FTBLibAPI.API.getUniverse().getPlayer(ownerId);

					if (owner != null)
					{
						FTBLibLang.OWNER.sendMessage(player, owner.getName());
					}

					return false;
				}
			}
			case BUTTON_TRANSFER_ITEMS:
			{
				boolean ret = false;
/*				int[] invSlots = InvUtils.getPlayerSlots(player);

				for (int i = 0; i < OUTPUT_SLOTS.length; i++)
				{
					if (items[OUTPUT_SLOTS[i]] != null)
					{
						int ss = items[OUTPUT_SLOTS[i]].stackSize;

						for (int j = 0; j < ss; j++)
						{
							if (LMInvUtils.addSingleItemToInv(items[OUTPUT_SLOTS[i]].copy(), player.inventory, invSlots, -1, true))
							{
								items[OUTPUT_SLOTS[i]].stackSize--;
								if (items[OUTPUT_SLOTS[i]].stackSize <= 0)
								{
									items[OUTPUT_SLOTS[i]] = null;
								}

								ret = true;
							}
						}
					}
				}
*/
				return ret;
			}
		}

		return false;
	}

	@Override
	public boolean shouldDrop()
	{
		return super.shouldDrop()
				|| storedEMC > 0
				|| !safe_mode.getBoolean()
				|| !target.getStackInSlot(0).isEmpty()
				|| InvUtils.getFirstItemIndex(input, ItemStack.EMPTY) != -1
				|| InvUtils.getFirstItemIndex(output, ItemStack.EMPTY) != -1;
	}
}