package latmod.emcc;
import latmod.core.*;
import latmod.core.tile.*;
import latmod.emcc.gui.*;
import latmod.emcc.tile.*;
import cpw.mods.fml.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

public class EMCCCommon implements IGuiHandler
{
	public void preInit() {}
	public void init() {}
	public void postInit() {}
	
	public Object getServerGuiElement(int ID, EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if(te != null)
		{
			if(te instanceof ISecureTile)
			{
				LMSecurity security = ((ISecureTile)te).getSecurity();
				
				if(security != null && !security.canPlayerInteract(ep)) return null;
			}
			
			if(ID == EMCCGuis.CONDENSER) return new ContainerCondenser(ep, (TileCondenser)te);
			else if(ID == EMCCGuis.COND_SETTINGS) return new ContainerCondenserSettings(ep, (TileCondenser)te);
		}
		
		return null;
	}
	
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{ return null; }
}