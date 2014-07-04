package latmod.emcc.item;
import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class ItemUUAxe extends ItemToolEMCC // ItemAxe
{
	private ItemStack itemAxe;
	
	public ItemUUAxe(int id, String s)
	{
		super(id, s);
		
		itemAxe = new ItemStack(Item.axeDiamond);
		
		LatCore.addTool(this, EnumToolClass.AXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableAxe)
			EMCC.recipes.addRecipe(new ItemStack(this), "UU", "US", " S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enableAxe; }
	
	public float getStrVsBlock(ItemStack is, Block block)
	{ return itemAxe.getStrVsBlock(block); }
}