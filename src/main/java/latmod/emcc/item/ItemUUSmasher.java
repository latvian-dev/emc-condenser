package latmod.emcc.item;
import latmod.core.*;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;

public class ItemUUSmasher extends ItemToolEMCC // ItemPickaxe
{
	public ItemUUSmasher(int id, String s)
	{
		super(id, s, getEffectiveBlocks());
		
		damageVsEntity = 6F;
		
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
					Character.valueOf('S'), EMCCItems.STICK,
					Character.valueOf('P'), EMCCItems.i_pick,
					Character.valueOf('A'), EMCCItems.i_axe,
					Character.valueOf('V'), EMCCItems.i_shovel);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return super.getEmcPerDmg(is) * 1.5D; }
	
	public boolean canHarvestBlock(Block b)
	{ return true; }
	
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{ return toolMaterial.getEfficiencyOnProperMaterial(); }
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(1, el1); return true; }
}