package latmod.emcc.item.tools;

import ftb.lib.api.item.ODItems;
import ftb.lib.api.item.Tool;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ItemUUShovel extends ItemToolEMCC
{
	public static final List<Block> effectiveBlocks = Arrays.asList(Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium);
	public static final List<Material> effectiveMaterials = Arrays.asList(Material.grass, Material.ground, Material.sand, Material.clay, Material.snow);
	
	public ItemUUShovel(String s)
	{
		super(s);
		
		setHarvestLevel(Tool.Type.SHOVEL, Tool.Level.ALUMITE);
	}
	
	@Override
	public void loadRecipes()
	{
		if(EMCCConfigTools.tools.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this), "U", "S", "S", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
	}
	
	@Override
	public boolean canHarvestBlock(Block b, ItemStack is)
	{ return b == Blocks.snow_layer || b == Blocks.snow; }
	
	@Override
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b) || effectiveMaterials.contains(b.getMaterial()); }
	
	//public float getStrVsBlock(ItemStack is, Block b)
	//{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 8F : 1F)) : 1F; }
	
	@Override
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		//return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
	{
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		//else return EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
	}
	
	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH); }
}