package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUSword extends ItemToolEMCC
{
	public ItemUUSword(String s)
	{
		super(s, emptySet);
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
		if(!w.isRemote && hasEnchantment(is, Enchantment.fireAspect) && ep.isBurning())
		{
			ep.extinguish();
			is.damageItem(2, ep);
		}
		
		return is;
	}
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{
		is.damageItem(1, el1);
		return true;
	}
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.AREA, ToolInfusion.FORTUNE, ToolInfusion.KNOCKBACK, ToolInfusion.FIRE); }
}