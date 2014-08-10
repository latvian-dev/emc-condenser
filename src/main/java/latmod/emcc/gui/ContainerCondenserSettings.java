package latmod.emcc.gui;
import latmod.core.base.gui.ContainerLM;
import latmod.emcc.EMCC;
import latmod.emcc.tile.TileCondenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ContainerCondenserSettings extends ContainerLM
{
	public ContainerCondenserSettings(EntityPlayer ep, TileCondenser t)
	{
		super(ep, t);
		
		for(int y = 0; y < 3; y++) for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x + y * 9 + 9, 8 + x * 18, 123 + y * 18));
		
		for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x, 8 + x * 18, 181));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ep)
	{ return true; }
	
	public ItemStack transferStackInSlot(EntityPlayer ep, int i)
	{ return null; }
	
	public ResourceLocation getTexture()
	{ return EMCC.mod.getLocation("textures/gui/condenserSettings.png"); }
}