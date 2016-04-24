package latmod.emcc.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.emcc.EMCC;
import net.minecraft.block.material.Material;

public abstract class BlockEMCC extends BlockLM
{
	public BlockEMCC(String s, Material m)
	{
		super(s, m);
		setCreativeTab(EMCC.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return EMCC.mod; }
}