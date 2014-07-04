package latmod.emcc.item;
import latmod.emcc.*;
import latmod.emcc.api.IEmcTool;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemUUHoe extends ItemEMCC implements IEmcTool
{
	public ItemUUHoe(int id, String s)
	{
		super(id, s);
		setMaxStackSize(1);
		setMaxDamage(256);
		setFull3D();
	}
	
	public void loadRecipes()
	{
		if(EMCC.config.tools.enableHoe)
			EMCC.recipes.addRecipe(new ItemStack(this), "UU", " S", " S",
					Character.valueOf('U'), EMCCItems.UU_ITEM,
					Character.valueOf('S'), EMCCItems.STICK);
	}
	
	public boolean isVisible(ItemStack is)
	{ return EMCC.config.tools.enableHoe; }
	
	public double getEmcPerDmg(ItemStack is)
	{ return EMCC.config.tools.toolEmcPerDamage; }
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int side, float x1, float y1, float z1)
	{
		if (!ep.canPlayerEdit(x, y, z, side, is)) return false;
		else
		{
			UseHoeEvent event = new UseHoeEvent(ep, is, w, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event)) return false;

			if (event.getResult() == Result.ALLOW)
			{ is.damageItem(1, ep); return true; }

			int i1 = w.getBlockId(x, y, z);
			boolean air = w.isAirBlock(x, y + 1, z);

			if (side != 0 && air && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID))
			{
				Block block = Block.tilledField;
				w.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

				if (w.isRemote) return true; else
				{
					w.setBlock(x, y, z, block.blockID);
					is.damageItem(1, ep);
					return true;
				}
			}
			else return false;
		}
	}
}