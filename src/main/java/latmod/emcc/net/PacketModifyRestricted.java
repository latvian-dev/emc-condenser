package latmod.emcc.net;
import java.io.*;

import net.minecraft.entity.player.EntityPlayer;
import latmod.emcc.tile.*;

public class PacketModifyRestricted extends PacketCondenser
{
	public boolean add;
	public String name;
	
	public PacketModifyRestricted(boolean b, String s)
	{
		super(ID_MODIFY_RESTRICTED);
		add = b;
		name = s;
	}
	
	public void writePacket(TileCondenser t, DataOutputStream dos) throws Exception
	{
		dos.writeBoolean(add);
		dos.writeUTF(name);
	}
	
	public void readPacket(TileCondenser t, DataInputStream dis, EntityPlayer ep) throws Exception
	{
		add = dis.readBoolean();
		name = dis.readUTF();
		
		if(add && !t.security.restricted.contains(name) && t.security.restricted.size() < 16)
			t.security.restricted.add(name);
		
		if(!add) t.security.restricted.remove(name);
		
		t.markDirty();
	}
}