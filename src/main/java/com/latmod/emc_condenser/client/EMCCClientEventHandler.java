package com.latmod.emc_condenser.client;

import com.feed_the_beast.ftbl.api.EventHandler;
import com.feed_the_beast.ftbl.api.events.registry.RegisterGuiProvidersEvent;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import com.latmod.emc_condenser.block.TileCondenser;
import com.latmod.emc_condenser.client.gui.ContainerCondenser;
import com.latmod.emc_condenser.client.gui.GuiCondenser;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author LatvianModder
 */
@EventHandler(Side.CLIENT)
public class EMCCClientEventHandler
{
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		Item item = event.getItemStack().getItem();

		if (item instanceof IEmcStorageItem && GuiScreen.isShiftKeyDown())
		{
			IEmcStorageItem i = (IEmcStorageItem) item;
			event.getToolTip().add((i.isActive(event.getItemStack()) ? GuiLang.ACTIVE : GuiLang.INACTIVE).translate());

			int stored = i.getStoredEmc(event.getItemStack());
			int maxStored = i.getMaxStoredEmc(event.getItemStack());

			if (stored == Integer.MAX_VALUE)
			{
				event.getToolTip().add("EMC: Infinite");
			}
			else if (maxStored == Integer.MAX_VALUE)
			{
				event.getToolTip().add("EMC: " + stored);
			}
			else
			{
				event.getToolTip().add("EMC: " + stored + " / " + maxStored);
			}

		}

		if (GuiScreen.isShiftKeyDown())
		{
			int v = EMCValues.getEMC(event.getItemStack()).value;

			if (v > 0)
			{
				event.getToolTip().add("EMC: " + v);

				if (event.getItemStack().getCount() > 1)
				{
					event.getToolTip().add("Total EMC: " + (v * event.getItemStack().getCount()));
				}
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
		if (d1 > 0D)
		{
			d = ((long) (d * d1)) / d1;
		}
		String s = "" + d;
		if (s.endsWith(".0"))
		{
			s = s.substring(0, s.length() - 2);
		}
		return s;
	}

	@SubscribeEvent
	public static void registerGuis(RegisterGuiProvidersEvent event)
	{
		event.register(ContainerCondenser.ID, (player, pos, data) -> new GuiCondenser(new ContainerCondenser(player, (TileCondenser) player.world.getTileEntity(pos))).getWrapper());
	}
}