package latmod.emcc.item;
import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class ItemUUPick extends ItemToolEMCC // ItemPickaxe
{
	private ItemStack itemPick;
	
	public ItemUUPick(int id, String s)
	{
		super(id, s);
		
		itemPick = new ItemStack(Item.pickaxeDiamond);
		
		LatCore.addTool(this, EnumToolClass.PICKAXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enablePick)
			EMCC.recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enablePick; }
	
	public boolean canHarvestBlock(Block b)
	{ return true; }
	
	public float getStrVsBlock(ItemStack is, Block block)
	{ return itemPick.getStrVsBlock(block); }
}