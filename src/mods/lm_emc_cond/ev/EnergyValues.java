package mods.lm_emc_cond.ev;
import com.pahimar.ee3.emc.EmcRegistry;

import net.minecraft.item.*;

public class EnergyValues
{
	public static final EnergyValues inst = new EnergyValues();
	
	public float getValue(ItemStack is)
	{ return EmcRegistry.getInstance().getEmcValue(is).getValue(); }
}