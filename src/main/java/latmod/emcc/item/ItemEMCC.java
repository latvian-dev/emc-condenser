package latmod.emcc.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.LMMod;
import ftb.lib.api.item.ItemLM;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;

public class ItemEMCC extends ItemLM
{
	public ItemEMCC(String s)
	{ super(s); }
	
	@Override
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return EMCC.tab; }
}