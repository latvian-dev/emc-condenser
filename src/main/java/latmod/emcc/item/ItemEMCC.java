package latmod.emcc.item;
import cpw.mods.fml.relauncher.*;
import latmod.core.base.ItemLM;
import latmod.emcc.*;
import net.minecraft.creativetab.*;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(int id, String s)
	{ super(EMCC.mod, id, s); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}