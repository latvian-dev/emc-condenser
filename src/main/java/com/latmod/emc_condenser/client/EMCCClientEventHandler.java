package com.latmod.emc_condenser.client;

import com.feed_the_beast.ftbl.api.EventHandler;
import com.feed_the_beast.ftbl.api.player.RegisterGuiProvidersEvent;
import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.feed_the_beast.ftbl.lib.gui.IGuiWrapper;
import com.latmod.emc_condenser.EMCCLang;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import com.latmod.emc_condenser.block.TileCondenser;
import com.latmod.emc_condenser.client.gui.ContainerCondenser;
import com.latmod.emc_condenser.client.gui.GuiCondenser;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
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

		if (item instanceof IEmcStorageItem)
		{
			IEmcStorageItem i = (IEmcStorageItem) item;
			event.getToolTip().add((i.isActive(event.getItemStack()) ? GuiLang.ACTIVE : GuiLang.INACTIVE).translate());

			int stored = i.getStoredEmc(event.getItemStack());
			int maxStored = i.getMaxStoredEmc(event.getItemStack());

			String text;

			if (stored == Integer.MAX_VALUE)
			{
				text = GuiLang.INFINITE.translate();
			}
			else if (maxStored == Integer.MAX_VALUE)
			{
				text = Integer.toString(stored);
			}
			else
			{
				text = stored + " / " + maxStored;
			}

			event.getToolTip().add(EMCCLang.EMC.translate(TextFormatting.GOLD + text));
		}

		if (GuiScreen.isShiftKeyDown() || (ClientUtils.MC.currentScreen instanceof IGuiWrapper && ((IGuiWrapper) ClientUtils.MC.currentScreen).getWrappedGui() instanceof GuiCondenser))
		{
			int v = EMCValues.getEMC(event.getItemStack()).value;

			if (v > 0)
			{
				event.getToolTip().add(EMCCLang.EMC.translate(TextFormatting.GOLD + Integer.toString(v)));

				if (event.getItemStack().getCount() > 1)
				{
					event.getToolTip().add(EMCCLang.TOTAL_EMC.translate(TextFormatting.GOLD + Integer.toString(v * event.getItemStack().getCount())));
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerGuis(RegisterGuiProvidersEvent event)
	{
		event.register(ContainerCondenser.ID, (player, pos, data) -> new GuiCondenser(new ContainerCondenser(player, (TileCondenser) player.world.getTileEntity(pos))).getWrapper());
	}
}