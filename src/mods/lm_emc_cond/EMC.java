package mods.lm_emc_cond;
import com.pahimar.ee3.emc.*;

import net.minecraft.item.*;

public class EMC
{
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		EmcValue e = EmcRegistry.getInstance().getEmcValue(is);
		return (e == null) ? 0F : e.getValue();
	}
}