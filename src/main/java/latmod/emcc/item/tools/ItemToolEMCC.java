package latmod.emcc.item.tools;
import latmod.core.util.FastList;
import latmod.emcc.EMCC;
import latmod.emcc.api.*;
import latmod.emcc.item.ItemEmcStorage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public abstract class ItemToolEMCC extends ItemEmcStorage implements IEmcTool
{
	public static final float efficiencyOnProperMaterial = 4F;
	
	public ItemToolEMCC(String s)
	{
		super(s);
		setFull3D();
		setHasSubtypes(false);
		setMaxDamage(0);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.mod.config().tools.toolEmcPerDamage; }
	
	public boolean getIsRepairable(ItemStack is1, ItemStack is2)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public final void onPostLoaded()
	{
		ItemStack is0 = new ItemStack(this);
		setStoredEmc(is0, 0);
		itemsAdded.add(is0);
		
		ItemStack is1 = new ItemStack(this);
		setStoredEmc(is1, getMaxStoredEmc(is1));
		itemsAdded.add(is1);
	}
	
	public void loadRecipes()
	{
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{ return is; }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{ itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/" + itemName); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{ return itemIcon; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return EMCC.mod.getItemName(itemName); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return isEffective(b) ? efficiencyOnProperMaterial : 1F; }
	
	public static int getInfusionLevel(ItemStack is, ToolInfusion t)
	{ return EnchantmentHelper.getEnchantmentLevel(t.enchantment.effectId, is); }
	
	public static boolean hasInfusion(ItemStack is, ToolInfusion t)
	{ return getInfusionLevel(is, t) > 0; }
	
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	public double getMaxStoredEmc(ItemStack is)
	{ return EMCC.mod.config().tools.toolEmcPerDamage * 512D; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return 4096D; }
	
	public boolean showDurabilityBar(ItemStack is)
	{ return true; }
	
	public double getDurabilityForDisplay(ItemStack is)
	{ return 1F - getStoredEmc(is) / getMaxStoredEmc(is); }
	
	public int getItemEnchantability(ItemStack is)
	{ return 0; }
	
	public boolean isRepairable()
	{ return false; }
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return isEffective(b); }
	
	public static boolean isEffective(Block b, FastList<Block> bl, FastList<Material> ml)
	{ return bl.contains(b) || ml.contains(b.getMaterial()); }
}