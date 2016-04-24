package latmod.emcc.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import latmod.emcc.EMCCCommon;
import latmod.emcc.client.render.world.RenderCondenser;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	@Override
	public void preInit()
	{
		RenderCondenser.instance.register();
	}
}