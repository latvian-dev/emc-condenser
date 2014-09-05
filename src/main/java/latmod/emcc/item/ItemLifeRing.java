package latmod.emcc.item;

import latmod.emcc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.*;

public class ItemLifeRing extends ItemEmcStorage implements IBauble
{
	public ItemLifeRing(String s)
	{
		super(s);
	}
	
	public boolean canDischargeEmc(ItemStack is)
	{ return false; }
	
	public double getMaxEmcChargeFromBattery(ItemStack is)
	{ return 2048D; }
	
	public double getEmcTrasferLimit(ItemStack is)
	{ return 128D; }
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.lifeStone_1hp != -1D || EMCC.mod.config().tools.lifeStone_food != -1D)
		EMCC.mod.recipes().addRecipe(new ItemStack(this), "SPS", "PBP", "SPS",
				'S', Items.cooked_beef,
				'B', EMCCItems.i_emc_battery,
				'P', new ItemStack(Items.potionitem, 1, 8225));
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{
		if(w.isRemote || !(e instanceof EntityPlayer)) return;
		onWornTick(is, (EntityPlayer)e);
	}

	public void onWornTick(ItemStack is, EntityLivingBase e)
	{
		if(e == null || e.worldObj.isRemote || !(e instanceof EntityPlayer)) return;
		if(EMCC.mod.config().tools.lifeStone_1hp == -1D && EMCC.mod.config().tools.lifeStone_food == -1D) return;
		
		EntityPlayer ep = (EntityPlayer)e;
		
		if(is.getItemDamage() == 1 && e.worldObj.getWorldTime() % 8 == 0)
		{
			double emc = getStoredEmc(is);
			
			if(EMCC.mod.config().tools.lifeStone_food != -1D && emc >= EMCC.mod.config().tools.lifeStone_food && ep.getFoodStats().needFood())
			{
				ep.getFoodStats().addStats(1, 0.6F);
				emc -= EMCC.mod.config().tools.lifeStone_food;
				setStoredEmc(is, emc);
			}
			
			float hp = ep.getHealth();
			float maxHp = ep.getMaxHealth();
			
			if(EMCC.mod.config().tools.lifeStone_1hp != -1D && hp < maxHp && emc >= EMCC.mod.config().tools.lifeStone_1hp)
			{
				ep.setHealth(hp + 1F);
				emc -= EMCC.mod.config().tools.lifeStone_1hp;
				if(emc < 0D) emc = 0D;
				setStoredEmc(is, emc);
			}
		}
	}
	
	public BaubleType getBaubleType(ItemStack is)
	{ return BaubleType.RING; }
	
	public void onEquipped(ItemStack is, EntityLivingBase ep) { }
	
	public void onUnequipped(ItemStack is, EntityLivingBase ep) { }
	
	public boolean canEquip(ItemStack is, EntityLivingBase ep)
	{ return is.getItemDamage() == 1; }
	
	public boolean canUnequip(ItemStack is, EntityLivingBase ep)
	{ return true; }
}
