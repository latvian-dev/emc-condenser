package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.core.util.FastList;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUShovel extends ItemToolEMCC
{
	public static final FastList<Block> effectiveBlocks = new FastList<Block>(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	public static final FastList<Material> effectiveMaterials = new FastList<Material>(new Material[] { Material.grass, Material.ground, Material.sand, Material.clay, Material.snow });
	
	public ItemUUShovel(String s)
	{
		super(s);
		
		setHarvestLevel(EnumToolClass.SHOVEL.toolClass, EnumToolClass.LEVEL_DIAMOND);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableShovel)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "U", "S", "S",
					'U', EMCCItems.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return b == Blocks.snow_layer || b == Blocks.snow; }
	
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b) || effectiveMaterials.contains(b.getMaterial()); }
	
	//public float getStrVsBlock(ItemStack is, Block b)
	//{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F)) : 1F; }
	
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
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.AREA, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH); }
}