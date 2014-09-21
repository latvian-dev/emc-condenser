package latmod.emcc.client.render.world;

import latmod.core.LatCoreMC;
import latmod.core.client.RenderBlocksCustom;
import latmod.emcc.EMCCItems;
import latmod.emcc.block.BlockCondenser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderCondenser implements ISimpleBlockRenderingHandler
{
	public RenderBlocksCustom renderBlocks = new RenderBlocksCustom();
	
	public Block glow = new Block(Material.glass)
	{
		@SideOnly(Side.CLIENT)
		public int getMixedBrightnessForBlock(IBlockAccess iba, int x, int y, int z)
		{ return 255; }
		
		public boolean isOpaqueCube()
		{ return false; }
		
		public boolean renderAsNormalBlock()
		{ return false; }
		
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_condenser.icon_top_glow;
			else return EMCCItems.b_condenser.icon_side_glow;
		}
	};
	
	public Block empty = new Block(Material.glass)
	{
		public boolean isOpaqueCube()
		{ return false; }
		
		public boolean renderAsNormalBlock()
		{ return false; }
		
		public IIcon getIcon(int s, int m)
		{
			if(s == LatCoreMC.TOP)
				return EMCCItems.b_condenser.icon_top_empty;
			else return EMCCItems.b_condenser.icon_side_empty;
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
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		renderBlocks.blockAccess = iba;
		
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(empty, x, y, z);
		renderBlocks.renderStandardBlock(glow, x, y, z);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public int getRenderId()
	{ return BlockCondenser.renderID; }
}