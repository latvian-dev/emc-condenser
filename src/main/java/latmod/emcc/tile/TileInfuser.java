package latmod.emcc.tile;
import latmod.core.ParticleHelper;
import latmod.core.mod.tile.TileLM;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileInfuser extends TileLM
{
	public ItemStack itemInfusing;
	public ToolInfusion infusion;
	public int level;
	public int timer;
	
	public TileInfuser()
	{
		items = null;
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(!worldObj.isRemote)
		{
		}
		
		return true;
	}
	
	public void onUpdate()
	{
		worldObj.spawnParticle("townaura", xCoord + ParticleHelper.rand.nextFloat(), yCoord + 1.1F, zCoord + ParticleHelper.rand.nextFloat(), 0D, 0D, 0D);
		
		if(!worldObj.isRemote)
		{
		}
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		if(tag.hasKey("ItemInfusing"))
		{
			itemInfusing = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ItemInfusing"));
			infusion = ToolInfusion.get(tag.getString("Infusion"));
			level = tag.getByte("Level");
			timer = tag.getShort("Timer");
		}
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		if(itemInfusing != null)
		{
			NBTTagCompound tag1 = new NBTTagCompound();
			itemInfusing.writeToNBT(tag1);
			
			tag.setTag("ItemInfusing", tag1);
			tag.setString("Infusion", infusion.name);
			tag.setByte("Level", (byte)level);
			tag.setShort("Timer", (short)timer);
		}
	}
}