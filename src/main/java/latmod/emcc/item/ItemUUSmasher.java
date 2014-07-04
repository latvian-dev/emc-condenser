package latmod.emcc.item;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import latmod.core.EnumToolClass;
import latmod.core.LatCore;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.*;

public class ItemUUSmasher extends ItemToolEMCC // ItemPickaxe
{
	private ItemStack itemPick;
	private ItemStack itemAxe;
	private ItemStack itemShovel;
	
	public ItemUUSmasher(int id, String s)
	{
		super(id, s);
		
		itemPick = new ItemStack(EMCCItems.i_pick);
		itemAxe = new ItemStack(EMCCItems.i_axe);
		itemShovel = new ItemStack(EMCCItems.i_shovel);
		
		LatCore.addTool(this, EnumToolClass.PICKAXE, EnumToolClass.EMERALD);
		LatCore.addTool(this, EnumToolClass.SHOVEL, EnumToolClass.EMERALD);
		LatCore.addTool(this, EnumToolClass.AXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableSmasher)
			EMCC.recipes.addRecipe(new ItemStack(this), "APA", "BVB", " S ",
					Character.valueOf('B'), EMCCItems.UU_BLOCK,
					Character.valueOf('S'), EMCCItems.STICK,
					Character.valueOf('P'), EMCCItems.i_pick,
					Character.valueOf('A'), EMCCItems.i_axe,
					Character.valueOf('V'), EMCCItems.i_shovel);
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return super.getEmcPerDmg(is) * 1.5D; }
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enablePick; }
	
	public boolean canHarvestBlock(Block b)
	{ return true; }
	
	public float getStrVsBlock(ItemStack is, Block block)
	{
		float f = itemPick.getStrVsBlock(block);
		float f1 = itemAxe.getStrVsBlock(block);
		float f2 = itemShovel.getStrVsBlock(block);
		return Math.max(f, Math.max(f1, f2));
	}
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(1, el1); return true; }
	
	@SuppressWarnings("all")
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 6, 0));
		return multimap;
	}
}