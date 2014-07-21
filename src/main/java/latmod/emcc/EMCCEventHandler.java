package latmod.emcc;
import java.util.List;
import com.pahimar.ee3.exchange.*;
import com.pahimar.ee3.item.crafting.RecipeAludel;
import com.pahimar.ee3.recipe.RecipesAludel;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import latmod.core.InvUtils;
import latmod.core.LatCore;
import latmod.core.mod.LC;
import latmod.emcc.api.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class EMCCEventHandler
{
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e)
	{
		Item i = e.itemStack.getItem();
		
		if(i instanceof IEmcStorageItem)
		{
			e.toolTip.add(EMCC.mod.translate("storedEMC", num(((IEmcStorageItem)i).getStoredEmc(e.itemStack))));
		}
		
		if(i instanceof IEmcTool)
		{
			String num = "" + ((IEmcTool)i).getEmcPerDmg(e.itemStack);
			if(num.endsWith(".0")) num = num.substring(0, num.length() - 2);
			e.toolTip.add(EMCC.mod.translate("repairEMC", num));
		}
		
		for(int j = 0; j < e.toolTip.size(); j++)
		{
			if(e.toolTip.get(j).startsWith("Infused with "))
			{ e.toolTip.remove(j); break; }
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
			
			if(is.getItemDamage() == LatCore.ANY)
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
	
	private String num(double d)
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