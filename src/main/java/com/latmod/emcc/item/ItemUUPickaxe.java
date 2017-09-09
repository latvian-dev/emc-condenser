package com.latmod.emcc.item;

import com.latmod.emcc.api.EMCCUtils;
import com.latmod.emcc.api.ToolInfusion;
import com.latmod.emcc.config.EMCCConfigTools;
import com.latmod.emcc.item.ItemMaterialsEMCC;
import ftb.lib.api.item.ODItems;
import ftb.lib.api.item.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUPickaxe extends ItemToolEMCC
{
	public ItemUUPickaxe(String s)
	{
		super(s);
		setHarvestLevel(Tool.Type.PICK, Tool.Level.ALUMITE);
	}

	@Override
	public void loadRecipes()
	{
		if (EMCCConfigTools.tools.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		return Items.DIAMOND_PICKAXE.canHarvestBlock(state);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		/*
		if(!isBlazing(tool)) return false;
		return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
		*/
	}

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
	{
		EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
	}

	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{
		return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH);
	}
}