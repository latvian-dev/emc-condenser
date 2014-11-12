package latmod.emcc;
import latmod.emcc.client.render.world.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends EMCCCommon
{
	public void preInit()
	{
	}

	public void init()
	{
	}

	public void postInit()
	{
		RenderCondenser.instance.register();
		RenderInfuser.instance.register();
	}
}