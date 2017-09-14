package com.latmod.emc_condenser.item;

import com.latmod.emc_condenser.EMCCConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

//@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemLifeRing extends ItemEmcStorage// implements IBauble
{
	public ItemLifeRing(String s)
	{
		super(s);
	}

	@Override
	public int getMaxStoredEmc(ItemStack stack)
	{
		return Short.MAX_VALUE;
	}

	@Override
	public int getEmcTransferLimit(ItemStack stack)
	{
		return 1024;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.isSneaking() && player.isBurning())
		{
			ItemStack stack = player.getHeldItem(hand);
			int emc = getStoredEmc(stack);

			if (emc >= EMCCConfig.tools.life_stone_extinguish)
			{
				if (!world.isRemote)
				{
					setStoredEmc(stack, emc - EMCCConfig.tools.life_stone_extinguish);
					player.extinguish();
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.PLAYERS, 1F, 1F);
				}

				return new ActionResult<>(EnumActionResult.SUCCESS, stack);
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (entity instanceof EntityPlayer && !world.isRemote)
		{
			onWornTick(stack, (EntityPlayer) entity);
		}
	}

	public void onWornTick(ItemStack stack, EntityLivingBase entity)
	{
		if (!(entity instanceof EntityPlayer) || entity.world.isRemote)
		{
			return;
		}

		int food = EMCCConfig.tools.life_stone_food;
		int hp1 = EMCCConfig.tools.life_stone_1hp;

		if (hp1 == -1 && food == -1)
		{
			return;
		}

		EntityPlayer player = (EntityPlayer) entity;

		if (entity.world.getWorldTime() % 8 == 0 && isActive(stack))
		{
			int emc = getStoredEmc(stack);

			if (food != -1D && emc >= food && player.getFoodStats().needFood())
			{
				player.getFoodStats().addStats(1, 0.6F);
				emc -= food;
				setStoredEmc(stack, emc);
			}

			float hp = player.getHealth();
			float maxHp = player.getMaxHealth();

			if (hp1 != -1 && hp < maxHp && emc >= hp1)
			{
				player.setHealth(hp + 1F);
				emc -= hp1;
				setStoredEmc(stack, emc);
			}
		}
	}

	/*
	@Override
	@Optional.Method(modid = "baubles")
	public BaubleType getBaubleType(ItemStack stack)
	{
		return BaubleType.RING;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase entity)
	{
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity)
	{
	}

	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase entity)
	{
		return true;
	}
	*/
}
