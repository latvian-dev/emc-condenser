package latmod.emcc.item;
import latmod.core.LMMod;
import latmod.core.item.ItemLM;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(String s)
	{ super(s); }
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}