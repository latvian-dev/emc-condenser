package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSmasher extends ItemUUPick
{
	public ItemUUSmasher(String s)
	{
		super(s);
		
		setHarvestLevel(EnumToolClass.PICKAXE.toolClass, EnumToolClass.LEVEL_DIAMOND);
		setHarvestLevel(EnumToolClass.SHOVEL.toolClass, EnumToolClass.LEVEL_DIAMOND);
		setHarvestLevel(EnumToolClass.AXE.toolClass, EnumToolClass.LEVEL_DIAMOND);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableSmasher)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "APA", "BVB", " S ",
					'B', EMCCItems.BLOCK_UUS,
					'S', ODItems.STICK,
					'P', EMCCItems.i_pick,
					'A', EMCCItems.i_axe,
					'V', EMCCItems.i_shovel);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return super.getEmcPerDmg(is) * 1.5D; }
	
	public boolean isEffective(Block b)
	{ return EMCCItems.i_pick.isEffective(b) || EMCCItems.i_shovel.isEffective(b) || EMCCItems.i_axe.isEffective(b); }
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(1, el1); return true; }
	
	//public float getStrVsBlock(ItemStack is, Block b)
	//{ return efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F); }
	
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		//return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
	}
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
    {
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		//else return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
    }
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.FIRE, ToolInfusion.AREA, ToolInfusion.SILKTOUCH); }
}