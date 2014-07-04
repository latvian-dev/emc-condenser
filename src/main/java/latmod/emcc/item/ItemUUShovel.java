package latmod.emcc.item;
import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class ItemUUShovel extends ItemToolEMCC
{
	private ItemStack itemShovel;
	
	public ItemUUShovel(int id, String s)
	{
		super(id, s);
		
		itemShovel = new ItemStack(Item.shovelDiamond);
		
		LatCore.addTool(this, EnumToolClass.SHOVEL, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableShovel)
			EMCC.recipes.addRecipe(new ItemStack(this), "U", "S", "S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enableShovel; }
	
	public float getStrVsBlock(ItemStack is, Block block)
	{ return itemShovel.getStrVsBlock(block); }
}