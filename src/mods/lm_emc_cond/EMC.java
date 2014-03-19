package mods.lm_emc_cond;
import com.pahimar.ee3.emc.EmcRegistry;
import net.minecraft.item.*;

public class EMC
{
	public static float getEMC(ItemStack is)
	{
		if(is == null) return 0F;
		try { return EmcRegistry.getInstance().getEmcValue(is).getValue(); }
		catch(Exception e) { e.printStackTrace(); return 0F; }
	}
}