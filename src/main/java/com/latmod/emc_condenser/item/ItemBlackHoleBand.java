package com.latmod.emc_condenser.item;

import com.latmod.emc_condenser.EMCCConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

//@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemBlackHoleBand extends ItemEmcStorage// implements IBauble
{
	public ItemBlackHoleBand(String s)
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
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (entity != null && entity instanceof EntityPlayer)
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

		EntityPlayer player = (EntityPlayer) entity;

		if (entity.world.getWorldTime() % 4 == 0 && isActive(stack))
		{
			int emc = getStoredEmc(stack);
			int itemCost = EMCCConfig.tools.black_hole_stone_item;
			if (itemCost == -1 || emc < itemCost)
			{
				return;
			}
			double r = EMCCConfig.tools.black_hole_stone_range;

			List<EntityItem> items = player.world.getEntitiesWithinAABB(EntityItem.class, player.getEntityBoundingBox().expand(r, r, r));

			for (EntityItem item : items)
			{
				item.onCollideWithPlayer(player);

				if (item.isDead)
				{
					entity.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, item.posX, item.posY + item.height / 2F, item.posZ, 0D, 0D, 0D);
					emc -= itemCost;
					setStoredEmc(stack, emc);
				}
			}
		}
	}

	/*
	@Override
	@Optional.Method(modid = OtherMods.BAUBLES)
	public BaubleType getBaubleType(ItemStack is)
	{
		return BaubleType.BELT;
	}
	*/
}
