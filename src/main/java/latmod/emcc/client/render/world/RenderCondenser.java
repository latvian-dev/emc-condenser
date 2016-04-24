package latmod.emcc.client.render.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.client.FTBLibClient;
import latmod.emcc.EMCCItems;
import latmod.latblocks.client.render.world.BlockRendererLM;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderCondenser extends BlockRendererLM
{
	public static final RenderCondenser instance = new RenderCondenser();
	
	public Block glow = new BlockCustom()
	{
		@Override
		public int getLightValue()
		{ return 15; }
		
		@Override
		public IIcon getIcon(int s, int m)
		{ return (s == 1) ? EMCCItems.b_condenser.icon_top_glow : EMCCItems.b_condenser.icon_side_glow; }
	};
	
	public Block empty = new BlockCustom()
	{
		@Override
		public IIcon getIcon(int s, int m)
		{ return (s == 1) ? EMCCItems.b_condenser.icon_top_empty : EMCCItems.b_condenser.icon_side_empty; }
	};
	
	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderBlockAsItem(empty, 0, 1F);
		FTBLibClient.pushMaxBrightness();
		renderBlocks.renderBlockAsItem(glow, 0, 1F);
		FTBLibClient.popMaxBrightness();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.setInst(iba);
		renderBlocks.currentSide = -1;
		
		renderBlocks.setRenderBounds(renderBlocks.fullBlock);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(glow, x, y, z);
		renderBlocks.renderStandardBlock(empty, x, y, z);
		
		return true;
	}
}