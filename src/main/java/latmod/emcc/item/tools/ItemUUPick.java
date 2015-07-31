package latmod.emcc.item.tools;
import latmod.emcc.EMCCConfig;
import latmod.emcc.api.*;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.item.Tool;
import latmod.ftbu.core.util.FastList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUPick extends ItemToolEMCC
{
	public static final FastList<Block> effectiveBlocks = new FastList<Block>(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	public static final FastList<Material> effectiveMaterials = new FastList<Material>(new Material[] { Material.iron, Material.anvil, Material.rock });
	
	public ItemUUPick(String s)
	{
		super(s);
		setHarvestLevel(Tool.Type.PICK, Tool.Level.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.Tools.enableTools)
			mod.recipes.addRecipe(new ItemStack(this), "UUU", " S ", " S ",
					'U', ItemMaterialsEMCC.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean isEffective(Block b)
	{ return effectiveBlocks.contains(b) || effectiveMaterials.contains(b.getMaterial()); }
	
	public boolean onBlockStartBreak(ItemStack tool, int x, int y, int z, EntityPlayer ep)
	{
		return false;
		/*
		if(!isBlazing(tool)) return false;
		return EMCCUtils.breakBlockWithBlazingItem(ep.worldObj, x, y, z, ep, tool, this);
		*/
	}
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
    {
		EMCCUtils.destroyBlockArea(w, x, y, z, el, is, bid, this);
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
    }
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.FIRE, ToolInfusion.SILKTOUCH); }
}