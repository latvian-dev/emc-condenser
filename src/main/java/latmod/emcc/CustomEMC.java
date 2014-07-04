package latmod.emcc;
import java.util.*;
import com.google.gson.annotations.*;

public class CustomEMC
{
	@Expose public Map<String, Float> ore_dictionary;
	//@Expose public Map<String, Float> unlocazlied_name;
	
	public CustomEMC()
	{
		ore_dictionary = new HashMap<String, Float>();
		//unlocazlied_name = new HashMap<String, Float>();
	}
}