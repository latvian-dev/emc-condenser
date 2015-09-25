package latmod.emcc;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;
import latmod.emcc.api.*;
import latmod.emcc.emc.EMCHandler;
import latmod.ftbu.api.EventFTBUReload;
import latmod.ftbu.mod.FTBU;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EMCCEventHandler
{
	public static final EMCCEventHandler instance = new EMCCEventHandler();
	
	@SubscribeEvent
	public void onReloaded(EventFTBUReload e)
	{
		if(e.side.isServer()) EMCHandler.instance().reloadEMCValues();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(ItemTooltipEvent e)
	{
		Item item = e.itemStack.getItem();
		
		if(item instanceof IEmcStorageItem && FTBU.proxy.isShiftDown())
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
		
		if((EMCCConfig.General.removeNoEMCTooltip || EMCCConfig.General.forceVanillaEMC) && EMCHandler.hasEE3())
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
		
		if((EMCCConfig.General.forceVanillaEMC || !EMCHandler.hasEE3()) && FTBU.proxy.isShiftDown())
		{
			float f = EMCHandler.instance().getEMC(e.itemStack);
			if(f > 0)
			{
				e.toolTip.add("EMC: " + formDouble(f, 1000D));
				if(e.itemStack.stackSize > 1)
					e.toolTip.add("Total EMC: " + formDouble(f * e.itemStack.stackSize, 1000D));
			}
		}
		
		/*
		if(item instanceof IEmcTool)
		{
			IEmcTool i = (IEmcTool)item;
			
			for(ToolInfusion t : ToolInfusion.VALUES)
			{
				int l = i.getInfusionLevel(e.itemStack, t);
				if(l > 0) e.toolTip.add(t.getEnchantment(i.getToolType(e.itemStack)).getTranslatedName(l));
			}
		}*/
	}
	
	private static String formDouble(double d, double d1)
	{
		if(d1 > 0D) d = ((long)(d * d1)) / d1;
		String s = "" + d;
		if(s.endsWith(".0")) s = s.substring(0, s.length() - 2);
		return s;
	}
	
	@SubscribeEvent
	public void onAnvilEvent(AnvilUpdateEvent e)
	{
		if(e.left != null && e.right != null && e.left.getItem() instanceof IEmcTool)
		{
			IEmcTool i = (IEmcTool)e.left.getItem();
			ToolInfusion t = ToolInfusion.get(e.right);
			
			if(t != null && i.canEnchantWith(e.left, t))
			{
				int l = i.getInfusionLevel(e.left, t);
				int lvlsToAdd = Math.min(e.right.stackSize, t.maxLevel - l);
				
				if(lvlsToAdd > 0)
				{
					e.materialCost = lvlsToAdd;
					e.cost = t.requiredLevel * lvlsToAdd;
					e.output = e.left.copy();
					i.setInfusionLevel(e.output, t, l + lvlsToAdd);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDamaged(LivingAttackEvent e)
	{
		if(e.entity instanceof EntityPlayer)
		{
		}
	}
}