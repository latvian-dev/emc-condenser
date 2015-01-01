package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.EMCCConfig;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.item.ItemMaterialsEMCC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class ItemUUSword extends ItemToolEMCC // ItemSword
{
	public ItemUUSword(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfig.Tools.enableSword)
			mod.recipes.addRecipe(new ItemStack(this), "U", "U", "S",
					'U', ItemMaterialsEMCC.INGOT_UUS,
					'S', ODItems.STICK);
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
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.AREA, ToolInfusion.FORTUNE, ToolInfusion.KNOCKBACK, ToolInfusion.FIRE); }
	
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