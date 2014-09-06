package latmod.emcc.item.tools;
import java.util.*;

import latmod.core.mod.item.IItemLM;
import latmod.emcc.EMCC;
import latmod.emcc.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class ItemToolEMCC extends ItemTool implements IItemLM, IEmcTool, IEffectiveTool
{
	public final String itemName;
	
	public Set<Block> effectiveBlocks;
	
	public ItemToolEMCC(String s, Set<Block> b)
	{
		super(4F, EMCC.toolMaterial, b);
		setUnlocalizedName(s);
		itemName = s;
		effectiveBlocks = b;
		setFull3D();
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.mod.config().tools.toolEmcPerDamage; }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{ return false; }
	
	public boolean isItemTool(ItemStack is)
	{ return true; }
	
	public Item getItem()
	{ return this; }

	public String getItemID()
	{ return itemName; }

	public final void onPostLoaded() { }
	
	public void loadRecipes()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{ return true; }
	
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
	
	public static boolean isEffectiveAgainst(Material m, Material... materials)
	{ for(Material m1 : materials) { if(m1 == m) return true; } return false; }
	
	public static boolean hasEnchantment(ItemStack is, int id)
	{ return EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, is) > 0; }
	
	public static ItemStack setBlazing(ItemStack is)
	{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(Enchantment.fireAspect.effectId, 1);
		EnchantmentHelper.setEnchantments(map, is);
		return is;
	}
	
	public static ItemStack setArea(ItemStack is)
	{
		if(!is.hasTagCompound())
			is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setBoolean("AreaMode", true);
		return is;
	}
}