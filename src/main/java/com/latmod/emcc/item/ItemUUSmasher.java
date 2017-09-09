package com.latmod.emcc.item;

import com.latmod.emcc.EMCCConfig;
import com.latmod.emcc.EMCCItems;
import com.latmod.emcc.api.ToolInfusion;
import ftb.lib.api.item.ODItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSmasher extends ItemUUPickaxe
{
	public ItemUUSmasher(String s)
	{
		super(s);

		setHarvestLevel("pickaxe", 4);
		setHarvestLevel("shovel", 4);
		setHarvestLevel("axe", 4);
	}

	@Override
	public void loadRecipes()
	{
		if (EMCCConfig.tools.tools)
		{
			getMod().recipes.addRecipe(new ItemStack(this), "APA", "BVB", " S ", 'B', EMCCItems.UNUNSEPTIUM_BLOCK, 'S', ODItems.STICK, 'P', EMCCItems.PICKAXE, 'A', EMCCItems.AXE, 'V', EMCCItems.SHOVEL);
		}
	}

	@Override
	public boolean isEffective(Block b)
	{
		return EMCCItems.PICKAXE.getStrVsBlock(b) || EMCCItems.SHOVEL.isEffective(b) || EMCCItems.AXE.isEffective(b);
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		is.damageItem(1, el1);
		return true;
	}

	//public float getStrVsBlock(ItemStack is, Block b)
	//{ return efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F); }

	@Override
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		//return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
	{
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		//else return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
	}

	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{
		return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH);
	}
}