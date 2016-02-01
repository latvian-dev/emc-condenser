package latmod.emcc.item;

import cpw.mods.fml.relauncher.*;
import ftb.lib.LMMod;
import ftb.lib.api.item.ItemLM;
import latmod.emcc.EMCC;
import net.minecraft.creativetab.CreativeTabs;

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