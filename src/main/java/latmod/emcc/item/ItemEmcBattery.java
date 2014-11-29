package latmod.emcc.item;

import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.BaublesApi;

public class ItemEmcBattery extends ItemEmcStorage
{
	public ItemEmcBattery(String s)
	{
		super(s);
	}
	
	public boolean canDischargeEmc(ItemStack is)
	{ return true; }
	
	public double getMaxStoredEmc(ItemStack is)
	{ return Double.POSITIVE_INFINITY; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return Integer.MAX_VALUE; }
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableBattery)
			mod.recipes.addRecipe(new ItemStack(this), "QRQ", "QUQ", "QGQ",
				'Q', ODItems.QUARTZ,
				'R', ODItems.REDSTONE,
				'G', ODItems.GLOWSTONE,
				'U', EMCCItems.ITEM_UUS);
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{
		if(w.isRemote || !(e instanceof EntityPlayer)) return;
		
		EntityPlayer ep = (EntityPlayer)e;
		
		if(is.getItemDamage() == 1 && (w.getWorldTime() % 8 == 0))
		{
			if(!EMCC.mod.config().tools.enableBattery) return;
			
			double emc = getStoredEmc(is);
			
			if(emc < 1D) return;
			
			for(int i = 0; i < ep.inventory.getSizeInventory(); i++)
			{
				ItemStack is1 = ep.inventory.getStackInSlot(i);
				
				/*if(is1 != null && is1.getItem() instanceof IEmcTool)
				{
					int dmg = is1.getItemDamage();
					
					if(dmg > 0 && is1.isItemStackDamageable())
					{
						double perDmg = EMCC.mod.config().tools.toolEmcPerDamage;
						
						if(perDmg < 1D) continue;
						
						if(emc >= perDmg)
						{
							emc -= perDmg;
							is1.setItemDamage(is1.getItemDamage() - 1);
							ep.inventory.markDirty();
						}
						
						setStoredEmc(is, emc);
						
						if(emc < 1D) return;
						continue;
					}
				}*/
				
				if(is1 != null && is1.getItem() instanceof IEmcStorageItem && is1.getItem() != this)
				{
					IEmcStorageItem si = (IEmcStorageItem)is1.getItem();
					
					double max = si.getMaxStoredEmc(is1);
					
					if(max > 0D)
					{
						double siEmc = si.getStoredEmc(is1);
						
						double a = Math.min(si.getEmcTrasferLimit(is1), max - siEmc);
						
						if(a > 0D && emc >= a)
						{
							emc -= a;
							setStoredEmc(is, emc);
							si.setStoredEmc(is1, siEmc + a);
							ep.inventory.setInventorySlotContents(i, is1);
							ep.inventory.markDirty();
						}
					}
				}
			}
			
			IInventory baubInv = BaublesApi.getBaubles(ep);
			
			if(baubInv != null)
			{
				if(emc < 1D) return;
				
				for(int i = 0; i < baubInv.getSizeInventory(); i++)
				{
					ItemStack is1 = baubInv.getStackInSlot(i);
					
					if(is1 != null && is1.getItem() instanceof IEmcStorageItem)
					{
						IEmcStorageItem si = (IEmcStorageItem)is1.getItem();
						
						double max = si.getMaxStoredEmc(is1);
						
						if(max > 0D)
						{
							double siEmc = si.getStoredEmc(is1);
							
							double a = Math.min(si.getEmcTrasferLimit(is1), max - siEmc);
							
							if(a > 0D && emc >= a)
							{
								emc -= a;
								setStoredEmc(is, emc);
								si.setStoredEmc(is1, siEmc + a);
								baubInv.setInventorySlotContents(i, is1);
								baubInv.markDirty();
							}
						}
					}
				}
			}
		}
	}
}