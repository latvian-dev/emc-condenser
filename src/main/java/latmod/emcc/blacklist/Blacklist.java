package latmod.emcc.blacklist;
import com.google.gson.annotations.Expose;

public class Blacklist
{
	@Expose public BlacklistEntry all;
	@Expose public BlacklistEntry targets;
	@Expose public BlacklistEntry fuels;
	@Expose public BlacklistEntry example;
	
	public Blacklist()
	{
		all = new BlacklistEntry();
		targets = new BlacklistEntry();
		fuels = new BlacklistEntry();
		example = new BlacklistEntry();
	}
}