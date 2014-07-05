package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import latmod.core.base.IItemLM;
import latmod.emcc.*;
import latmod.emcc.api.IEmcTool;

public class ItemUUSword extends ItemSword implements IItemLM, IEmcTool
{
	public final String itemName;
	
	public ItemUUSword(int id, String s)
	{
		super(id, EMCC.toolMaterial);
		itemName = s;
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableSword)
			EMCC.recipes.addRecipe(new ItemStack(this), "U", "U", "S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public Item getItem()
	{ return this; }

	public String getItemID()
	{ return itemName; }

	public void onPostLoaded()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{ itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/" + itemName); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(itemName); }
}