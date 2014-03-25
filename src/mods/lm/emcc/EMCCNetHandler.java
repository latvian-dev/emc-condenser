package mods.lm.emcc;
import java.io.*;

import mods.lm.emcc.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.*;

public class EMCCNetHandler implements IPacketHandler
{
	public void onPacketData(INetworkManager m, Packet250CustomPayload p, Player player)
	{
		try
		{
			if(p.channel.equals(EMCCFinals.MOD_ID) && player instanceof EntityPlayer)
			{
				World worldObj = ((EntityPlayer)player).worldObj;
				
				DataInputStream dios = new DataInputStream(new ByteArrayInputStream(p.data));
				int dim = dios.readShort();
				int x = dios.readInt();
				int y = dios.readInt();
				int z = dios.readInt();
				int buttonID = dios.readByte();
				
				if(dim == worldObj.provider.dimensionId && !worldObj.isRemote)
				{
					TileEntity te = worldObj.getBlockTileEntity(x, y, z);
					
					if(te != null && te instanceof TileAlchemy)
					{
						if(te instanceof TileCondenser)
						((TileCondenser)te).handleGuiButton(buttonID);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendToServer(TileAlchemy te, int buttonID)
	{
		Packet250CustomPayload packet = new Packet250CustomPayload(EMCCFinals.MOD_ID, new byte[0]);
		
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dios = new DataOutputStream(bos);
			
			dios.writeShort((short)te.worldObj.provider.dimensionId);
			dios.writeInt(te.xCoord);
			dios.writeInt(te.yCoord);
			dios.writeInt(te.zCoord);
			dios.writeByte(buttonID);
			dios.flush();
			
			packet.data = bos.toByteArray();
			packet.length = bos.size();
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		PacketDispatcher.sendPacketToServer(packet);
	}
}