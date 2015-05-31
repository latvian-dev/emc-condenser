package latmod.emcc.block;
import latmod.core.tile.TileLM;
import latmod.emcc.EMCCItems;
import latmod.emcc.client.render.world.RenderInfuser;
import latmod.emcc.tile.TileInfuser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockInfuser extends BlockEMCC
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_top_empty, icon_top_glow, icon_side_empty, icon_side_glow;
	
	public BlockInfuser(String s)
	{
		super(s, Material.rock);
		isBlockContainer = true;
		
		mod.addTile(TileInfuser.class, s);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileInfuser(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "UEU", "UCU", "UDU",
				'U', EMCCItems.b_uu_block,
				'E', Blocks.enchanting_table,
				'C', EMCCItems.b_condenser,
				'D', Blocks.diamond_block);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_top_empty = ir.registerIcon(mod.assets + "infuser_top_empty");
		icon_top_glow = ir.registerIcon(mod.assets + "infuser_top_glow");
		icon_side_empty = ir.registerIcon(mod.assets + "infuser_side_empty");
		icon_side_glow = ir.registerIcon(mod.assets + "infuser_side_glow");
		blockIcon = icon_side_empty;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderInfuser.instance.getRenderId(); }
	
	public boolean renderAsNormalBlock()
	{ return false; }
}