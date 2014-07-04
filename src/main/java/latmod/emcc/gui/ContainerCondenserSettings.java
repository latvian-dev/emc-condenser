package latmod.emcc.gui;
import latmod.core.base.ContainerLM;
import latmod.emcc.EMCC;
import latmod.emcc.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ContainerCondenserSettings extends ContainerLM
{
	public ContainerCondenserSettings(EntityPlayer ep, TileCondenser t)
	{
		super(ep, t);
		
		addSlotToContainer(new SlotRestrictedTags(t.restrictedItems, 0, 12, 125));
		
		for(int i = 1; i <= 5; i++)
			addSlotToContainer(new SlotRestricted(t.restrictedItems, i, 26 + 18 * i, 125));
		
		addSlotToContainer(new SlotRestrictedTags(t.restrictedItems, 6, 148, 125));
		
		for(int y = 0; y < 3; y++) for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x + y * 9 + 9, 8 + x * 18, 158 + y * 18));
		
		for(int x = 0; x < 9; x++)
		addSlotToContainer(new Slot(ep.inventory, x, 8 + x * 18, 216));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer ep)
	{ return true; }
	
	public ItemStack transferStackInSlot(EntityPlayer ep, int i)
	{ return null; }
	
	public ResourceLocation getTexture()
	{ return EMCC.mod.getLocation("textures/gui/condenserSettings.png"); }
}