package latmod.emcc.api;

import ftb.lib.item.LMInvUtils;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EMCCUtils
{
	public static boolean transferEmcToMachine(ItemStack is, IEmcMachine m)
	{
		if(is != null && is.getItem() instanceof IEmcStorageItem)
		{
			double emc = ((IEmcStorageItem)is.getItem()).getStoredEmc(is);
			if(emc == 0D) return false;
			
			double mEmc = m.getStoredEmc();
			double mMaxEmc = m.getEmcCapacity();
			
			double d = Math.min(emc, mMaxEmc - mEmc);
			
			if(d > 0D)
			{
				emc -= d;
				mEmc += d;
				
				m.setStoredEmc(mEmc);
				((IEmcStorageItem)is.getItem()).setStoredEmc(is, emc);
				
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean dropAndSmeltBlock(World w, int x, int y, int z, EntityPlayer ep, Block b, int meta, int fortune)
	{
		boolean flag = false;
		
		ArrayList<ItemStack> is0 = b.getDrops(w, x, y, z, meta, fortune);
		
		if(is0 != null && !is0.isEmpty())
		{
			for(ItemStack is : is0) if(is != null)
			{
				ItemStack is1 = FurnaceRecipes.smelting().getSmeltingResult(LMInvUtils.singleCopy(is));
				
				if(is1 != null)
				{
					if(!w.isRemote) for(int s = 0; s < is.stackSize; s++)
						LMInvUtils.dropItem(w, x + 0.5D, y + 0.5D, z + 0.5D, is1, 10);
					flag = true;
				}
				else if(!w.isRemote) LMInvUtils.dropItem(w, x + 0.5D, y + 0.5D, z + 0.5D, is, 10);
			}
		}
		
		return flag;
	}
	
	public static boolean breakBlockWithBlazingItem(World w, int x, int y, int z, EntityPlayer ep, ItemStack is, IEmcTool ei)
	{
		Block b = w.getBlock(x, y, z);
		if(b == Blocks.air || !ei.isEffective(b)) return false;
		
		int meta = w.getBlockMetadata(x, y, z);
		
		if(EMCCUtils.dropAndSmeltBlock(w, x, y, z, ep, b, meta, EnchantmentHelper.getFortuneModifier(ep)))
		{
			for(int i = 0; i < 20; i++)
			{
				double ox = MathHelperLM.rand.nextFloat();
				double oy = MathHelperLM.rand.nextFloat();
				double oz = MathHelperLM.rand.nextFloat();
				
				w.spawnParticle("flame", x + ox, y + oy, z + oz, 0D, 0D, 0D);
			}
		}
		else
		{
			for(int i = 0; i < 40; i++)
			{
				double ox = MathHelperLM.rand.nextFloat();
				double oy = MathHelperLM.rand.nextFloat();
				double oz = MathHelperLM.rand.nextFloat();
				
				w.spawnParticle("tilecrack_" + Item.getIdFromItem(Item.getItemFromBlock(b)) + "_" + meta, x + ox, y + oy, z + oz, 0D, 0D, 0D);
			}
		}
		
		w.setBlockToAir(x, y, z);
		is.damageItem(2, ep);
		return true;
	}
	
	public static boolean destroyBlockArea(World w, int x, int y, int z, EntityLivingBase el, ItemStack is, Block block, IEmcTool ei)
	{
		boolean b = false;
		
		if(!w.isRemote)
		{
			int meta = w.getBlockMetadata(x, y, z);
			
			if(block.getBlockHardness(w, x, y, z) != 0F)
				is.damageItem(1, el);
			
			int fortune = EnchantmentHelper.getFortuneModifier(el);
			
			for(int ox = -1; ox <= 1; ox++)
			for(int oy = -1; oy <= 1; oy++)
			for(int oz = -1; oz <= 1; oz++)
			{
				if((ox == 0 && oy == 0 && oz == 0) || is.getItemDamage() == is.getMaxDamage());
				else
				{
					if(w.getTileEntity(x + ox, y + oy, z + oz) == null)
					{
						Block block1 = w.getBlock(x + ox, y + oy, z + oz);
						
						if(block1 != Blocks.air && ei.isEffective(block1))
						{
							float h = block1.getBlockHardness(w, x + ox, y + oy, z + oz);
							
							if(h != -1F)
							{
								if(w.setBlockToAir(x + ox, y + oy, z + oz))
								{
									if (h != 0F) is.damageItem(1, el);
									
									block1.dropBlockAsItem(w, x + ox, y + oy, z + oz, meta, fortune);
									
									b = true;
									
									if(is.stackSize == 0) return true;
								}
							}
						}
					}
				}
			}
		}
		
		return b;
	}
}