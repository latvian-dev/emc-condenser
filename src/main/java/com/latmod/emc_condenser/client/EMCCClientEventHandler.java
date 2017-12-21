package com.latmod.emc_condenser.client;

import com.feed_the_beast.ftblib.events.player.RegisterGuiProvidersEvent;
import com.feed_the_beast.ftblib.lib.EventHandler;
import com.feed_the_beast.ftblib.lib.client.ClientUtils;
import com.feed_the_beast.ftblib.lib.gui.IGuiWrapper;
import com.latmod.emc_condenser.EMCCLang;
import com.latmod.emc_condenser.block.TileConstructor;
import com.latmod.emc_condenser.block.TileDestructor;
import com.latmod.emc_condenser.gui.ContainerConstructor;
import com.latmod.emc_condenser.gui.ContainerDestructor;
import com.latmod.emc_condenser.gui.GuiConstructor;
import com.latmod.emc_condenser.gui.GuiDestructor;
import com.latmod.emc_condenser.util.EMCValues;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author LatvianModder
 */
@EventHandler(Side.CLIENT)
public class EMCCClientEventHandler
{
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();

		if (GuiScreen.isShiftKeyDown())
		{
			long cv = EMCValues.getConstructionEMC(stack).value;
			long dv = EMCValues.getDestructionEMC(stack).value;

			if (cv > 0L || dv > 0L)
			{
				if (cv == dv)
				{
					if (stack.getCount() > 1)
					{
						event.getToolTip().add(EMCCLang.TOTAL_EMC.translate(EMCCLang.format(cv, TextFormatting.GOLD), EMCCLang.format(cv * stack.getCount(), TextFormatting.GOLD)));
					}
					else
					{
						event.getToolTip().add(EMCCLang.EMC.translate(EMCCLang.format(cv, TextFormatting.GOLD)));
					}
				}
				else
				{
					if (stack.getCount() > 1)
					{
						if (cv > 0L)
						{
							event.getToolTip().add(EMCCLang.CTOTAL_EMC.translate(EMCCLang.format(cv, TextFormatting.GREEN), EMCCLang.format(cv * stack.getCount(), TextFormatting.GREEN)));
						}
						if (dv > 0L)
						{
							event.getToolTip().add(EMCCLang.DTOTAL_EMC.translate(EMCCLang.format(dv, TextFormatting.RED), EMCCLang.format(dv * stack.getCount(), TextFormatting.RED)));
						}
					}
					else
					{
						if (cv > 0L)
						{
							event.getToolTip().add(EMCCLang.CEMC.translate(EMCCLang.format(cv, TextFormatting.GREEN)));
						}
						if (dv > 0L)
						{
							event.getToolTip().add(EMCCLang.DEMC.translate(EMCCLang.format(dv, TextFormatting.RED)));
						}
					}
				}
			}
		}
		else if (ClientUtils.MC.currentScreen instanceof IGuiWrapper && ((IGuiWrapper) ClientUtils.MC.currentScreen).getWrappedGui() instanceof GuiConstructor)
		{
			int cv = EMCValues.getConstructionEMC(stack).value;
			event.getToolTip().add(EMCCLang.EMC.translate(EMCCLang.format(cv, cv > 0 ? TextFormatting.GREEN : TextFormatting.RESET)));
		}
		else if (ClientUtils.MC.currentScreen instanceof IGuiWrapper && ((IGuiWrapper) ClientUtils.MC.currentScreen).getWrappedGui() instanceof GuiDestructor)
		{
			int dv = EMCValues.getDestructionEMC(stack).value;
			event.getToolTip().add(EMCCLang.EMC.translate(EMCCLang.format(dv, dv > 0 ? TextFormatting.RED : TextFormatting.RESET)));

		}
	}

	@SubscribeEvent
	public static void registerGuis(RegisterGuiProvidersEvent event)
	{
		event.register(ContainerConstructor.ID, (player, pos, data) -> new GuiConstructor(new ContainerConstructor(player, (TileConstructor) player.world.getTileEntity(pos))).getWrapper());
		event.register(ContainerDestructor.ID, (player, pos, data) -> new GuiDestructor(new ContainerDestructor(player, (TileDestructor) player.world.getTileEntity(pos))).getWrapper());
	}
}