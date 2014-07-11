package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.EMCCUtils;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class ItemUUSmasher extends ItemToolEMCC
{
	public ItemUUSmasher(int id, String s)
	{
		super(id, s, getEffectiveBlocks(), true, true);
		
		LatCore.addTool(this, EnumToolClass.PICKAXE, EnumToolClass.EMERALD);
		LatCore.addTool(this, EnumToolClass.SHOVEL, EnumToolClass.EMERALD);
		LatCore.addTool(this, EnumToolClass.AXE, EnumToolClass.EMERALD);
	}
	
	private static Block[] getEffectiveBlocks()
	{
		FastList<Block> list = new FastList<Block>();
		list.addAll(ItemPickaxe.blocksEffectiveAgainst);
		list.addAll(ItemSpade.blocksEffectiveAgainst);
		list.addAll(ItemAxe.blocksEffectiveAgainst);
		return list.toArray(new Block[0]);
	}

	public void loadRecipes()
	{
		if(EMCC.config.tools.enableSmasher)
			EMCC.recipes.addRecipe(new ItemStack(this), "APA", "BVB", " S ",
					Character.valueOf('B'), EMCCItems.UU_BLOCK,
					Character.valueOf('S'), ODItems.STICK,
					Character.valueOf('P'), EMCCItems.i_pick,
					Character.valueOf('A'), EMCCItems.i_axe,
					Character.valueOf('V'), EMCCItems.i_shovel);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return super.getEmcPerDmg(is) * 1.5D; }
	
	public boolean canHarvestBlock(Block b)
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
	
	public boolean onBlockDestroyed(ItemStack is, World w, int bid, int x, int y, int z, EntityLivingBase el)
    {
		if(!isArea(is)) return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		else return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
    }
}