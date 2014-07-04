package latmod.emcc.tile;
import latmod.core.InvUtils;
import net.minecraft.nbt.*;

public class EnumCond
{
	public static enum InvMode
	{
		ENABLED("Enabled"),
		ONLY_IN("Insert Only"),
		ONLY_OUT("Extract Only"),
		DISABLED("Disabled");
		
		public static final InvMode[] VALUES = values();
		
		public final int ID;
		public final String text;
		
		InvMode(String s)
		{
			ID = ordinal();
			text = s;
		}
		
		public InvMode next()
		{ return VALUES[(ID + 1) % VALUES.length]; }

		public boolean canInsertItem()
		{ return this == ENABLED || this == ONLY_IN; }
		
		public boolean canExtractItem()
		{ return this == ENABLED || this == ONLY_OUT; }
	}
	
	public static enum Redstone
	{
		DISABLED("Disabled"),
		ACTIVE_HIGH("Active High"),
		ACTIVE_LOW("Active Low");
		
		public static final Redstone[] VALUES = values();
		
		public final int ID;
		public final String text;
		
		Redstone(String s)
		{
			ID = ordinal();
			text = s;
		}
		
		public boolean cancel(boolean b)
		{
			if(this == DISABLED) return false;
			if(this == ACTIVE_HIGH && !b) return true;
			if(this == ACTIVE_LOW && b) return true;
			return false;
		}

		public Redstone next()
		{ return VALUES[(ID + 1) % VALUES.length]; }
	}
	
	public static enum Security
	{
		PUBLIC("Public"),
		PRIVATE("Private"),
		WHITELIST("Whitelist"),
		BLACKLIST("Blacklist");
		
		public static final Security[] VALUES = values();
		
		public final int ID;
		public final String text;
		
		Security(String s)
		{
			ID = ordinal();
			text = s;
		}
	}
	
	public static enum SafeMode
	{
		ENABLED("Enabled"),
		DISABLED("Disabled");
		
		public static final SafeMode[] VALUES = values();
		
		public final int ID;
		public final String text;
		
		SafeMode(String s)
		{
			ID = ordinal();
			text = s;
		}

		public boolean isOn()
		{ return this == ENABLED; }

		public SafeMode next()
		{ return isOn() ? DISABLED : ENABLED; }
	}
	
	public static enum AutoExport
	{
		DISABLED("Disabled"),
		ENABLED("Enabled");
		
		public static final AutoExport[] VALUES = values();
		
		public final int ID;
		public final String text;
		
		AutoExport(String s)
		{
			ID = ordinal();
			text = s;
		}

		public boolean isOn()
		{ return this == ENABLED; }

		public AutoExport next()
		{ return isOn() ? DISABLED : ENABLED; }
	}
	
	public static class Buttons
	{
		public static final int SAFE_MODE = 1;
		public static final int REDSTONE = 2;
		public static final int SECURITY = 3;
		public static final int INV_MODE = 4;
		public static final int AUTO_EXPORT = 5;
	}
	
	/** Just in case I figure out, how packet system works :P */
	public static class NBT
	{
		// Read //
		
		public static void readInv(TileCondenser t, NBTTagCompound tag)
		{
			t.items = InvUtils.readItemsFromNBT(t.items.length, tag, "Items");
		}
		
		public static void readRendering(TileCondenser t, NBTTagCompound tag)
		{
			t.security.readFromNBT(tag);
			
			t.storedEMC = tag.getDouble("StoredEMC");
			t.safeMode = SafeMode.VALUES[tag.getByte("SafeMode")];
			t.redstoneMode = Redstone.VALUES[tag.getByte("RSMode")];
			t.invMode = InvMode.VALUES[tag.getByte("InvMode")];
			t.autoExport = AutoExport.VALUES[tag.getByte("AutoExport")];
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
			t.security.writeToNBT(tag);
			
			tag.setDouble("StoredEMC", t.storedEMC);
			tag.setByte("SafeMode", (byte)t.safeMode.ID);
			tag.setByte("RSMode", (byte)t.redstoneMode.ID);
			tag.setByte("InvMode", (byte)t.invMode.ID);
			tag.setByte("AutoExport", (byte)t.autoExport.ID);
		}
		
		public static void writeAll(TileCondenser t, NBTTagCompound tag)
		{
			writeInv(t, tag);
			writeRendering(t, tag);
			
			tag.setLong("Tick", t.tick);
			tag.setShort("Cooldown", (short)t.cooldown);
		}
	}
}