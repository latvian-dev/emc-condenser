package latmod.emcc.client;
import cpw.mods.fml.relauncher.*;
import latmod.emcc.EMCCCommon;
import latmod.emcc.client.render.world.RenderCondenser;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	public void init()
	{
		RenderCondenser.instance.register();
	}
}