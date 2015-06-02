package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.core.item.Tool;
import latmod.core.util.FastList;
import latmod.emcc.EMCCConfig;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemUUAxe extends ItemToolEMCC // ItemAxe
{
	public static final FastList<Block> effectiveBlocks = new FastList<Block>(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.lit_pumpkin });
	public static final FastList<Material> effectiveMaterials = new FastList<Material>(new Material[] { Material.wood, Material.plants, Material.vine, Material.gourd, Material.leaves });
	
	public ItemUUAxe(String s)
	{
		super(s);
		
		setHarvestLevel(Tool.Type.AXE, Tool.Level.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.Tools.enableTools)
			mod.recipes.addRecipe(new ItemStack(this), "UU", "US", " S",
					'U', ItemMaterialsEMCC.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean isEffective(Block b)
	{ return isEffective(b, effectiveBlocks, effectiveMaterials); }
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING); }
}