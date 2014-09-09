package latmod.emcc.api;
import net.minecraft.item.ItemStack;

public interface IEmcStorageItem
{
	public boolean canChargeEmc(ItemStack is);
	public boolean canDischargeEmc(ItemStack is);
	public double getStoredEmc(ItemStack is);
	public void setStoredEmc(ItemStack is, double emc);
	public double getMaxEmcCharge(ItemStack is, boolean battery);
	public double getEmcTrasferLimit(ItemStack is);
}