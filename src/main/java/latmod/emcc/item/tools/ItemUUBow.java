package latmod.emcc.item.tools;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.*;

public class ItemUUBow extends ItemToolEMCC // ItemBow
{
	public ItemUUBow(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.Tools.enableBow)
			mod.recipes.addRecipe(new ItemStack(this), " US", "U S", " US",
					'U', EMCCItems.INGOT_UUS,
					'S', Items.string);
	}
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING, ToolInfusion.INFINITY, ToolInfusion.FIRE, ToolInfusion.SHARPNESS, ToolInfusion.KNOCKBACK); }
	
	public boolean isEffective(Block b)
	{ return false; }
	
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer ep, int itemInUseCount)
	{
		if(!damageItem(is, true)) return;
		
		int j = getMaxItemUseDuration(is) - itemInUseCount;

		ArrowLooseEvent event = new ArrowLooseEvent(ep, is, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) return;
		j = event.charge;

		boolean flag = ep.capabilities.isCreativeMode || hasInfusion(is, ToolInfusion.INFINITY);

		if (flag || ep.inventory.hasItem(Items.arrow))
		{
			float f = j / 20F;
			f = (f * f + f * 2F) / 3F;
			if (f < 0.1D) return;
			if (f > 1F) f = 1F;
			
			EntityArrow ea = new EntityArrow(w, ep, f * 2F);
			
			if (f == 1F) ea.setIsCritical(true);
			
			int k = getInfusionLevel(is, ToolInfusion.SHARPNESS);
			if (k > 0) ea.setDamage(ea.getDamage() + (double)k * 0.5D + 0.5D);
			
			int l = getInfusionLevel(is, ToolInfusion.KNOCKBACK);
			if (l > 0) ea.setKnockbackStrength(l);
			
			if (hasInfusion(is, ToolInfusion.FIRE)) ea.setFire(100);
			
			damageItem(is, false);
			w.playSoundAtEntity(ep, "random.bow", 1F, 1F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			
			if (flag) ea.canBePickedUp = 2;
			else ep.inventory.consumeInventoryItem(Items.arrow);
			
			if (!w.isRemote) w.spawnEntityInWorld(ea);
		}
	}
	
	public ItemStack onEaten(ItemStack is, World w, EntityPlayer ep)
	{ return is; }
	
	public int getMaxItemUseDuration(ItemStack is)
	{ return 72000; }
	
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{ return EnumAction.bow; }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		ArrowNockEvent event = new ArrowNockEvent(ep, is);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) return event.result;
		
		if(ep.capabilities.isCreativeMode || ep.inventory.hasItem(Items.arrow) || hasInfusion(is, ToolInfusion.INFINITY))
		{ if(damageItem(is, true)) ep.setItemInUse(is, getMaxItemUseDuration(is)); }
		
		return is;
	}
}