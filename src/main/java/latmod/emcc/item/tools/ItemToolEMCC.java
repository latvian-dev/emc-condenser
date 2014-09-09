package latmod.emcc.item.tools;
import java.util.*;

import latmod.emcc.EMCC;
import latmod.emcc.api.IEmcTool;
import latmod.emcc.item.ItemEmcStorage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public abstract class ItemToolEMCC extends ItemEmcStorage implements IEmcTool
{
	public Set<Block> effectiveBlocks;
	public static final Set<Block> emptySet = new HashSet<Block>();
	
	public final float efficiencyOnProperMaterial = 4F;
	
	public ItemToolEMCC(String s, Set<Block> b)
	{
		super(s);
		effectiveBlocks = b;
		setFull3D();
		setHasSubtypes(false);
		setMaxDamage(20);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.mod.config().tools.toolEmcPerDamage; }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public final void onPostLoaded() { }
	
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
	
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b); }
	
	public boolean isEffectiveAgainst(Material m, Material... materials)
	{ for(Material m1 : materials) { if(m1 == m) return true; } return false; }
	
	public static boolean hasEnchantment(ItemStack is, Enchantment e)
	{ return EnchantmentHelper.getEnchantmentLevel(e.effectId, is) > 0; }
	
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	public double getMaxEmcCharge(ItemStack is, boolean battery)
	{ return EMCC.mod.config().tools.toolEmcPerDamage * 512D; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return 32D; }
	
	public double getDurabilityForDisplay(ItemStack is)
	{ return 0.5D; }
}