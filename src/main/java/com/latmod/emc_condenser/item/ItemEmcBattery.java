package com.latmod.emc_condenser.item;

import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

//@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemEmcBattery extends ItemEMCC implements IEmcStorageItem// implements IBauble
{
	public ItemEmcBattery(String s)
	{
		super(s);
		setMaxStackSize(1);

		addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter()
		{
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return isActive(stack) ? 1F : 0F;
			}
		});
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (player.isSneaking())
		{
			if (!world.isRemote)
			{
				boolean active = isActive(stack);
				setActive(stack, !active);
				world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1F, active ? 0.5F : 1F);
			}

			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult<>(EnumActionResult.PASS, stack);
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