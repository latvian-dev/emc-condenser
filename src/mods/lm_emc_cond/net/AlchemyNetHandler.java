package mods.lm_emc_cond.net;
import java.io.*;

import mods.lm_emc_cond.*;
import mods.lm_emc_cond.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.*;

public class AlchemyNetHandler implements IPacketHandler
{
	public void onPacketData(INetworkManager m, Packet250CustomPayload p, Player player)
	{
		try
		{
			if(p.channel.equals(AlchemyFinals.MOD_ID) && p instanceof PacketAlchemyTile && player instanceof EntityPlayer)
			{
				PacketAlchemyTile pat = (PacketAlchemyTile)p;
				World worldObj = ((EntityPlayer)player).worldObj;
				
				DataInputStream dios = new DataInputStream(new ByteArrayInputStream(p.data));
				int dim = dios.readShort();
				int x = dios.readInt();
				int y = dios.readInt();
				int z = dios.readInt();
				
				if(dim == worldObj.provider.dimensionId && !worldObj.isRemote)
				{
					TileEntity te = worldObj.getBlockTileEntity(x, y, z);
					
					if(te != null && te instanceof TileAlchemy)
					{
						pat.readExtraData((TileAlchemy)te, dios);
						pat.readAlchemyPacket((TileAlchemy)te, m, player);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}