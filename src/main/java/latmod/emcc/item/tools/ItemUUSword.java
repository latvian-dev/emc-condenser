package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
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
		if(EMCC.mod.config().tools.enableSword)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "U", "U", "S",
					'U', EMCCItems.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.mod.config().tools.toolEmcPerDamage; }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && hasInfusion(is, ToolInfusion.FIRE) && ep.isBurning())
		{
			ep.extinguish();
			//is.damageItem(2, ep);
		}
		
		return is;
	}
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		return true;
	}
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.AREA, ToolInfusion.FORTUNE, ToolInfusion.KNOCKBACK, ToolInfusion.FIRE); }
	
	@SuppressWarnings("all")
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 7D, 0));
        return multimap;
    }
	
	public boolean isEffective(Block b)
	{ return b.getMaterial() == Material.web; }
}