package latmod.emcc;
import latmod.core.client.LatCoreMCClient;
import latmod.emcc.block.*;
import latmod.emcc.client.render.world.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	public static boolean glowingCondensers = false;
	
	public void preInit()
	{
		glowingCondensers = EMCC.mod.config().general.enableCustomRenderer;
	}

	public void init()
	{
	}

	public void postInit()
	{
		if(glowingCondensers)
		{
			LatCoreMCClient.addBlockRenderer(BlockCondenser.renderID = LatCoreMCClient.getNewBlockRenderID(), new RenderCondenser());
			LatCoreMCClient.addBlockRenderer(BlockInfuser.renderID = LatCoreMCClient.getNewBlockRenderID(), new RenderInfuser());
		}
	}
}