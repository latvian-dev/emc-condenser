package latmod.emcc.item;

import cpw.mods.fml.relauncher.*;
import latmod.emcc.api.IEmcStorageItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class ItemEmcStorage extends ItemEMCC implements IEmcStorageItem
{
	private static final String NBT_KEY = "StoredEMC";
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_enabled;
	
	public ItemEmcStorage(String s)
	{
		super(s);
		setMaxDamage(0);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote)
		{
			if(ep.isSneaking())
			{
				int dmg = is.getItemDamage();
				
				if(dmg == 0 || dmg == 1) is.setItemDamage(dmg == 0 ? 1 : 0);
				
				w.playSoundAtEntity(ep, "random.orb", 1F, (dmg % 2 == 0) ? 1F : 0.5F);
			}
		}
		
		return is;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(getMod().assets + "stones/" + itemName);
		icon_enabled = ir.registerIcon(getMod().assets + "stones/" + itemName + "_enabled");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{ return is.getItemDamage() == 1 ? icon_enabled : itemIcon; }
	
	public boolean canChargeEmc(ItemStack is)
	{ return true; }

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
	
	public void onEquipped(ItemStack is, EntityLivingBase ep) { }
	
	public void onUnequipped(ItemStack is, EntityLivingBase ep) { }
	
	public boolean canEquip(ItemStack is, EntityLivingBase ep)
	{ return true; }
	
	public boolean canUnequip(ItemStack is, EntityLivingBase ep)
	{ return true; }
}