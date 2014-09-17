package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemUUBow extends ItemToolEMCC // ItemBow
{
	public ItemUUBow(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableBow)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), " US", "U S", " US",
					'U', EMCCItems.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING, ToolInfusion.INFINITY, ToolInfusion.FIRE, ToolInfusion.SHARPNESS, ToolInfusion.KNOCKBACK); }
	
	public boolean isEffective(Block b)
	{ return false; }
}