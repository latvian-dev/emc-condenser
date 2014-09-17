package latmod.emcc;
import java.util.List;

import latmod.core.*;
import latmod.core.mod.LC;
import latmod.emcc.api.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.pahimar.ee3.exchange.*;
import com.pahimar.ee3.item.crafting.RecipeAludel;
import com.pahimar.ee3.recipe.RecipesAludel;

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
			if(s.startsWith("Infused with "))
				e.toolTip.remove(j);
			
			if(s.equalsIgnoreCase("No Exchange Energy value"))
				e.toolTip.remove(j);
		}
		
		List<RecipeAludel> al = RecipesAludel.getInstance().getRecipes();
		
		boolean flag = false;
		
		for(RecipeAludel r : al)
		{
			if(InvUtils.itemsEquals(e.itemStack, r.getRecipeOutput(), false, true))
			{
				if(!flag)
				{
					e.toolTip.add("Aludel Infusion:");
					flag = true;
				}
				
				List<WrappedStack> al1 = r.getRecipeInputsAsWrappedStacks();
				String t = "> " + getItemName(al1.get(0)) + " with " + getItemName(al1.get(1));
				if(r.getRecipeOutput().stackSize > 1) t += " [" + r.getRecipeOutput().stackSize + "]";
				
				if(!e.toolTip.contains(t)) e.toolTip.add(t);
			}
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
