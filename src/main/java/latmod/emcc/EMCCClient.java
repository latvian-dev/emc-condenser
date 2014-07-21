package latmod.emcc;
import latmod.emcc.gui.*;
import latmod.emcc.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
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
	}
	
	public Object getClientGuiElement(int ID, EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te != null)
		{
			if(ID == EMCCGuis.CONDENSER) return new GuiCondenser(new ContainerCondenser(ep, (TileCondenser)te));
			else if(ID == EMCCGuis.COND_SETTINGS) return new GuiCondenserSettings(new ContainerCondenserSettings(ep, (TileCondenser)te));
			else if(ID == EMCCGuis.COND_RESTRICTED) return new GuiRestricted(new ContainerRestricted(ep, (TileCondenser)te));
		}
		
		return null;
	}
}