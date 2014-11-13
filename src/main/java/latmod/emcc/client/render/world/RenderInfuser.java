package latmod.emcc.client.render.world;

import latmod.core.LatCoreMC;
import latmod.core.client.BlockRendererLM;
import latmod.emcc.EMCCItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderInfuser extends BlockRendererLM
{
	public static final RenderInfuser instance = new RenderInfuser();
	
	public Block glow = new BlockGlowing()
	{
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_infuser.icon_top_glow;
			else return EMCCItems.b_infuser.icon_side_glow;
		}
	};
	
	public Block empty = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_infuser.icon_top_empty;
			else return EMCCItems.b_infuser.icon_side_empty;
		}
	};
	
	public void renderInventoryBlock(Block b, int paramInt1, int paramInt2, RenderBlocks renderer)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderBlockAsItem(empty, 0, 1F);
		renderBlocks.renderBlockAsItem(glow, 0, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int renderID, RenderBlocks renderer0)
	{
		renderBlocks.blockAccess = iba;
		
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(empty, x, y, z);
		renderBlocks.renderStandardBlock(glow, x, y, z);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}