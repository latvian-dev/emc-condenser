package latmod.emcc.client;
import latmod.core.LMProxy;
import latmod.emcc.client.render.world.RenderCondenser;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		RenderCondenser.instance.register();
	}
}