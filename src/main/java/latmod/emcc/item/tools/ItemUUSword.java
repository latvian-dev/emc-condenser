package latmod.emcc.item.tools;

import com.google.common.collect.Multimap;
import ftb.lib.item.ODItems;
import latmod.emcc.api.*;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.api.item.ICreativeSafeItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSword extends ItemToolEMCC implements ICreativeSafeItem // ItemSword
{
	public ItemUUSword(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfigTools.Enable.sword.get())
			getMod().recipes.addRecipe(new ItemStack(this), "U", "U", "S", 'U', ItemMaterialsEMCC.INGOT_UUS, 'S', ODItems.STICK);
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && hasInfusion(is, ToolInfusion.FIRE) && ep.isBurning())
		{
			ep.extinguish();
		}
		
		return is;
	}
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		damageItem(is, false);
		return true;
	}
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.FORTUNE, ToolInfusion.KNOCKBACK, ToolInfusion.FIRE); }
	
	public EnumToolType getToolType(ItemStack is)
	{ return EnumToolType.SWORD; }
	
	@SuppressWarnings("all")
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap multimap = super.getAttributeModifiers(is);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", damageItem(is, true) ? 9D : 1D, 0));
		return multimap;
	}
	
	public boolean isEffective(Block b)
	{ return b.getMaterial() == Material.web; }
}