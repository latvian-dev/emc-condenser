package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class ItemUUPick extends ItemToolEMCC
{
	public ItemUUPick(int id, String s)
	{
		super(id, s, ItemPickaxe.blocksEffectiveAgainst, true, true);
		
		LatCore.addTool(this, EnumToolClass.PICKAXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enablePick)
			EMCC.recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), ODItems.STICK);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public boolean canHarvestBlock(Block b)
	{ return true; }
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.blockMaterial, Material.iron, Material.anvil, Material.rock); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F)) : 1F; }
	
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