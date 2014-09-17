package latmod.emcc.item.tools;
import latmod.core.*;
import latmod.core.util.FastList;
import latmod.emcc.*;
import latmod.emcc.api.ToolInfusion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUUAxe extends ItemToolEMCC // ItemAxe
{
	public static final FastList<Block> effectiveBlocks = new FastList<Block>(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.lit_pumpkin });
	public static final FastList<Material> effectiveMaterials = new FastList<Material>(new Material[] { Material.wood, Material.plants, Material.vine, Material.gourd, Material.leaves });
	
	public ItemUUAxe(String s)
	{
		super(s);
		
		setHarvestLevel(EnumToolClass.AXE.toolClass, EnumToolClass.LEVEL_DIAMOND);
	}
	
	public void loadRecipes()
	{
		if(EMCC.mod.config().tools.enableAxe)
			EMCC.mod.recipes().addRecipe(new ItemStack(this), "UU", "US", " S",
					'U', EMCCItems.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean isEffective(Block b)
	{ return isEffective(b, effectiveBlocks, effectiveMaterials); }
	
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
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.SHARPNESS, ToolInfusion.UNBREAKING, ToolInfusion.AREA); }
}