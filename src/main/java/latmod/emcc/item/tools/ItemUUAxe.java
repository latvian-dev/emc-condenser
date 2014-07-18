package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class ItemUUAxe extends ItemToolEMCC
{
	public ItemUUAxe(int id, String s)
	{
		super(id, s, ItemAxe.blocksEffectiveAgainst, false, true);
		
		LatCore.addTool(this, EnumToolClass.AXE, EnumToolClass.EMERALD);
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableAxe)
			EMCC.recipes.addRecipe(new ItemStack(this), "UU", "US", " S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), ODItems.STICK);
		
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.blockMaterial, Material.wood, Material.plants, Material.vine, Material.pumpkin); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? ItemToolEMCC.areaSlowness : 1F)) : 1F; }
	
	public boolean onBlockDestroyed(ItemStack is, World w, int bid, int x, int y, int z, EntityLivingBase el)
    {
		if(!isArea(is)) return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		else
		{
			if(!isEffective(Block.blocksList[bid])) return false;
			
			boolean b = false;
			
			if(!w.isRemote)
			{
				int meta = w.getBlockMetadata(x, y, z);
				
				if (Block.blocksList[bid].getBlockHardness(w, x, y, z) != 0F)
					is.damageItem(1, el);
				
				for(int oy = 1; oy <= 10; oy++)
				{
					if(is.getItemDamage() < is.getMaxDamage())
					{
						if(w.getBlockId(x, y + oy, z) == bid && w.getBlockMetadata(x, y + oy, z) == meta && w.getBlockTileEntity(x, y + oy, z) == null)
						{
							if(w.setBlockToAir(x, y + oy, z))
							{
								if (Block.blocksList[bid].getBlockHardness(w, x, y + oy, z) != 0F)
									is.damageItem(1, el);
								
								Block.blocksList[bid].dropBlockAsItem(w, x, y + oy, z, meta, EnchantmentHelper.getFortuneModifier(el));
								
								b = true;
							}
						}
						//else break;
					}
				}
			}
			
			return b;
		}
    }
}