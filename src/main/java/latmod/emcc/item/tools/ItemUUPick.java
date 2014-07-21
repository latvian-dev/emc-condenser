package latmod.emcc.item.tools;
import java.util.Set;

import com.google.common.collect.Sets;

import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class ItemUUPick extends ItemToolEMCC
{
	public static final Set<Block> effective = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	
	public ItemUUPick(String s)
	{
		super(s, effective, true, true);
		
		setHarvestLevel(EnumToolClass.PICKAXE.toolClass, EnumToolClass.EMERALD);
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
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return true; }
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.getMaterial(), Material.iron, Material.anvil, Material.rock); }
	
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