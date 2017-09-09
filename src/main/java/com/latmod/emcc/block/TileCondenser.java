package com.latmod.emcc.block;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.lib.EnumIO;
import com.feed_the_beast.ftbl.lib.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.EnumRedstoneMode;
import com.feed_the_beast.ftbl.lib.config.ConfigBoolean;
import com.feed_the_beast.ftbl.lib.config.ConfigEnum;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import com.feed_the_beast.ftbl.lib.tile.EnumSaveType;
import com.feed_the_beast.ftbl.lib.tile.TileInvBase;
import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emcc.Blacklist;
import com.latmod.emcc.EMCCConfig;
import com.latmod.emcc.EMCCItems;
import com.latmod.emcc.VanillaEMC;
import com.latmod.emcc.api.IEmcStorageItem;
import com.latmod.emcc.api.IEmcWrenchable;
import com.latmod.emcc.client.gui.ContainerCondenser;
import com.latmod.emcc.client.gui.GuiCondenser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileCondenser extends TileInvBase implements ITickable, ISidedInventory, IEmcWrenchable
{
	public UUID ownerId;
	public double storedEMC = 0D;
	public int cooldown = 0;

	public final ConfigBoolean safe_mode = new ConfigBoolean(false);
	public final ConfigEnum<EnumRedstoneMode> redstone_mode = new ConfigEnum<>(EnumRedstoneMode.NAME_MAP);
	public final ConfigEnum<EnumIO> inv_mode = new ConfigEnum<>(EnumIO.NAME_MAP);
	public final ConfigEnum<EnumPrivacyLevel> security = new ConfigEnum<>(EnumPrivacyLevel.NAME_MAP);

	public final ItemStackHandler target, input, output;

	public TileCondenser()
	{
		super(SLOT_COUNT);
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
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		nbt.setTag("Items", itemHandler.serializeNBT());
	}

	@Override
	protected void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		itemHandler.deserializeNBT(nbt.getCompoundTag("Items"));
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

		if (redstone_mode.getValue().cancel(redstonePowered))
		{
			return;
		}

		int limit = EMCCConfig.condenser.limit_per_tick;
		if (limit == -1)
		{
			limit = INPUT_SLOTS.length * 64;
		}

		for (int i = 0; i < INPUT_SLOTS.length; i++)
		{
			if (items[INPUT_SLOTS[i]] != null && items[INPUT_SLOTS[i]].stackSize > 0)
			{
				if (items[INPUT_SLOTS[i]].getItem() instanceof IEmcStorageItem)
				{
					IEmcStorageItem storageItem = (IEmcStorageItem) items[INPUT_SLOTS[i]].getItem();

					double ev = storageItem.getStoredEmc(items[INPUT_SLOTS[i]]);

					storedEMC += ev;
					storageItem.setStoredEmc(items[INPUT_SLOTS[i]], 0D);
					markDirty();
					continue;
				}

				double iev = VanillaEMC.INSTANCE.getEMC(items[INPUT_SLOTS[i]]);

				if (iev > 0D && !Blacklist.INSTANCE.isBlacklistedFuel(items[INPUT_SLOTS[i]]))
				{
					if (safe_mode.getAsBoolean() && items[INPUT_SLOTS[i]].stackSize == 1)
					{
						continue;
					}

					int s = Math.min((safe_mode.getAsBoolean() && items[INPUT_SLOTS[i]].stackSize > 1) ? (items[INPUT_SLOTS[i]].stackSize - 1) : items[INPUT_SLOTS[i]].stackSize, limit);

					if (s <= 0)
					{
						continue;
					}

					limit -= s;
					storedEMC += iev * s;
					items[INPUT_SLOTS[i]].stackSize -= s;
					if (items[INPUT_SLOTS[i]].stackSize <= 0)
					{
						items[INPUT_SLOTS[i]] = null;
					}
					markDirty();
				}
			}

			if (limit <= 0)
			{
				break;
			}
		}

		if (storedEMC > 0D && items[SLOT_TARGET] != null)
		{
			ItemStack tar = items[SLOT_TARGET];

			if (tar.getItem() instanceof IEmcStorageItem)
			{
				IEmcStorageItem storageItem = (IEmcStorageItem) tar.getItem();

				if (storageItem.canChargeEmc(tar))
				{
					double ev = storageItem.getStoredEmc(tar);
					double a = Math.min(Math.min(storedEMC, storageItem.getEmcTrasferLimit(tar)), storageItem.getMaxStoredEmc(tar) - ev);

					if (a > 0D)
					{
						storageItem.setStoredEmc(items[SLOT_TARGET], ev + a);
						storedEMC -= a;
						markDirty();
					}
				}
			}
			else
			{
				double ev = VanillaEMC.INSTANCE.getEMC(tar);

				if (ev > 0D && !Blacklist.INSTANCE.isBlacklistedTarget(tar))
				{
					long d1 = (long) (storedEMC / ev);

					if (d1 > 0L)
					{
						for (long d = 0L; d < d1; d++)
						{
							if (LMInvUtils.addSingleItemToInv(ItemHandlerHelper.copyStackWithSize(tar, 1), this, OUTPUT_SLOTS, -1, true))
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

	@Override
	public void readData(NBTTagCompound nbt, EnumSaveType type)
	{
		super.readData(nbt, type);

		storedEMC = nbt.getDouble("StoredEMC");
		safe_mode.setBoolean(nbt.getBoolean("SafeMode"));
		redstone_mode.setValue(EnumRedstoneMode.NAME_MAP.readFromNBT(nbt, "RedstoneMode", type));
		inv_mode.setValue(EnumIO.NAME_MAP.readFromNBT(nbt, "InvMode", type));
		cooldown = nbt.getShort("Cooldown");

		checkForced();
	}

	@Override
	public void writeData(NBTTagCompound nbt, EnumSaveType type)
	{
		super.writeData(nbt, type);

		nbt.setDouble("StoredEMC", storedEMC);
		nbt.setBoolean("SafeMode", safe_mode.getAsBoolean());
		EnumRedstoneMode.NAME_MAP.writeToNBT(nbt, "RedstoneMode", type, redstone_mode.getValue());
		EnumIO.NAME_MAP.writeToNBT(nbt, "InvMode", type, inv_mode.getValue());
		nbt.setShort("Cooldown", (short) cooldown);
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

		if (EMCCConfig.condenser_forced.force_safe_mode && safe_mode.getAsBoolean() != EMCCConfig.condenser_forced.safe_mode)
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

	@Override
	public boolean canWrench(EntityPlayer ep)
	{
		return security.canInteract(ep);
	}

	@Override
	public void readFromWrench(NBTTagCompound tag)
	{
		readTileData(tag);
	}

	@Override
	public void writeToWrench(NBTTagCompound tag)
	{
		writeTileData(tag);
	}

	@Override
	public void onWrenched(EntityPlayer ep, ItemStack is)
	{
		dropItems = false;
	}

	@Override
	public ItemStack getBlockToPlace()
	{
		return new ItemStack(EMCCItems.CONDENSER);
	}

	public void handleButton(String button, IMouseButton mouseButton, EntityPlayer player)
	{
		switch (button)
		{
			case "safe_mode":
			{
				safe_mode.toggle();
				break;
			}
			case "redstone":
			{
				redstone_mode.onClicked(mouseButton);
				break;
			}
			case "inv_mode":
			{
				inv_mode.onClicked(mouseButton);
				break;
			}
			case "security":
			{
				if (ownerId.equals(player.getGameProfile().getId()))
				{
					security.onClicked(mouseButton);
				}
				else
				{
					IForgePlayer owner = FTBLibAPI.API.getUniverse().getPlayer(ownerId);

					if (owner != null)
					{
						FTBLibLang.OWNER.printChat(player, owner.getName());
					}
				}
				break;
			}
			case "transfer_items":
			{
				int[] invSlots = InvUtils.getPlayerSlots(player);

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

								markDirty();
							}
						}
					}
				}
				break;
			}
		}

		checkForced();
		markDirty();
	}

	@Override
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{
		return new ContainerCondenser(ep, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{
		return new GuiCondenser(new ContainerCondenser(ep, this));
	}

	public void dropItems()
	{
		InvUtils.dropAllItems(world, getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, itemHandler);
	}
}