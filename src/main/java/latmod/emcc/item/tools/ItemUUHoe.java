package latmod.emcc.item.tools;
import latmod.core.ODItems;
import latmod.emcc.*;
import latmod.emcc.api.IEmcTool;
import latmod.emcc.item.ItemEMCC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.*;

public class ItemUUHoe extends ItemEMCC implements IEmcTool
{
	@SideOnly(Side.CLIENT)
	public IIcon areaIcon;
	
	public ItemUUHoe(String s)
	{
		super(s);
		setMaxStackSize(1);
		setMaxDamage(EMCC.toolMaterial.getMaxUses());
		
		setFull3D();
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableHoe)
			EMCC.recipes.addRecipe(new ItemStack(this), "UU", " S", " S",
					'U', EMCCItems.UU_ITEM,
					'S', ODItems.STICK);
		
		ItemToolEMCC.addAreaRecipe(new ItemStack(this));
	}
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
	{
		boolean b = false;
		
		b = onItemUse2(is, ep, w, x, y, z, side, x1, y1, z1);
		
		if(ItemToolEMCC.isArea(is))
		{
			for(int ox = -1; ox <= 1; ox++)
			for(int oz = -1; oz <= 1; oz++)
			{
				if((ox == 0 && oz == 0) || is.getItemDamage() == is.getMaxDamage());
				else
				{
					b |= onItemUse2(is, ep, w, x + ox, y, z + oz, side, x1, y1, z1);
				}
			}
		}
		
		return b;
	}
	
	public boolean onItemUse2(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
	{
		if (!ep.canPlayerEdit(x, y, z, side, is) || ep.isSneaking()) return false;
		else
		{
			UseHoeEvent event = new UseHoeEvent(ep, is, w, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event)) return false;

			if (event.getResult() == Result.ALLOW)
			{ is.damageItem(1, ep); return true; }

			Block b = w.getBlock(x, y, z);
			boolean air = w.isAirBlock(x, y + 1, z);

			if (side != 0 && air && (b == Blocks.grass || b == Blocks.dirt))
			{
				Block block = Blocks.farmland;
				w.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1F) / 2F, block.stepSound.getPitch() * 0.8F);

				if (w.isRemote) return true; else
				{
					w.setBlock(x, y, z, block);
					is.damageItem(1, ep);
					return true;
				}
			}
			else return false;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(EMCC.mod.assets + "tools/def/" + itemName);
		areaIcon = ir.registerIcon(EMCC.mod.assets + "tools/area/" + itemName);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack is, int r)
	{ return ItemToolEMCC.isArea(is) ? areaIcon : itemIcon; }
}