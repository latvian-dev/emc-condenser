package latmod.emcc.client;
import latmod.emcc.EMCCCommon;
import latmod.emcc.client.render.world.RenderCondenser;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	public void init()
	{
		RenderCondenser.instance.register();
	}
}