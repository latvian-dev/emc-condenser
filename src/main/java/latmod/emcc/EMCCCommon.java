package latmod.emcc;
import latmod.emcc.gui.*;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class EMCCCommon implements IGuiHandler
{
	public void preInit() {}
	public void init() {}
	public void postInit() {}
	
	public Object getServerGuiElement(int ID, EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te != null)
		{
			if(ID == EMCCGuis.CONDENSER) return new ContainerCondenser(ep, (TileCondenser)te);
			else if(ID == EMCCGuis.COND_SETTINGS) return new ContainerCondenserSettings(ep, (TileCondenser)te);
		}
		
		return null;
	}
	
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{ return null; }
}