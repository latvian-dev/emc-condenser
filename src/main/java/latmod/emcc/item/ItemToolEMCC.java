package latmod.emcc.item;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import latmod.emcc.EMCC;
import latmod.emcc.api.*;

public class ItemToolEMCC extends ItemEMCC implements IEmcTool
{
	public ItemToolEMCC(int id, String s)
	{
		super(id, s);
		setMaxStackSize(1);
		setMaxDamage(256);
		setFull3D();
	}

	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public int getItemEnchantability()
	{ return 25; }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{ return false; }
	
	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(2, el1); return true; }

	public boolean onBlockDestroyed(ItemStack is, World w, int bid, int x, int y, int z, EntityLivingBase el)
	{ if(Block.blocksList[bid].getBlockHardness(w, x, y, z) != 0F) is.damageItem(1, el); return true; }
	
	@SuppressWarnings("all")
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 4, 0));
		return multimap;
	}
}