package latmod.emcc.item.tools;
import java.util.Set;

import latmod.core.*;
import latmod.emcc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Sets;

public class ItemUUAxe extends ItemToolEMCC
{
	public static final Set<Block> effective = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});
	
	public ItemUUAxe(String s)
	{
		super(s, effective);
		
		setHarvestLevel(EnumToolClass.AXE.toolClass, EnumToolClass.ALUMITE);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableAxe)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "UU", "US", " S",
					'U', EMCCItems.UU_ITEM,
					'S', ODItems.STICK);
	}
	
	public boolean isEffective(Block b)
	{ return super.isEffective(b) || isEffectiveAgainst(b.getMaterial(), Material.wood, Material.plants, Material.vine, Material.gourd); }
	
	public float getStrVsBlock(ItemStack is, Block b)
	{ return super.getStrVsBlock(is, b); }//{ return isEffective(b) ? (efficiencyOnProperMaterial / (isArea(is) ? 4F : 1F)) : 1F; }
	
	public boolean onBlockDestroyed(ItemStack is, World w, Block bid, int x, int y, int z, EntityLivingBase el)
    {
		return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		/*if(!isArea(is)) return super.onBlockDestroyed(is, w, bid, x, y, z, el);
		else
		{
			if(!isEffective(bid)) return false;
			
			boolean b = false;
			
			if(!w.isRemote)
			{
				int meta = w.getBlockMetadata(x, y, z);
				
				if (bid.getBlockHardness(w, x, y, z) != 0F)
					is.damageItem(1, el);
				
				for(int oy = 1; oy <= 10; oy++)
				{
					if(is.getItemDamage() < is.getMaxDamage())
					{
						if(w.getBlock(x, y + oy, z) == bid && w.getBlockMetadata(x, y + oy, z) == meta && w.getTileEntity(x, y + oy, z) == null)
						{
							if(w.setBlockToAir(x, y + oy, z))
							{
								if (bid.getBlockHardness(w, x, y + oy, z) != 0F)
									is.damageItem(1, el);
								
								bid.dropBlockAsItem(w, x, y + oy, z, meta, EnchantmentHelper.getFortuneModifier(el));
								
								b = true;
							}
						}
						else break;
					}
				}
			}
			
			return b;
		}
		*/
    }
}