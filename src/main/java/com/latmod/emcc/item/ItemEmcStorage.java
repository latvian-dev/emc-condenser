package com.latmod.emcc.item;

import com.latmod.emcc.api.IEmcStorageItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class ItemEmcStorage extends ItemEMCC implements IEmcStorageItem
{
	private static final String NBT_KEY = "StoredEMC";

	public ItemEmcStorage(String s)
	{
		super(s);
		setMaxDamage(0);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if (!w.isRemote)
		{
			if (ep.isSneaking())
			{
				int dmg = is.getItemDamage();

				if (dmg == 0 || dmg == 1)
				{
					is.setItemDamage(dmg == 0 ? 1 : 0);
				}

				w.playSound(null, ep.posX, ep.posY, ep.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.3F, (dmg % 2 == 0) ? 1F : 0.5F);
			}
		}

		return is;
	}

	@Override
	public boolean canChargeEmc(ItemStack is)
	{
		return true;
	}

	@Override
	public void setStoredEmc(ItemStack is, double emc)
	{
		if (emc <= 0D)
		{
			if (is.hasTagCompound())
			{
				is.getTagCompound().removeTag(NBT_KEY);

				if (is.getTagCompound().hasNoTags())
				{
					is.setTagCompound(null);
				}
			}
		}
		else
		{
			is.setTagInfo(NBT_KEY, new NBTTagDouble(emc));
		}
	}

	@Override
	public double getStoredEmc(ItemStack is)
	{
		return is.hasTagCompound() ? is.getTagCompound().getDouble(NBT_KEY) : 0D;
	}

	public void onEquipped(ItemStack is, EntityLivingBase ep)
	{
	}

	public void onUnequipped(ItemStack is, EntityLivingBase ep)
	{
	}

	public boolean canEquip(ItemStack is, EntityLivingBase ep)
	{
		return true;
	}

	public boolean canUnequip(ItemStack is, EntityLivingBase ep)
	{
		return true;
	}
}