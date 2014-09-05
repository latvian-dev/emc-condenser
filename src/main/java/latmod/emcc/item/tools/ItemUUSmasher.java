package latmod.emcc.item.tools;
import java.util.Set;

import latmod.core.*;
import latmod.core.util.FastList;
import latmod.emcc.*;
import latmod.emcc.api.EMCCUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSmasher extends ItemToolEMCC
{
	public ItemUUSmasher(String s)
	{
		super(s, getEffectiveBlocks(), true, true);
		
		setHarvestLevel(EnumToolClass.PICKAXE.toolClass, EnumToolClass.ALUMITE);
		setHarvestLevel(EnumToolClass.SHOVEL.toolClass, EnumToolClass.ALUMITE);
		setHarvestLevel(EnumToolClass.AXE.toolClass, EnumToolClass.ALUMITE);
	}
	
	private static Set<Block> getEffectiveBlocks()
	{
		FastList<Block> blocks = new FastList<Block>();
		blocks.addAll(ItemUUPick.effective);
		blocks.addAll(ItemUUShovel.effective);
		blocks.addAll(ItemUUAxe.effective);
		return blocks;
	}

	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableSmasher)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "APA", "BVB", " S ",
					'B', EMCCItems.UU_BLOCK,
					'S', ODItems.STICK,
					'P', EMCCItems.i_pick,
					'A', EMCCItems.i_axe,
					'V', EMCCItems.i_shovel);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return super.getEmcPerDmg(is) * 1.5D; }
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return true; }
	
	public boolean isEffective(Block b)
	{ return EMCCItems.i_pick.isEffective(b) || EMCCItems.i_shovel.isEffective(b) || EMCCItems.i_axe.isEffective(b); }
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(1, el1); return true; }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F); }
	
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		if(!isBlazing(tool)) return false;
		return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
	}
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
    {
		if(!isArea(is)) return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		else return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
    }
}