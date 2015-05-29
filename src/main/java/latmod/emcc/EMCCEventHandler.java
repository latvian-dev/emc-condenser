package latmod.emcc;

import latmod.core.event.ReloadEvent;
import latmod.core.mod.LC;
import latmod.emcc.api.IEmcStorageItem;
import latmod.emcc.emc.EMCHandler;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;

public class EMCCEventHandler
{
	public static final EMCCEventHandler instance = new EMCCEventHandler();
	
	@SubscribeEvent
	public void onReloaded(ReloadEvent e)
	{
		EMCHandler.instance().reloadEMCValues();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(ItemTooltipEvent e)
	{
		Item item = e.itemStack.getItem();
		
		if(item instanceof IEmcStorageItem && LC.proxy.isShiftDown())
		{
			IEmcStorageItem i = (IEmcStorageItem)item;
			
			double stored = i.getStoredEmc(e.itemStack);
			double maxStored = i.getMaxStoredEmc(e.itemStack);
			
			if(stored == Double.POSITIVE_INFINITY)
				e.toolTip.add("EMC: Infinite");
			else if(maxStored == Double.POSITIVE_INFINITY)
				e.toolTip.add("EMC: " + formDouble(stored, 100D));
			else
				e.toolTip.add("EMC: " + formDouble(stored, 100D) + " [ " + formDouble(stored * 100D / maxStored, 100D) + "% ]");
			
		}
		
		if(EMCHandler.hasEE3() && (EMCCConfig.General.removeNoEMCTooltip || EMCCConfig.General.forceVanillaEMC))
		{
			for(int j = 0; j < e.toolTip.size(); j++)
			{
				String s = e.toolTip.get(j);
				if(s != null && !s.isEmpty())
				{
					if((EMCCConfig.General.removeNoEMCTooltip && s.contains("No Exchange Energy value"))
					|| (EMCCConfig.General.forceVanillaEMC && s.contains("Exchange Energy")))
						e.toolTip.remove(j);
				}
			}
		}
		
		if((!EMCHandler.hasEE3() || EMCCConfig.General.forceVanillaEMC) && LC.proxy.isShiftDown())
		{
			float f = EMCHandler.instance().getEMC(e.itemStack);
			if(f > 0)
			{
				e.toolTip.add("EMC: " + formDouble(f, 1000D));
				if(e.itemStack.stackSize > 1)
					e.toolTip.add("Total EMC: " + formDouble(f * e.itemStack.stackSize, 1000D));
			}
		}
	}
	
	private static String formDouble(double d, double d1)
	{
		if(d1 > 0D) d = ((long)(d * d1)) / d1;
		String s = "" + d;
		if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
		return s;
	}
}