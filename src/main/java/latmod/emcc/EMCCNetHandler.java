package latmod.emcc;

import latmod.emcc.emc.EMCHandler;
import latmod.ftbu.core.net.CustomActionFromServer;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;

public class EMCCNetHandler implements CustomActionFromServer
{
	public static final EMCCNetHandler instance = new EMCCNetHandler();
	
	public void sendToClient(EntityPlayerMP ep, NBTTagCompound data)
	{
		if(EMCCConfig.General.forceVanillaEMC || !EMCHandler.hasEE3())
		try { byte[] b = EMCHandler.instance().vanillaEMC.toBytes(); data.setByteArray("B", b); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public void readFromServer(EntityPlayer ep, NBTTagCompound data)
	{
		if(data.hasKey("B"))
		{
			try
			{
				byte[] b = data.getByteArray("B");
				EMCHandler.instance().vanillaEMC.fromBytes(b);
			}
			catch(Exception e)
			{ e.printStackTrace(); }
		}
	}
}