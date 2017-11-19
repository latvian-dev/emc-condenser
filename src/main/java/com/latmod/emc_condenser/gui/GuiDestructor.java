package com.latmod.emc_condenser.gui;

import com.feed_the_beast.ftbl.lib.gui.Button;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;
import com.latmod.emc_condenser.EMCCLang;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class GuiDestructor extends GuiBase
{
	public final ContainerDestructor container;
	public final Button emc;

	public GuiDestructor(ContainerDestructor c)
	{
		super(176, 192);
		container = c;

		emc = new Button(this, 72, 37, 32, 32, "", getTheme().getContainerSlot())
		{
			@Override
			public void onClicked(MouseButton button)
			{
			}

			@Override
			public void addMouseOverText(List<String> list)
			{
				list.add(EMCCLang.EMC.translate(EMCCLang.format(container.destructor.emc, TextFormatting.GOLD)));
			}

			@Override
			public void renderWidget()
			{
				getIcon().draw(this);
				//render EMC
			}
		};
	}

	@Override
	public void addWidgets()
	{
		add(emc);
	}

	@Override
	public GuiScreen getWrapper()
	{
		return new GuiContainerWrapper(this, container);
	}
}