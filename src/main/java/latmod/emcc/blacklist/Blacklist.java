package latmod.emcc.blacklist;

public class Blacklist
{
	public BlacklistEntry all;
	public BlacklistEntry targets;
	public BlacklistEntry fuels;
	public BlacklistEntry example;
	
	public Blacklist()
	{
		all = new BlacklistEntry();
		targets = new BlacklistEntry();
		fuels = new BlacklistEntry();
		example = new BlacklistEntry();
	}
}