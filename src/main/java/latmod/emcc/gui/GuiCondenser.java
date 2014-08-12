package latmod.emcc.gui;
import java.util.ArrayList;

import latmod.core.mod.LC;
import latmod.core.mod.gui.*;
import latmod.emcc.*;
import latmod.emcc.api.IEmcStorageItem;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiCondenser extends GuiLM
{
	public TileCondenser condenser;
	public ButtonLM buttonSettings, buttonSafeMode, buttonTransItems;
	public WidgetLM barEMC, targetIcon;
	
	public GuiCondenser(ContainerCondenser c)
	{
		super(c);
		condenser = (TileCondenser)c.inv;
		ySize = 240;
		
		widgets.add(buttonSettings = new ButtonLM(this, 153, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				condenser.openGui(false, container.player, EMCCGuis.COND_SETTINGS);
				playClickSound();
			}
		});
		
		widgets.add(buttonSafeMode = new ButtonLM(this, 153, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				condenser.handleGuiButton(false, container.player, EMCCGuis.Buttons.SAFE_MODE, b);
				playClickSound();
			}
		});
		
		widgets.add(buttonTransItems = new ButtonLM(this, 162, 25, 7, 6)
		{
			public void onButtonPressed(int b)
			{
				condenser.transferItems(false, container.player);
				playClickSound();
			}
		});
		
		barEMC = new WidgetLM(this, 30, 9, 118, 16);
		targetIcon = new WidgetLM(this, 8, 9, 16, 16);
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		boolean b = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if(b) GL11.glDisable(GL11.GL_LIGHTING);
		
		super.drawGuiContainerBackgroundLayer(f, x, y);
		
		ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
		
		double emc1 =  EMCC.getEMC(tar);
		
		boolean charging = tar != null && tar.getItem() instanceof IEmcStorageItem;
		
		boolean repairing = tar != null && !charging && condenser.repairTools.isOn() && tar.isItemStackDamageable() && !tar.isStackable();
		
		if(repairing && tar.getItemDamage() > 0)
		{
			ItemStack tar1 = tar.copy();
			if(tar1.hasTagCompound())
				tar1.stackTagCompound.removeTag("ench");
			
			ItemStack tar2 = tar1.copy();
			tar2.setItemDamage(tar1.getItemDamage() - 1);
			
			double ev = EMCC.getEMC(tar1);
			double ev2 = EMCC.getEMC(tar2);
			
			emc1 = ev2 - ev;
		}
		
		if(emc1 > 0L)
		barEMC.render(0, ySize, (condenser.storedEMC % emc1) / emc1, 1D);
		
		if(condenser.items[TileCondenser.SLOT_TARGET] == null)
		targetIcon.render(xSize, 0);
		
		if(condenser.safeMode.isOn())
		buttonSafeMode.render(xSize, 16);
		
		if(b) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(barEMC.mouseOver(mx, my))
		{
			ItemStack tar = condenser.items[TileCondenser.SLOT_TARGET];
			
			double emc1 =  EMCC.getEMC(tar);
			
			boolean charging = tar != null && tar.getItem() instanceof IEmcStorageItem;
			
			boolean repairing = tar != null && !charging && condenser.repairTools.isOn() && tar.isItemStackDamageable() && !tar.isStackable();
			
			if(repairing && tar.getItemDamage() > 0)
			{
				ItemStack tar1 = tar.copy();
				if(tar1.hasTagCompound())
					tar1.stackTagCompound.removeTag("ench");
				
				ItemStack tar2 = tar1.copy();
				tar2.setItemDamage(tar1.getItemDamage() - 1);
				
				double ev = EMCC.getEMC(tar1);
				double ev2 = EMCC.getEMC(tar2);
				
				emc1 = ev2 - ev;
			}
			
			al.add(EnumChatFormatting.GOLD.toString() + "" + formatEMC(condenser.storedEMC) + (emc1 <= 0D ? "" : (" / " + formatEMC(emc1))));
			if(charging && condenser.storedEMC > 0D) al.add(EMCC.mod.translate("charging"));
			else if(emc1 > 0D && repairing && tar.getItemDamage() > 0) al.add(EMCC.mod.translate("repairing"));
		}
		
		if(buttonSettings.mouseOver(mx, my))
			al.add(EMCC.mod.translate("settings"));
		
		if(buttonSafeMode.mouseOver(mx, my))
		{
			al.add(condenser.safeMode.getTitle());
			al.add(condenser.safeMode.getText());
		}
		
		if(buttonTransItems.mouseOver(mx, my))
			al.add(EMCC.mod.translate("takeitems"));
		
		if(targetIcon.mouseOver(mx, my) && condenser.items[TileCondenser.SLOT_TARGET] == null)
			al.add(EMCC.mod.translate("notarget"));
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
	
	public static String formatEMC(double d)
	{
		d = ((long)(d * 1000D)) / 1000D;
		
		String s = "" + d;
		
		if(!LC.proxy.isShiftDown())
		{
			if(d > 1000)
			{
				double d1 = d / 1000D;
				d1 = ((long)(d1 * 1000D)) / 1000D;
				s = "" + d1 + "K";
			}
			
			if(d > 1000000)
			{
				double d1 = d / 1000000D;
				d1 = ((long)(d1 * 100D)) / 100D;
				s = "" + d1 + "M";
			}
		}
		
		if(s.endsWith(".0"))
			s = s.substring(0, s.length() - 2);
		
		return s;
	}
}