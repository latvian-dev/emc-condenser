package com.latmod.emcc.item;

import com.latmod.emcc.api.ToolInfusion;
import com.latmod.emcc.config.EMCCConfigTools;
import com.latmod.emcc.item.ItemMaterialsEMCC;
import ftb.lib.api.item.ODItems;
import ftb.lib.api.item.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemUUAxe extends ItemToolEMCC // ItemAxe
{
	public static final List<Block> effectiveBlocks = Arrays.asList(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.lit_pumpkin);
	public static final List<Material> effectiveMaterials = Arrays.asList(Material.wood, Material.plants, Material.vine, Material.gourd, Material.leaves);

	public ItemUUAxe(String s)
	{
		super(s);

		setHarvestLevel(Tool.Type.AXE, Tool.Level.ALUMITE);
	}

	@Override
	public void loadRecipes()
	{
		if (EMCCConfigTools.tools.getAsBoolean())
		{
			getMod().recipes.addRecipe(new ItemStack(this), "UU", "US", " S", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
		}
	}

	@Override
	public boolean isEffective(Block b)
	{
		return isEffective(b, effectiveBlocks, effectiveMaterials);
	}

	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{
		return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING);
	}
}