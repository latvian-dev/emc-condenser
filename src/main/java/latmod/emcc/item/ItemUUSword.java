package latmod.emcc.item;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.*;
import net.minecraft.world.World;
import latmod.emcc.*;

public class ItemUUSword extends ItemToolEMCC // ItemSword
{
	public ItemUUSword(int id, String s)
	{
		super(id, s);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableSword)
			EMCC.recipes.addRecipe(new ItemStack(this), "U", "U", "S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enableSword; }
	
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		if (par2Block.blockID == Block.web.blockID) return 15F; else
		{
			Material material = par2Block.blockMaterial;
			return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.pumpkin ? 1F : 1.5F;
		}
	}

	public boolean hitEntity(ItemStack is, EntityLivingBase el, EntityLivingBase el1)
	{ is.damageItem(1, el1); return true; }

	public boolean onBlockDestroyed(ItemStack is, World w, int bid, int x, int y, int z, EntityLivingBase el)
	{ if(Block.blocksList[bid].getBlockHardness(w, x, y, z) != 0F) is.damageItem(2, el); return true; }
	
	public boolean canHarvestBlock(Block par1Block)
	{ return par1Block.blockID == Block.web.blockID; }
	
	@SuppressWarnings("all")
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 7, 0));
		return multimap;
	}
}