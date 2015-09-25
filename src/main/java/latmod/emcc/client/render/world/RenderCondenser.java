package latmod.emcc.client.render.world;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.EMCCItems;
import latmod.ftbu.util.client.*;
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
		public int getLightValue()
		{ return 15; }
		
		public IIcon getIcon(int s, int m)
		{ return (s == 1) ? EMCCItems.b_condenser.icon_top_glow : EMCCItems.b_condenser.icon_side_glow; }
	};
	
	public Block empty = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{ return (s == 1) ? EMCCItems.b_condenser.icon_top_empty : EMCCItems.b_condenser.icon_side_empty; }
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderBlockAsItem(empty, 0, 1F);
		LatCoreMCClient.pushMaxBrightness();
		renderBlocks.renderBlockAsItem(glow, 0, 1F);
		LatCoreMCClient.popMaxBrightness();
	}
	
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