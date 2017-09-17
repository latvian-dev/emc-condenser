package com.latmod.emc_condenser.item;

import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemEmcBattery extends ItemEmcStorage// implements IBauble
{
	public ItemEmcBattery(String s)
	{
		super(s);
	}

	@Override
	public boolean canDischargeEmc(ItemStack stack)
	{
		return true;
	}

	@Override
	public int getMaxStoredEmc(ItemStack stack)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public int getEmcTransferLimit(ItemStack stack)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
			ItemStack fullStack = new ItemStack(this);
			setStoredEmc(fullStack, Integer.MAX_VALUE);
			setActive(fullStack, true);
			items.add(fullStack);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (!world.isRemote && entity instanceof EntityPlayer)
		{
			onWornTick(stack, (EntityPlayer) entity);
		}
	}

	/*
	@Override
	@Optional.Method(modid = OtherMods.BAUBLES)
	public BaubleType getBaubleType(ItemStack is)
	{
		return BaubleType.AMULET;
	}
	*/

	//@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity)
	{
		if (entity.world.isRemote || !(entity instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) entity;

		if (entity.world.getWorldTime() % 8 == 0 && isActive(stack))
		{
			chargeInv(stack, player, player.inventory);

			IInventory baubInv = InvUtils.getBaubles(player);
			if (baubInv != null)
			{
				chargeInv(stack, player, baubInv);
			}
		}
	}

	public void chargeInv(ItemStack stack, EntityPlayer player, IInventory inventory)
	{
		int emc = getStoredEmc(stack);

		if (emc <= 0)
		{
			return;
		}
		boolean changed = false;

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack is1 = inventory.getStackInSlot(i);

			if (is1.getItem() instanceof IEmcStorageItem && is1.getItem() != this)
			{
				IEmcStorageItem si = (IEmcStorageItem) is1.getItem();

				int max = si.getMaxStoredEmc(is1);

				if (max > 0)
				{
					int siEmc = si.getStoredEmc(is1);

					int a = Math.min(si.getEmcTransferLimit(is1), max - siEmc);

					if (a > 0 && emc >= a)
					{
						emc -= a;
						setStoredEmc(stack, emc);
						si.setStoredEmc(is1, siEmc + a);
						inventory.setInventorySlotContents(i, is1);
						changed = true;
					}
				}
			}
		}

		if (changed)
		{
			inventory.markDirty();
			player.openContainer.detectAndSendChanges();
		}
	}
}