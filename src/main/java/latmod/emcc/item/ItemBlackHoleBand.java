package latmod.emcc.item;

import java.util.List;

import latmod.core.InvUtils;
import latmod.emcc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.*;

public class ItemBlackHoleBand extends ItemEmcStorage implements IBauble
{
	public ItemBlackHoleBand(String s)
	{
		super(s);
	}
	
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	public double getMaxStoredEmc(ItemStack is)
	{ return Short.MAX_VALUE; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return 1024D; }
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.blackHoleStone_item != -1D)
			mod.recipes.addRecipe(new ItemStack(this), "OEO", "EBE", "OEO",
				'O', EMCCItems.b_uu_block,
				'B', EMCCItems.i_emc_battery,
				'E', Items.ender_pearl);
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{ if(e != null && e instanceof EntityPlayer) onWornTick(is, (EntityPlayer)e); }
	
	public void onWornTick(ItemStack is, EntityLivingBase e)
	{
		if(!(e instanceof EntityPlayer)) return;
		
		EntityPlayer ep = (EntityPlayer)e;
		
		if(is.getItemDamage() == 1 && (e.worldObj.getWorldTime() % 4 == 0))
		{
			double emc = getStoredEmc(is);
			
			if(EMCC.mod.config().tools.blackHoleStone_item == -1D || emc < EMCC.mod.config().tools.blackHoleStone_item) return;
			
			double r = EMCC.mod.config().tools.blackHoleStone_range;
			
			@SuppressWarnings("unchecked")
			List<EntityItem> items = ep.worldObj.getEntitiesWithinAABB(EntityItem.class, ep.boundingBox.expand(r, r, r));
			
			for (EntityItem item : items)
			{
				if (InvUtils.addSingleItemToInv(item.getEntityItem(), ep.inventory, InvUtils.getPlayerSlots(ep), -1, false))
				{
					e.worldObj.spawnParticle("smoke", item.posX, item.posY + item.height / 2F, item.posZ, 0D, 0D, 0D);
					
					if(!e.worldObj.isRemote)
					{
						if(item.delayBeforeCanPickup > 4)
							item.delayBeforeCanPickup = 4;
						if(item.delayBeforeCanPickup != 0) continue;
						
						emc -= EMCC.mod.config().tools.blackHoleStone_item;
						setStoredEmc(is, emc);
						
						item.setLocationAndAngles(ep.posX, ep.posY, ep.posZ, 0F, 0F);
					}
				}
			}
		}
	}
	
	public BaubleType getBaubleType(ItemStack is)
	{ return BaubleType.BELT; }
	
	public void onEquipped(ItemStack is, EntityLivingBase ep) { }
	
	public void onUnequipped(ItemStack is, EntityLivingBase ep) { }
	
	public boolean canEquip(ItemStack is, EntityLivingBase ep)
	{ return is.getItemDamage() == 1; }
	
	public boolean canUnequip(ItemStack is, EntityLivingBase ep)
	{ return true; }
}
