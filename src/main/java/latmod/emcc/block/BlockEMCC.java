package latmod.emcc.block;

import cpw.mods.fml.relauncher.*;
import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.emcc.EMCC;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockEMCC extends BlockLM
{
	public BlockEMCC(String s, Material m)
	{ super(s, m); }
	
	public LMMod getMod()
	{ return EMCC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return EMCC.tab; }
}