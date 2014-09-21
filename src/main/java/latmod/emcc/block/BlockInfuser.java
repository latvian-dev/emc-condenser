package latmod.emcc.block;
import latmod.core.mod.tile.TileLM;
import latmod.emcc.tile.TileInfuser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockInfuser extends BlockEMCC
{
	public static int renderID = -1;
	
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
		/*ItemStack is = new ItemStack(EMCCItems.b_uu_block);
		
		if(EMCC.mod.config().recipes.condenserRecipeDifficulty == 1) is = new ItemStack(Items.nether_star);
		else if(EMCC.mod.config().recipes.condenserRecipeDifficulty == 2) is = EMCCItems.MINIUM_STAR;
		
		EMCC.mod.recipes().addRecipe(new ItemStack(this), "OBO", "OSO", "OIO",
				'O', ODItems.OBSIDIAN,
				'I', is,
				'B', EMCCItems.i_black_hole_band,
				'S', new ItemStack(ModItems.stoneMinium, 1, LatCoreMC.ANY));
				*/
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_top_empty = ir.registerIcon(mod.assets + "infuserTopEmpty");
		icon_top_glow = ir.registerIcon(mod.assets + "infuserTopGlow");
		icon_side_empty = ir.registerIcon(mod.assets + "infuserSideEmpty");
		icon_side_glow = ir.registerIcon(mod.assets + "infuserSideGlow");
	}
	
	public int getRenderType()
	{ return renderID; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
}