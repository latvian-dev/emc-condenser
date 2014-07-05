package latmod.emcc.item;
import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class ItemUUPick extends ItemToolEMCC // ItemPickaxe
{
	public ItemUUPick(int id, String s)
	{
		super(id, s, ItemPickaxe.blocksEffectiveAgainst);
		
		LatCore.addTool(this, EnumToolClass.PICKAXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enablePick)
			EMCC.recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean canHarvestBlock(Block b)
	{ return true; }
	
	public float getStrVsBlock(ItemStack is, Block b)
    { return b != null && (b.blockMaterial == Material.iron || b.blockMaterial == Material.anvil || b.blockMaterial == Material.rock) ? efficiencyOnProperMaterial : super.getStrVsBlock(is, b); }
}