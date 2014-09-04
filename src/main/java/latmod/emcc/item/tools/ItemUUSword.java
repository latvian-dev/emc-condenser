package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.core.mod.item.IItemLM;
import latmod.emcc.*;
import latmod.emcc.api.IEmcTool;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class ItemUUSword extends ItemSword implements IItemLM, IEmcTool
{
	public final String itemName;
	
	@SideOnly(Side.CLIENT)
	public IIcon blazingIcon;
	
	public ItemUUSword(String s)
	{
		super(EMCC.toolMaterial);
		itemName = s;
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config.tools.enableSword)
			EMCC.mod.recipes.addRecipe(new ItemStack(this), "U", "U", "S",
					'U', EMCCItems.UU_ITEM,
					'S', ODItems.STICK);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.mod.config.tools.toolEmcPerDamage; }
	
	public Item getItem()
	{ return this; }

	public String getItemID()
	{ return itemName; }

	public void onPostLoaded()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(itemName); }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && ItemToolEMCC.isBlazing(is) && ep.isBurning())
		{
			ep.extinguish();
			is.damageItem(2, ep);
		}
		
		return is;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/def/" + itemName);
		blazingIcon = ir.registerIcon(EMCC.mod.assets + "tools/blazing/" + itemName);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{
		if(ItemToolEMCC.isBlazing(is))
			return blazingIcon;
		return itemIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{ return true; }
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		is.damageItem(1, el1);
		return true;
	}
}