package latmod.emcc.item.tools;
import java.util.Set;

import com.google.common.collect.Sets;

import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.EMCCUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class ItemUUShovel extends ItemToolEMCC
{
	public static final Set<Block> effective = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	
	public ItemUUShovel(String s)
	{
		super(s, effective, true, true);
		
		setHarvestLevel(EnumToolClass.SHOVEL.toolClass, EnumToolClass.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableShovel)
			EMCC.recipes.addRecipe(new ItemStack(this), "U", "S", "S",
					'U', EMCCItems.UU_ITEM,
					'S', ODItems.STICK);
		
		ItemToolEMCC.addBlazingRecipe(new ItemStack(this));
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return b == Blocks.snow_layer || b == Blocks.snow; }
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.getMaterial(), Material.grass, Material.ground, Material.sand, Material.clay, Material.snow); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F)) : 1F; }
	
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