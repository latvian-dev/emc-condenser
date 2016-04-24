package latmod.emcc.item.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import latmod.emcc.api.EnumToolType;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemUUBow extends ItemToolEMCC // ItemBow
{
	@SideOnly(Side.CLIENT)
	public IIcon[] pullIcons;
	
	public ItemUUBow(String s)
	{
		super(s);
	}
	
	@Override
	public void loadRecipes()
	{
		if(EMCCConfigTools.bow.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this), " US", "U S", " US", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', Items.string);
	}
	
	@Override
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING, ToolInfusion.INFINITY, ToolInfusion.FIRE, ToolInfusion.SHARPNESS, ToolInfusion.KNOCKBACK); }
	
	@Override
	public boolean isEffective(Block b)
	{ return false; }
	
	@Override
	public EnumToolType getToolType(ItemStack is)
	{ return EnumToolType.BOW; }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer ep, int itemInUseCount)
	{
		if(!damageItem(is, true)) return;
		
		int j = getMaxItemUseDuration(is) - itemInUseCount;
		
		ArrowLooseEvent event = new ArrowLooseEvent(ep, is, j);
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) return;
		j = event.charge;
		
		boolean flag = ep.capabilities.isCreativeMode || hasInfusion(is, ToolInfusion.INFINITY);
		
		if(flag || ep.inventory.hasItem(Items.arrow))
		{
			float f = j / 20F;
			f = (f * f + f * 2F) / 3F;
			if(f < 0.1D) return;
			if(f > 1F) f = 1F;
			
			EntityArrow ea = new EntityArrow(w, ep, f * 2F);
			
			if(f == 1F) ea.setIsCritical(true);
			
			int k = getInfusionLevel(is, ToolInfusion.SHARPNESS);
			if(k > 0) ea.setDamage(ea.getDamage() + (double) k * 0.5D + 0.5D);
			
			int l = getInfusionLevel(is, ToolInfusion.KNOCKBACK);
			if(l > 0) ea.setKnockbackStrength(l);
			
			if(hasInfusion(is, ToolInfusion.FIRE)) ea.setFire(100);
			
			if(!ep.capabilities.isCreativeMode) damageItem(is, false);
			w.playSoundAtEntity(ep, "random.bow", 1F, 1F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			
			if(flag) ea.canBePickedUp = 2;
			else ep.inventory.consumeInventoryItem(Items.arrow);
			
			if(!w.isRemote) w.spawnEntityInWorld(ea);
		}
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World w, EntityPlayer ep)
	{ return is; }
	
	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{ return 72000; }
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{ return EnumAction.bow; }
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		ArrowNockEvent event = new ArrowNockEvent(ep, is);
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) return event.result;
		
		if(ep.capabilities.isCreativeMode || ep.inventory.hasItem(Items.arrow) || hasInfusion(is, ToolInfusion.INFINITY))
		{ if(damageItem(is, true)) ep.setItemInUse(is, getMaxItemUseDuration(is)); }
		
		return is;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(getMod().lowerCaseModID + ":tools/bow_0");
		
		pullIcons = new IIcon[3];
		for(int i = 0; i < pullIcons.length; i++)
			pullIcons[i] = ir.registerIcon(getMod().lowerCaseModID + ":tools/bow_" + (i + 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r, EntityPlayer ep, ItemStack is1, int t)
	{
		if(t == 0) return itemIcon;
		int i = getMaxItemUseDuration(is1) - t;
		if(i > 17) return pullIcons[2];
		if(i > 13) return pullIcons[1];
		return pullIcons[0];
	}
}