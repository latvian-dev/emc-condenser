package mods.lm_emc_cond;
import mods.lm_core.LatCore;
import net.minecraft.command.*;

public class AlchemyCommand extends CommandBase
{
	public String getCommandName()
	{ return "lma"; }

	public String getCommandUsage(ICommandSender ics)
	{ return AlchemyFinals.ASSETS + "command.usage"; }

	public void processCommand(ICommandSender ics, String[] s)
	{
		if(!LatCore.canUpdate()) return;
		
		if(s != null && s.length > 0)
		{
			if(s[0].equals("reloadRecipes"))
			{
				AlchemyRecipes.load();
				LatCore.printChat("Elemite Recipes reloaded");
			}
			else LatCore.printChat("Unknown subcommand: " + s[0]);
		}
		else LatCore.printChat("Subcommands: reloadRecipes");
	}
}