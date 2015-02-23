package latmod.emcc;
import latmod.core.mod.LC;
import latmod.emcc.api.IEmcStorageItem;
import latmod.emcc.emc.EMCHandler;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class EMCCClientEventHandler
{
	public static final EMCCClientEventHandler instance = new EMCCClientEventHandler();
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(ItemTooltipEvent e)
	{
		Item item = e.itemStack.getItem();
		
		if(item instanceof IEmcStorageItem)
		{
			IEmcStorageItem i = (IEmcStorageItem)item;
			
			double stored = i.getStoredEmc(e.itemStack);
			
			String s = "";
			
			if(LC.proxy.isShiftDown())
			{
				s += stored;
				if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
			}
			else
			{
				double maxStored = i.getMaxStoredEmc(e.itemStack);
				
				if(maxStored == Double.POSITIVE_INFINITY)
				{
					s += stored;
					if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
				}
				else
				{
					s += ( ((long)(stored / maxStored * 100D * 100D)) / 100D );
					if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
					s += " %";
				}
			}
			
			e.toolTip.add(EMCC.mod.translate("storedEMC", s));
		}
		
		if(EMCHandler.hasEE3())
		{
			if(EMCCConfig.General.removeNoEMCTooltip || EMCCConfig.General.forceVanillaEMC)
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
		
		if(!EMCHandler.hasEE3() || EMCCConfig.General.forceVanillaEMC && LC.proxy.isShiftDown())
		{
			float f = EMCHandler.instance().getEMC(e.itemStack);
			if(f > 0)
			{
				f = ((int)(f * 1000F)) / 1000F;
				String s = "" + f;
				if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
				e.toolTip.add("EMC: " + s);
			}
		}
	}
}
