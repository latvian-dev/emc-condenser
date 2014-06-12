package latmod.emcc.tile;
import java.util.*;

import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.*;
import latmod.core.*;
import latmod.core.tile.*;

public class TileEMCC extends TileEntity implements ITileInterface, ISecureTile
{
	public static final int UP = ForgeDirection.UP.ordinal();
	public static final int DOWN = ForgeDirection.DOWN.ordinal();
	public static final int[] NO_SLOTS = { };
	
	public boolean haveToRefresh = false;
	public boolean dropItems = true;
	public boolean isDirty = true;
	public boolean isLoaded = false;
	public long tick = 0L;
	public LMSecurity security = new LMSecurity((String)null);
	
	@Override
	public final TileEntity getTile()
	{ return this; }
	
	public final LMSecurity getSecurity()
	{ return security; }
	
	public boolean enableSecurity()
	{ return true; }
	
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	public void onDataPacket(INetworkManager m, Packet132TileEntityData p)
	{
		readFromNBT(p.data);
	}
	
	public void invalidate()
	{
		if(isLoaded) onUnloaded();
		super.invalidate();
	}

	public void onChunkUnload()
	{
		if(isLoaded) onUnloaded();
		super.onChunkUnload();
	}
	
	public void onLoaded()
	{
		isLoaded = true;
		blockType = null;
		blockType = getBlockType();
		blockMetadata = getMeta();
	}
	
	public void onUnloaded()
	{
		isLoaded = false;
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		tick = tag.getLong("Tick");
		if(tick < 0L) tick = 0L;
		
		if(tag.hasKey("Security"))
		security.readFromNBT(tag.getCompoundTag("Security"));
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		if(tick < 0L) tick = 0L;
		tag.setLong("Tick", tick);
		
		NBTTagCompound securityTag = new NBTTagCompound();
		security.writeToNBT(securityTag);
		tag.setTag("Security", securityTag);
	}
	
	public final void updateEntity()
	{
		if(!isLoaded) onLoaded();
		
		onUpdate();
		
		if(isDirty)
		{
			//worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
			//if(getBlockType() != null) worldObj.func_96440_m(xCoord, yCoord, zCoord, getBlockType().blockID);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			
			isDirty = false;
		}
		
		tick++;
	}
	
	public void onUpdate()
	{
	}

	public void onPlaced()
	{ blockType = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)]; }

	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{ security.owner = ep.username; isDirty = true; }
	
	public void addDropItems(ArrayList<ItemStack> al, int blockID, int meta)
	{ al.add(new ItemStack(blockID, 1, meta)); }
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(this instanceof IGuiTile)
		{ if(!worldObj.isRemote) openGui(ep, 0);
		return true; } return false;
	}
	
	public void openGui(EntityPlayer ep, int ID)
	{ if(this instanceof IGuiTile && !worldObj.isRemote) LatCore.openGui((IGuiTile)this, ID, ep); }

	public void dropItem(ItemStack is, double ox, double oy, double oz)
	{ EntityItem ei = new EntityItem(worldObj, xCoord + 0.5D + ox, yCoord + 0.5D + oy, zCoord + 0.5D + oz, is);
	ei.delayBeforeCanPickup = 20; worldObj.spawnEntityInWorld(ei); }

	@Override
	public int hashCode()
	{ return xCoord | yCoord << 8 | zCoord << 16; }
	
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof TileEMCC)
		{
			TileEMCC t = (TileEMCC)o;
			return t.worldObj.provider.dimensionId == worldObj.provider.dimensionId && t.xCoord == xCoord && t.yCoord == yCoord && t.zCoord == zCoord;
		}

		return false;
	}

	@Override
	public void onInventoryChanged()
	{
		isDirty = true;
	}

	public void setMeta(int m)
	{ worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, m, 2); }

	public int getMeta()
	{ return worldObj.getBlockMetadata(xCoord, yCoord, zCoord); }

	public boolean isPowered(boolean direct)
	{
		if(direct) return isPowered(false);
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
	
	public void onPostPlaced(int s) { }
	
	/** Player may be null */
	public boolean isIndestructible(EntityPlayer ep)
	{ return (ep == null) ? (security.level != LMSecurity.PUBLIC) : !security.canPlayerInteract(ep); }
	
	public boolean canBePlaced(EntityPlayer ep, int sideAt)
	{ return true; }
	
	public void onBroken()
	{
		isDirty = true;
	}
}