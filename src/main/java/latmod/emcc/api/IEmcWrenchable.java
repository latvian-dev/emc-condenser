package latmod.emcc.api;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IEmcWrenchable
{
	public boolean canWrench(EntityPlayer ep);
	public void readFromWrench(NBTTagCompound tag);
	public void writeToWrench(NBTTagCompound tag);
	public void onWrenched(EntityPlayer ep, ItemStack is);
	public ItemStack getBlockToPlace();
}