package com.latmod.emcc;

import com.feed_the_beast.ftbl.api.EventHandler;
import com.feed_the_beast.ftbl.api.events.ReloadEvent;
import com.latmod.emcc.api.IEmcStorageItem;
import com.latmod.emcc.api.IEmcTool;
import com.latmod.emcc.api.ToolInfusion;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventHandler
public class EMCCEventHandler
{
	public static final ResourceLocation RELOAD_BLACKLIST = new ResourceLocation(EMCC.MOD_ID, "blacklist");
	public static final ResourceLocation RELOAD_EMC = new ResourceLocation(EMCC.MOD_ID, "emc");
	public static final EMCCEventHandler instance = new EMCCEventHandler();

	@SubscribeEvent
	public void onReloaded(ReloadEvent event)
	{
		if (event.getSide().isServer())
		{
			if (event.reload(RELOAD_BLACKLIST))
			{
				Blacklist.INSTANCE.load();
			}

			if (event.reload(RELOAD_EMC))
			{
				VanillaEMC.INSTANCE.load();
			}
		}
	}

	@SubscribeEvent
	public void syncData(EventFTBSync e)
	{
		/* TODO: Sync EMC and Blacklist
		if(e.world.side.isServer())
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			e.syncData.setTag("EMCC", tag);
		}
		else
		{
			NBTTagCompound tag = e.syncData.getCompoundTag("EMCC");
		}*/
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(ItemTooltipEvent event)
	{
		Item item = event.itemStack.getItem();

		if (item instanceof IEmcStorageItem && GuiScreen.isShiftKeyDown())
		{
			IEmcStorageItem i = (IEmcStorageItem) item;

			double stored = i.getStoredEmc(event.itemStack);
			double maxStored = i.getMaxStoredEmc(event.itemStack);

			if (stored == Double.POSITIVE_INFINITY)
			{
				event.toolTip.add("EMC: Infinite");
			}
			else if (maxStored == Double.POSITIVE_INFINITY)
			{
				event.toolTip.add("EMC: " + formDouble(stored, 100D));
			}
			else
			{
				event.toolTip.add("EMC: " + formDouble(stored, 100D) + " [ " + formDouble(stored * 100D / maxStored, 100D) + "% ]");
			}

		}

		if (GuiScreen.isShiftKeyDown())
		{
			float f = VanillaEMC.INSTANCE.getEMC(event.getItemStack());
			if (f > 0)
			{
				event.toolTip.add("EMC: " + formDouble(f, 1000D));
				if (event.itemStack.stackSize > 1)
				{
					event.toolTip.add("Total EMC: " + formDouble(f * event.itemStack.stackSize, 1000D));
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
	public void onAnvilEvent(AnvilUpdateEvent e)
	{
		if (e.left != null && e.right != null && e.left.getItem() instanceof IEmcTool)
		{
			IEmcTool i = (IEmcTool) e.left.getItem();
			ToolInfusion t = ToolInfusion.get(e.right);

			if (t != null && i.canEnchantWith(e.left, t))
			{
				int l = i.getInfusionLevel(e.left, t);
				int lvlsToAdd = Math.min(e.right.stackSize, t.maxLevel - l);

				if (lvlsToAdd > 0)
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
		if (e.entity instanceof EntityPlayer)
		{
		}
	}
}