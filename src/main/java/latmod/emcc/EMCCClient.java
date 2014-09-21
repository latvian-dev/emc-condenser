package latmod.emcc;
import latmod.core.client.LatCoreMCClient;
import latmod.core.mod.gui.ContainerEmpty;
import latmod.emcc.block.*;
import latmod.emcc.client.container.ContainerCondenser;
import latmod.emcc.client.gui.*;
import latmod.emcc.client.render.world.*;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	public static final int FRONT = 3;

	public void preInit()
	{
	}

	public void init()
	{
	}

	public void postInit()
	{
		LatCoreMCClient.addBlockRenderer(BlockCondenser.renderID = LatCoreMCClient.getNewBlockRenderID(), new RenderCondenser());
		LatCoreMCClient.addBlockRenderer(BlockInfuser.renderID = LatCoreMCClient.getNewBlockRenderID(), new RenderInfuser());
	}
	
	public Object getClientGuiElement(int ID, EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te != null)
		{
			if(ID == EMCCGuis.CONDENSER) return new GuiCondenser(new ContainerCondenser(ep, (TileCondenser)te));
			else if(ID == EMCCGuis.COND_SETTINGS) return new GuiCondenserSettings(new ContainerEmpty(ep, (TileCondenser)te));
		}
		
		return null;
	}
}