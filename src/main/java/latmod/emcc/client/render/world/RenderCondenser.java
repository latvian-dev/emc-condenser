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
public class RenderCondenser extends BlockRendererLM
{
	public static final RenderCondenser instance = new RenderCondenser();
	
	public Block glow = new BlockGlowing()
	{
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_condenser.icon_top_glow;
			else return EMCCItems.b_condenser.icon_side_glow;
		}
	};
	
	public Block empty = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_condenser.icon_top_empty;
			else return EMCCItems.b_condenser.icon_side_empty;
		}
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderBlockAsItem(empty, 0, 1F);
		renderBlocks.renderBlockAsItem(glow, 0, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.blockAccess = iba;
		
		renderBlocks.setRenderBounds(renderBlocks.fullBlock);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(glow, x, y, z);
		renderBlocks.renderStandardBlock(empty, x, y, z);
		
		return true;
	}
}