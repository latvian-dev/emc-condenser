package latmod.emcc;
import latmod.core.*;
import latmod.emcc.client.render.world.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		LatCoreMC.addEventHandler(EMCCClientEventHandler.instance, true, false, false);
		RenderCondenser.instance.register();
		RenderInfuser.instance.register();
	}
}