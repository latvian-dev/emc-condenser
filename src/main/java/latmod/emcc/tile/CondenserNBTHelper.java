package latmod.emcc.tile;
import latmod.core.*;
import latmod.core.tile.*;
import net.minecraft.nbt.*;

public class CondenserNBTHelper
{
	public static void readInv(TileCondenser t, NBTTagCompound tag)
	{
		t.items = InvUtils.readItemsFromNBT(t.items.length, tag, "Items");
	}
	
	public static void readRendering(TileCondenser t, NBTTagCompound tag)
	{
		NBTTagCompound securityTag = tag.getCompoundTag("Security");
		t.security.readFromNBT(securityTag);
		
		t.security.restricted.trim(16);
		
		t.storedEMC = tag.getDouble("StoredEMC");
		t.safeMode = SafeMode.VALUES[tag.getByte("SafeMode")];
		t.redstoneMode = RedstoneMode.VALUES[tag.getByte("RSMode")];
		t.invMode = InvMode.VALUES[tag.getByte("InvMode")];
		t.repairTools = RepairTools.VALUES[tag.getByte("RepairTools")];
	}
	
	public static void readAll(TileCondenser t, NBTTagCompound tag)
	{
		readInv(t, tag);
		readRendering(t, tag);
		
		t.tick = tag.getLong("Tick");
		if(t.tick < 0L) t.tick = 0L;
		
		t.cooldown = tag.getShort("Cooldown");
	}
	
	// Write //
	
	public static void writeInv(TileCondenser t, NBTTagCompound tag)
	{
		InvUtils.writeItemsToNBT(t.items, tag, "Items");
	}
	
	public static void writeRendering(TileCondenser t, NBTTagCompound tag)
	{
		NBTTagCompound securityTag = new NBTTagCompound();
		t.security.writeToNBT(securityTag);
		tag.setTag("Security", securityTag);
		
		tag.setDouble("StoredEMC", t.storedEMC);
		tag.setByte("SafeMode", (byte)t.safeMode.ID);
		tag.setByte("RSMode", (byte)t.redstoneMode.ID);
		tag.setByte("InvMode", (byte)t.invMode.ID);
		tag.setByte("RepairTools", (byte)t.repairTools.ID);
	}
	
	public static void writeAll(TileCondenser t, NBTTagCompound tag)
	{
		writeInv(t, tag);
		writeRendering(t, tag);
		
		tag.setLong("Tick", t.tick);
		tag.setShort("Cooldown", (short)t.cooldown);
	}
}