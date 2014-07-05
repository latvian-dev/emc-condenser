package latmod.emcc.item;
import latmod.core.*;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class ItemUUAxe extends ItemToolEMCC
{
	public ItemUUAxe(int id, String s)
	{
		super(id, s, ItemAxe.blocksEffectiveAgainst);
		
		LatCore.addTool(this, EnumToolClass.AXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableAxe)
			EMCC.recipes.addRecipe(new ItemStack(this), "UU", "US", " S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public float getStrVsBlock(ItemStack is, Block b)
    { return b != null && (b.blockMaterial == Material.wood || b.blockMaterial == Material.plants || b.blockMaterial == Material.vine) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(is, b); }
}