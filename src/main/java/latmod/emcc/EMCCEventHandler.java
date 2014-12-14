package latmod.emcc;
import latmod.core.LatCoreMC;
import latmod.core.mod.LC;
import latmod.emcc.api.IEmcStorageItem;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.pahimar.ee3.exchange.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EMCCEventHandler
{
	@SubscribeEvent
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
		
		for(int j = 0; j < e.toolTip.size(); j++)
		{
			String s = e.toolTip.get(j);
			if(s.equalsIgnoreCase("No Exchange Energy value"))
				e.toolTip.remove(j);
		}
	}
	
	public String getItemName(WrappedStack w)
	{
		Object o = w.getWrappedStack();
		
		String s = "" + o;
		
		if(o instanceof ItemStack)
		{
			ItemStack is = (ItemStack)o;
			s = is.getDisplayName();
			
			if(s.equals("Black Wool")) s = "Wool";
			
			if(is.getItemDamage() == LatCoreMC.ANY)
				s = "Any " + s;
		}
		else if(o instanceof OreStack)
		{
			s = ((OreStack)o).oreName;
			
			if(s == "logWood") s = "Wood";
			if(s == "plankWood") s = "Planks";
			if(s == "stickWood") s = "Stick";
			
			s = "Any " + s;
			//OreDictionary.getOreID(itemStack);
		}
		
		if(w.getStackSize() > 1)
		s = w.getStackSize() + " x " + s;
		
		return s;
	}
}
