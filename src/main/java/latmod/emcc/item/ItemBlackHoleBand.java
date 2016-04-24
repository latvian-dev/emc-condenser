package latmod.emcc.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.Optional;
import ftb.lib.OtherMods;
import ftb.lib.api.item.LMInvUtils;
import latmod.emcc.EMCCItems;
import latmod.emcc.config.EMCCConfigTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemBlackHoleBand extends ItemEmcStorage implements IBauble
{
	public ItemBlackHoleBand(String s)
	{
		super(s);
	}
	
	@Override
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	@Override
	public double getMaxStoredEmc(ItemStack is)
	{ return Short.MAX_VALUE; }
	
	@Override
	public double getEmcTrasferLimit(ItemStack is)
	{ return 1024D; }
	
	@Override
	public void loadRecipes()
	{
		if(EMCCConfigTools.black_hole_stone_item.getAsDouble() != -1D)
			getMod().recipes.addRecipe(new ItemStack(this), "OEO", "EBE", "OEO", 'O', EMCCItems.b_uu_block, 'B', EMCCItems.i_emc_battery, 'E', Items.ender_pearl);
	}
	
	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{ if(e != null && e instanceof EntityPlayer) onWornTick(is, (EntityPlayer) e); }
	
	@Override
	public void onWornTick(ItemStack is, EntityLivingBase e)
	{
		if(!(e instanceof EntityPlayer)) return;
		
		EntityPlayer ep = (EntityPlayer) e;
		
		if(is.getItemDamage() == 1 && (e.worldObj.getWorldTime() % 4 == 0))
		{
			double emc = getStoredEmc(is);
			double itemCost = EMCCConfigTools.black_hole_stone_item.getAsDouble();
			if(itemCost == -1F || emc < itemCost) return;
			double r = EMCCConfigTools.black_hole_stone_range.getAsDouble();
			
			@SuppressWarnings("unchecked") List<EntityItem> items = ep.worldObj.getEntitiesWithinAABB(EntityItem.class, ep.boundingBox.expand(r, r, r));
			
			for(EntityItem item : items)
			{
				if(LMInvUtils.addSingleItemToInv(item.getEntityItem(), ep.inventory, LMInvUtils.getPlayerSlots(ep), -1, false))
				{
					e.worldObj.spawnParticle("smoke", item.posX, item.posY + item.height / 2F, item.posZ, 0D, 0D, 0D);
					
					if(!e.worldObj.isRemote)
					{
						if(item.delayBeforeCanPickup > 4) item.delayBeforeCanPickup = 4;
						if(item.delayBeforeCanPickup != 0) continue;
						
						emc -= itemCost;
						setStoredEmc(is, emc);
						
						item.setLocationAndAngles(ep.posX, ep.posY, ep.posZ, 0F, 0F);
					}
				}
			}
		}
	}
	
	@Override
	@Optional.Method(modid = OtherMods.BAUBLES)
	public BaubleType getBaubleType(ItemStack is)
	{ return BaubleType.BELT; }
}
