package latmod.emcc.item;
import java.util.List;
import cpw.mods.fml.relauncher.*;
import latmod.core.*;
import latmod.emcc.*;
import latmod.emcc.api.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class ItemEmcStorage extends ItemEMCC implements IEmcStorageItem
{
	private static final String NBT_KEY = "StoredEMC";
	
	public static final String names[] =
	{
		"battery",
		"lifeStone",
		"blackHoleStone",
	};
	
	@SideOnly(Side.CLIENT)
	public Icon icons[];
	
	@SideOnly(Side.CLIENT)
	public Icon icons_enabled[];
	
	public ItemEmcStorage(int id, String s)
	{
		super(id, s);
		setMaxDamage(0);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	public void onPostLoaded()
	{
		itemsAdded.add(new ItemStack(this, 1, 0));
		itemsAdded.add(new ItemStack(this, 1, 2));
		itemsAdded.add(new ItemStack(this, 1, 4));
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableBattery)
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 0), "QRQ", "QUQ", "QGQ",
				Character.valueOf('Q'), Item.netherQuartz,
				Character.valueOf('R'), Item.redstone,
				Character.valueOf('G'), Item.glowstone,
				Character.valueOf('U'), EMCCItems.UU_ITEM);
		
		if(EMCC.config.tools.lifeStone_1hp != -1D || EMCC.config.tools.lifeStone_food != -1D)
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 2), "SPS", "PBP", "SPS",
				Character.valueOf('S'), Item.beefCooked,
				Character.valueOf('B'), new ItemStack(this, 1, 0),
				Character.valueOf('P'), new ItemStack(Item.potion, 1, 8225));
		
		if(EMCC.config.tools.blackHoleStone_item != -1D)
		EMCC.recipes.addRecipe(new ItemStack(this, 1, 4), "OEO", "EBE", "OEO",
				Character.valueOf('O'), EMCCItems.UU_BLOCK,
				Character.valueOf('B'), new ItemStack(this, 1, 0),
				Character.valueOf('E'), Item.enderPearl);
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return mod.getItemName(names[is.getItemDamage() / 2]); }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote)
		{
			int dmg = is.getItemDamage();
			
			if(ep.isSneaking())
			{
				if(dmg == 0 || dmg == 1)
					is.setItemDamage(dmg == 0 ? 1 : 0);
				else if(dmg == 2 || dmg == 3)
					is.setItemDamage(dmg == 2 ? 3 : 2);
				else if(dmg == 4 || dmg == 5)
					is.setItemDamage(dmg == 4 ? 5 : 4);
				
				w.playSoundAtEntity(ep, "random.orb", 1F, (dmg % 2 == 0) ? 1F : 0.5F);
			}
		}
		
		return is;
	}
	
	public void onUpdate(ItemStack is, World w, Entity e, int t, boolean b)
	{
		if(e instanceof EntityPlayer)
		{
			EntityPlayer ep = (EntityPlayer)e;
			
			if(is.getItemDamage() == 1 && (w.getWorldTime() % 8 == 0))
			{
				if(w.isRemote) return;
				
				if(!EMCC.config.tools.enableBattery) return;
				
				double emc = getStoredEmc(is);
				
				if(emc < 1D) return;
				
				for(int i = 0; i < ep.inventory.getSizeInventory(); i++)
				{
					ItemStack is1 = ep.inventory.getStackInSlot(i);
					
					if(is1 != null && is1.getItem() instanceof IEmcTool)
					{
						int dmg = is1.getItemDamage();
						
						if(dmg > 0 && is1.isItemStackDamageable())
						{
							double perDmg = ((IEmcTool)is1.getItem()).getEmcPerDmg(is1);
							
							if(perDmg < 1D) continue;
							
							if(emc >= perDmg)
							{
								emc -= perDmg;
								is1.setItemDamage(is1.getItemDamage() - 1);
								ep.inventory.onInventoryChanged();
							}
							
							setStoredEmc(is, emc);
							
							if(emc < 1D) return;
							continue;
						}
					}
					
					if(is1 != null && is1.getItem() instanceof IEmcStorageItem)
					{
						IEmcStorageItem si = (IEmcStorageItem)is1.getItem();
						
						double max = si.getMaxEmcChargeFromBattery(is1);
						
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
								ep.inventory.onInventoryChanged();
							}
						}
					}
				}
			}
			
			if(is.getItemDamage() == 3 && w.getWorldTime() % 8 == 0)
			{
				if(w.isRemote) return;
				
				if(EMCC.config.tools.lifeStone_1hp == -1D && EMCC.config.tools.lifeStone_food == -1D) return;
				
				double emc = getStoredEmc(is);
				
				if(EMCC.config.tools.lifeStone_food != -1D && emc >= EMCC.config.tools.lifeStone_food && ep.getFoodStats().needFood())
				{
					ep.getFoodStats().setFoodLevel(ep.getFoodStats().getFoodLevel() + 1);
					emc -= EMCC.config.tools.lifeStone_food;
					setStoredEmc(is, emc);
				}
				
				float hp = ep.getHealth();
				float maxHp = ep.getMaxHealth();
				
				if(EMCC.config.tools.lifeStone_1hp != -1D && hp < maxHp && emc >= EMCC.config.tools.lifeStone_1hp)
				{
					ep.setHealth(hp + 1F);
					emc -= EMCC.config.tools.lifeStone_1hp;
					if(emc < 0D) emc = 0D;
					setStoredEmc(is, emc);
				}
			}
			
			if(is.getItemDamage() == 5 && (w.getWorldTime() % 4 == 0))
			{
				double emc = getStoredEmc(is);
				
				if(EMCC.config.tools.blackHoleStone_item == -1D || emc < EMCC.config.tools.blackHoleStone_item) return;
				
				double r = EMCC.config.tools.blackHoleStone_range;
				
				@SuppressWarnings("unchecked")
				List<EntityItem> items = ep.worldObj.getEntitiesWithinAABB(EntityItem.class, ep.boundingBox.expand(r, r, r));
				
				for (EntityItem item : items)
				{
					if (InvUtils.addSingleItemToInv(item.getEntityItem(), ep.inventory, InvUtils.getPlayerSlots(ep), -1, false))
					{
						w.spawnParticle("smoke", item.posX, item.posY + item.height / 2F, item.posZ, 0D, 0D, 0D);
						
						if(!w.isRemote)
						{
							if(item.delayBeforeCanPickup > 4)
								item.delayBeforeCanPickup = 4;
							if(item.delayBeforeCanPickup != 0) continue;
							
							emc -= EMCC.config.tools.blackHoleStone_item;
							setStoredEmc(is, emc);
							
							item.setLocationAndAngles(ep.posX, ep.posY, ep.posZ, 0F, 0F);
						}
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		icons = new Icon[names.length];
		icons_enabled = new Icon[names.length];
		
		for(int i = 0; i < icons.length; i++)
		{
			icons[i] = ir.registerIcon(mod.assets + "stones/" + names[i]);
			icons_enabled[i] = ir.registerIcon(mod.assets + "stones/" + names[i] + "_enabled");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack is, int r)
	{
		int dmg = is.getItemDamage();
		if(dmg >= icons.length * 2) return icons[0];
		
		return (dmg % 2 == 1) ? icons_enabled[dmg / 2] : icons[dmg / 2];
	}
	
	public boolean canChargeEmc(ItemStack is)
	{ return true; }

	public boolean canDischargeEmc(ItemStack is)
	{ return is.getItemDamage() < 2; }
	
	public void setStoredEmc(ItemStack is, double emc)
	{
		if(emc <= 0D)
		{
			if(is.hasTagCompound()) is.stackTagCompound.removeTag(NBT_KEY);
		}
		else
		{
			if(!is.hasTagCompound()) is.stackTagCompound = new NBTTagCompound();
			is.stackTagCompound.setDouble(NBT_KEY, emc);
		}
	}
	
	public double getStoredEmc(ItemStack is)
	{ return is.hasTagCompound() ? is.stackTagCompound.getDouble(NBT_KEY) : 0D; }

	public double getMaxEmcChargeFromBattery(ItemStack is)
	{
		if(is.getItemDamage() < 2) return 0D;
		if(is.getItemDamage() < 4) return 2048D;
		if(is.getItemDamage() < 6) return 1024D;
		if(is.getItemDamage() < 8) return 2048D;
		return 0D;
	}

	public double getEmcTrasferLimit(ItemStack is)
	{
		if(is.getItemDamage() < 2) return Integer.MAX_VALUE;
		return 128D;
	}
}