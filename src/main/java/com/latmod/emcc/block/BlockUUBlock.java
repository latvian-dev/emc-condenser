package com.latmod.emcc.block;

import com.latmod.emcc.EMCCItems;
import com.latmod.emcc.config.EMCCConfigGeneral;
import com.latmod.emcc.item.ItemMaterialsEMCC;
import ftb.lib.api.item.ODItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockUUBlock extends BlockEMCC
{
	public BlockUUBlock(String s)
	{
		super(s, Material.rock);
	}

	@Override
	public float getEnchantPowerBonus(World w, int x, int y, int z)
	{
		return (float) EMCCConfigGeneral.uu_block_enchant_power.getAsDouble();
	}

	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(EMCCItems.UNUNSEPTIUM_BLOCK, 8), "III", "IUI", "III", 'I', ODItems.OBSIDIAN, 'U', ItemMaterialsEMCC.ITEM_UUS);
	}
}