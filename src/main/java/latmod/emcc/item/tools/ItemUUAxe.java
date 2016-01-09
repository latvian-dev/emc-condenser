package latmod.emcc.item.tools;

import ftb.lib.item.*;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ItemUUAxe extends ItemToolEMCC // ItemAxe
{
	public static final List<Block> effectiveBlocks = Arrays.asList(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.lit_pumpkin);
	public static final List<Material> effectiveMaterials = Arrays.asList(Material.wood, Material.plants, Material.vine, Material.gourd, Material.leaves);
	
	public ItemUUAxe(String s)
	{
		super(s);
		
		setHarvestLevel(Tool.Type.AXE, Tool.Level.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfigTools.Enable.tools.get())
			getMod().recipes.addRecipe(new ItemStack(this), "UU", "US", " S", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
	}
	
	public boolean isEffective(Block b)
	{ return isEffective(b, effectiveBlocks, effectiveMaterials); }
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING); }
}