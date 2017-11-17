package com.latmod.emc_condenser.client;

import com.feed_the_beast.ftbl.api.EventHandler;
import com.feed_the_beast.ftbl.api.player.RegisterGuiProvidersEvent;
import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.feed_the_beast.ftbl.lib.gui.IGuiWrapper;
import com.latmod.emc_condenser.EMCCLang;
import com.latmod.emc_condenser.api.IEmcStorageItem;
import com.latmod.emc_condenser.block.TileDestructor;
import com.latmod.emc_condenser.client.gui.ContainerDestructor;
import com.latmod.emc_condenser.client.gui.GuiCondenser;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.text.DecimalFormat;

/**
 * @author LatvianModder
 */
@EventHandler(Side.CLIENT)
public class EMCCClientEventHandler
{
	private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###,###");

	private static String format(long num, TextFormatting color)
	{
		return color + FORMATTER.format(num) + TextFormatting.RESET;
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();

		if (item instanceof IEmcStorageItem)
		{
			IEmcStorageItem i = (IEmcStorageItem) item;
			event.getToolTip().add((i.isActive(stack) ? GuiLang.ACTIVE : GuiLang.INACTIVE).translate());

			int stored = i.getStoredEmc(stack);
			int maxStored = i.getMaxStoredEmc(stack);

			if (stored == Integer.MAX_VALUE)
			{
				event.getToolTip().add(EMCCLang.EMC.translate(TextFormatting.GOLD + GuiLang.INFINITE.translate()));
			}
			else if (maxStored == Integer.MAX_VALUE)
			{
				event.getToolTip().add(EMCCLang.EMC.translate(format(stored, TextFormatting.GOLD)));
			}
			else
			{
				event.getToolTip().add(EMCCLang.EMC.translate(format(stored, TextFormatting.GOLD) + " / " + format(maxStored, TextFormatting.GOLD)));
			}
		}

		if (GuiScreen.isShiftKeyDown() || (ClientUtils.MC.currentScreen instanceof IGuiWrapper && ((IGuiWrapper) ClientUtils.MC.currentScreen).getWrappedGui() instanceof GuiCondenser))
		{
			long cv = EMCValues.getConstructionEMC(stack).value;
			long dv = EMCValues.getDestructionEMC(stack).value;

			if (cv > 0L || dv > 0L)
			{
				if (cv == dv)
				{
					if (stack.getCount() > 1)
					{
						event.getToolTip().add(EMCCLang.TOTAL_EMC.translate(format(cv, TextFormatting.GOLD), format(cv * stack.getCount(), TextFormatting.GOLD)));
					}
					else
					{
						event.getToolTip().add(EMCCLang.EMC.translate(format(cv, TextFormatting.GOLD)));
					}
				}
				else
				{
					if (stack.getCount() > 1)
					{
						if (cv > 0L)
						{
							event.getToolTip().add(EMCCLang.CTOTAL_EMC.translate(format(cv, TextFormatting.GREEN), format(cv * stack.getCount(), TextFormatting.GREEN)));
						}
						if (dv > 0L)
						{
							event.getToolTip().add(EMCCLang.DTOTAL_EMC.translate(format(dv, TextFormatting.RED), format(dv * stack.getCount(), TextFormatting.RED)));
						}
					}
					else
					{
						if (cv > 0L)
						{
							event.getToolTip().add(EMCCLang.CEMC.translate(format(cv, TextFormatting.GREEN)));
						}
						if (dv > 0L)
						{
							event.getToolTip().add(EMCCLang.DEMC.translate(format(dv, TextFormatting.RED)));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerGuis(RegisterGuiProvidersEvent event)
	{
		event.register(ContainerDestructor.ID, (player, pos, data) -> new GuiCondenser(new ContainerDestructor(player, (TileDestructor) player.world.getTileEntity(pos))).getWrapper());
	}
}