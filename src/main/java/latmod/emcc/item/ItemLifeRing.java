package latmod.emcc.item;

import baubles.api.*;
import cpw.mods.fml.common.Optional;
import ftb.lib.OtherMods;
import latmod.emcc.EMCCItems;
import latmod.emcc.config.EMCCConfigTools;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Optional.Interface(modid = OtherMods.BAUBLES, iface = "baubles.api.IBauble")
public class ItemLifeRing extends ItemEmcStorage implements IBauble
{
	public ItemLifeRing(String s)
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
		if(EMCCConfigTools.life_stone_1hp.getAsDouble() != -1D || EMCCConfigTools.life_stone_food.getAsDouble() != -1D)
			getMod().recipes.addRecipe(new ItemStack(this), "SPS", "PBP", "SPS", 'S', Items.cooked_beef, 'B', EMCCItems.i_emc_battery, 'P', new ItemStack(Items.potionitem, 1, 8225));
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && !ep.isSneaking())
		{
			if(ep.isBurning())
			{
				double emc = getStoredEmc(is);
				
				if(emc >= EMCCConfigTools.life_stone_extinguish.getAsDouble())
				{
					setStoredEmc(is, emc - EMCCConfigTools.life_stone_extinguish.getAsDouble());
					ep.extinguish();
					w.playSoundAtEntity(ep, "random.fizz", 1F, 1F);
				}
			}
		}
		
		return super.onItemRightClick(is, w, ep);
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{
		if(w.isRemote || !(e instanceof EntityPlayer)) return;
		onWornTick(is, (EntityPlayer) e);
	}
	
	public void onWornTick(ItemStack is, EntityLivingBase e)
	{
		if(e == null || e.worldObj.isRemote || !(e instanceof EntityPlayer)) return;
		
		double food = EMCCConfigTools.life_stone_food.getAsDouble();
		double hp1 = EMCCConfigTools.life_stone_1hp.getAsDouble();
		
		if(hp1 == -1D && food == -1D) return;
		
		EntityPlayer ep = (EntityPlayer) e;
		
		if(is.getItemDamage() == 1 && e.worldObj.getWorldTime() % 8 == 0)
		{
			double emc = getStoredEmc(is);
			
			if(food != -1D && emc >= food && ep.getFoodStats().needFood())
			{
				ep.getFoodStats().addStats(1, 0.6F);
				emc -= food;
				setStoredEmc(is, emc);
			}
			
			float hp = ep.getHealth();
			float maxHp = ep.getMaxHealth();
			
			if(hp1 != -1D && hp < maxHp && emc >= hp1)
			{
				ep.setHealth(hp + 1F);
				emc -= hp1;
				if(emc < 0D) emc = 0D;
				setStoredEmc(is, emc);
			}
		}
	}
	
	@Optional.Method(modid = OtherMods.BAUBLES)
	public BaubleType getBaubleType(ItemStack is)
	{ return BaubleType.RING; }
	
	public void onEquipped(ItemStack is, EntityLivingBase ep) { }
	
	public void onUnequipped(ItemStack is, EntityLivingBase ep) { }
	
	public boolean canEquip(ItemStack is, EntityLivingBase ep)
	{ return true; }
	
	public boolean canUnequip(ItemStack is, EntityLivingBase ep)
	{ return true; }
}
