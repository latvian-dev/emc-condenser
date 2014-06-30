package latmod.emcc.tile;
import net.minecraft.entity.player.EntityPlayer;
import latmod.core.*;
import latmod.core.base.TileLM;
import latmod.core.tile.*;
import latmod.emcc.EMCC;

public class TileEMCC extends TileLM implements ISecureTile
{
	public LMSecurity getSecurity()
	{ return security; }
	
	public void openGui(EntityPlayer ep, int guiID)
	{ ep.openGui(EMCC.inst, guiID, worldObj, xCoord, yCoord, zCoord); }
}