package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;
import latmod.core.LatCore;
import latmod.core.base.IItemLM;
import latmod.emcc.EMCC;
import latmod.emcc.api.*;

public class ItemToolEMCC extends ItemTool implements IItemLM, IEmcTool
{
	public final String itemName;
	
	public ItemToolEMCC(int id, String s, Block[] b)
	{
		super(id, 4F, EMCC.toolMaterial, b);
		itemName = s;
		setFull3D();
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public Item getItem()
	{ return this; }

	public String getItemID()
	{ return itemName; }

	public final void onPostLoaded() { }
	
	public boolean onBlockDestroyed(ItemStack is, World w, int x, int y, int z, int s, EntityLivingBase el)
    {
		if(!w.isRemote && is.getItemDamage() < is.getMaxItemUseDuration() && !el.isSneaking())
		{
			LatCore.printChat((EntityPlayer)el, "S: " + s);
		}
		
		return super.onBlockDestroyed(is, w, x, y, z, s, el);
    }
	
	public void loadRecipes()
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