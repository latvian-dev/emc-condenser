package latmod.emcc.item.tools;
import cpw.mods.fml.common.eventhandler.Event.Result;
import latmod.emcc.api.ToolInfusion;
import latmod.emcc.config.EMCCConfigTools;
import latmod.emcc.item.ItemMaterialsEMCC;
import latmod.ftbu.inv.ODItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemUUHoe extends ItemToolEMCC
{
	public ItemUUHoe(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		if(EMCCConfigTools.enableTools.get())
			mod.recipes.addRecipe(new ItemStack(this), "UU", " S", " S",
					'U', ItemMaterialsEMCC.INGOT_UUS,
					'S', ODItems.STICK);
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
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
	
	public boolean canEnchantWith(ItemStack is, ToolInfusion t)
	{ return t.is(ToolInfusion.UNBREAKING); }
	
	public boolean isEffective(Block b)
	{ return false; }
}