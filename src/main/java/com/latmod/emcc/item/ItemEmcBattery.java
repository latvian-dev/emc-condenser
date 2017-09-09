package com.latmod.emcc.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.latmod.emcc.api.IEmcStorageItem;
import com.latmod.emcc.config.EMCCConfigTools;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.BaublesHelper;
import ftb.lib.OtherMods;
import ftb.lib.api.item.ODItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemEmcBattery extends ItemEmcStorage implements IBauble
{
	public ItemEmcBattery(String s)
	{
		super(s);
	}

	@Override
	public boolean canDischargeEmc(ItemStack is)
	{
		return true;
	}

	@Override
	public double getMaxStoredEmc(ItemStack is)
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getEmcTrasferLimit(ItemStack is)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public void loadRecipes()
	{
		if (EMCCConfigTools.battery.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "QRQ", "QUQ", "QGQ", 'Q', ODItems.QUARTZ, 'R', ODItems.REDSTONE, 'G', ODItems.GLOWSTONE, 'U', ItemMaterialsEMCC.ITEM_UUS);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs c, List l)
	{
		l.add(new ItemStack(item, 1, 0));
		ItemStack is1 = new ItemStack(item, 1, 1);
		is1.stackTagCompound = new NBTTagCompound();
		setStoredEmc(is1, Double.POSITIVE_INFINITY);
		l.add(is1);
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{
		if (!w.isRemote && e instanceof EntityPlayer)
		{
			onWornTick(is, (EntityPlayer) e);
		}
	}

	@Override
	@Optional.Method(modid = OtherMods.BAUBLES)
	public BaubleType getBaubleType(ItemStack is)
	{
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack is, EntityLivingBase el)
	{
		if (el.worldObj.isRemote || !(el instanceof EntityPlayer))
		{
			return;
		}

		EntityPlayer ep = (EntityPlayer) el;

		if (is.getItemDamage() == 1 && (el.worldObj.getWorldTime() % 8 == 0))
		{
			if (!EMCCConfigTools.battery.getAsBoolean())
			{
				return;
			}

			chargeInv(is, ep, ep.inventory);
			IInventory baubInv = BaublesHelper.getBaubles(ep);
			if (baubInv != null)
			{
				chargeInv(is, ep, baubInv);
			}
		}
	}

	public void chargeInv(ItemStack is, EntityPlayer ep, IInventory inv)
	{
		double emc = getStoredEmc(is);

		if (emc <= 0D)
		{
			return;
		}
		boolean changed = false;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack is1 = inv.getStackInSlot(i);

			if (is1 != null && is1.getItem() instanceof IEmcStorageItem && is1.getItem() != this)
			{
				IEmcStorageItem si = (IEmcStorageItem) is1.getItem();

				double max = si.getMaxStoredEmc(is1);

				if (max > 0D)
				{
					double siEmc = si.getStoredEmc(is1);

					double a = Math.min(si.getEmcTrasferLimit(is1), max - siEmc);

					if (a > 0D && emc >= a)
					{
						emc -= a;
						setStoredEmc(is, emc);
						si.setStoredEmc(is1, siEmc + a);
						inv.setInventorySlotContents(i, is1);
						changed = true;
					}
				}
			}
		}

		if (changed)
		{
			inv.markDirty();
			ep.openContainer.detectAndSendChanges();
		}
	}
}