package latmod.emcc.item;
import net.minecraft.creativetab.*;
import cpw.mods.fml.relauncher.*;
import latmod.core.base.*;
import latmod.emcc.EMCC;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(String s)
	{ super(EMCC.mod, s); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}