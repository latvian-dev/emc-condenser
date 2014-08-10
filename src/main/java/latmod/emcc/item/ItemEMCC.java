package latmod.emcc.item;
import latmod.core.base.ItemLM;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(String s)
	{ super(EMCC.mod, s); }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}