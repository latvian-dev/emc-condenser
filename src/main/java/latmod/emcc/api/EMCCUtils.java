package latmod.emcc.api;
import net.minecraft.item.*;

public class EMCCUtils
{
	public static boolean transferEmcToMachine(ItemStack is, IEmcMachine m)
	{
		if(is != null && is.getItem() instanceof IEmcStorageItem)
		{
			double emc = ((IEmcStorageItem)is.getItem()).getStoredEmc(is);
			if(emc == 0D) return false;
			
			double mEmc = m.getStoredEmc();
			double mMaxEmc = m.getEmcCapacity();
			
			double d = Math.min(emc, mMaxEmc - mEmc);
			
			if(d > 0D)
			{
				emc -= d;
				mEmc += d;
				
				m.setStoredEmc(mEmc);
				((IEmcStorageItem)is.getItem()).setStoredEmc(is, emc);
				
				return true;
			}
		}
		
		return false;
	}
}