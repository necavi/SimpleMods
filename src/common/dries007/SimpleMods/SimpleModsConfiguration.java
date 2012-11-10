package dries007.SimpleMods;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import dries007.SimpleMods.Regions.API;
import dries007.SimpleMods.asm.SimpleModsTransformer;

public class SimpleModsConfiguration {

	public static final String WANDTEXTURE = "/dries007/SimpleMods/Regions/wands.png";
	public static MinecraftServer server;
	public static boolean postModlist;
	public static String postLocation;
	public static boolean spawnOverride;
	public static boolean addCore;
	public static boolean addExtra;
	public static String pingMsg;
	public static String configFileName;

	public static int tpaTimeout;
	public static boolean addRegions;
	public static Item itemWand;
	public static Integer itemWandID;
	
	
	
	
	public static void makeConfig(File configFile) {
		configFileName = configFile.getName();
		final String CATEGORY_CORE = "Core";
		final String CATEGORY_RANK = "Ranks";
		final String CATEGORY_REGIONS = "Regions";
		final String CATEGORY_MESSAGES = "Messages";
		final String CATEGORY_OVERRIDE = "OverrideClasses";
		final String CATEGORY_MODULES = "Modules";
		
		Configuration configuration = new Configuration(configFile);
		System.out.println("Path: " + configFile.getAbsolutePath());
		try
		{
			configuration.load();
			Property prop;
			
			//CORE CONFIG
			prop = configuration.get(CATEGORY_CORE, "postModlist", true);
			prop.comment = "Make a file that conatains all the mods with version and URL.";
			postModlist = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_CORE, "postLocation", "mods.txt");
			prop.comment = "Use / as seperator.";
			postLocation = prop.value;
			
	    	prop = configuration.get(CATEGORY_CORE, "spawnOverride", true);
			prop.comment = "When a player repsawns to the server spawn, override the location to allow 1 block specific spawn zone. Use setspawn to set the spawn, you can specify ranks for different spawn per rank.";
			spawnOverride = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_CORE, "tap-makeup", "%Rc[%Rn]%U");
			prop.comment = "%U = username ; %Hn = Health numaric ; %Rn = rankname ; %Rc = ranks color ; Request MOAR @ ssm.dries007.net";
			VanillaInterface.tapMakeup = prop.value;
			
			//EXTRA COMMANDS
			prop = configuration.get(Configuration.CATEGORY_GENERAL, "TPAtimeout", 20);
			tpaTimeout = prop.getInt();
			
			//RANKS
			prop = configuration.get(CATEGORY_RANK, "defaultRank", "Guest");
			prop.comment = "Default rank";
			Permissions.defaultRank = prop.value;
	    	
			prop = configuration.get(CATEGORY_RANK, "opRank", "Admin");
			prop.comment = "Name of the OP rank";
			Permissions.opRank = prop.value;
			
			//MESSAGES
			prop = configuration.get(CATEGORY_MESSAGES, "wbMessage", "World ends here");
			prop.comment = null;
			WorldBorder.wbMessage = prop.value;
			
			prop = configuration.get(CATEGORY_MESSAGES, "PingMsg", "Pong!");
			prop.comment = "Response to the Ping Command";
			pingMsg = prop.value;
			
			//REGIONS
			prop = configuration.get(CATEGORY_REGIONS, "itemWandId", 900);
			prop.comment = "Id for the Wand";
			itemWandID = prop.getInt();
			
			prop = configuration.get(CATEGORY_REGIONS, "maxChanges", 500000);
			prop.comment = "The maximum amount of blocks that can be edited at once.";
			API.maxChanges = prop.getInt();
			
			prop = configuration.get(CATEGORY_REGIONS, "warningLevel", 100000);
			prop.comment = "If this amount of blocks (or more) is edited, a server wide message will be sent.";
			API.warningLevel = prop.getInt();
			
			prop = configuration.get(CATEGORY_REGIONS, "vertLevel", 128);
			prop.comment = "The hight a selection will be set at using vert or up. Setting this to 256 will make more lagg but makes editing large things easier.";
			API.vertLevel = prop.getInt();
			
			prop = configuration.get(CATEGORY_REGIONS, "bedrockRemoval", false);
			prop.comment = "Set this to true to make selections select layer 0 when using vert or down.";
			API.bedrockRemoval = prop.getBoolean(false);
			
			prop = configuration.get(CATEGORY_REGIONS, "secureTNT", true);
			prop.comment = "Calculate every block destroyed in the blast, laggy. If false, you only calculate the explosion source position.";
			API.secureTNT = prop.getBoolean(true);
			
			//MODULES
			prop = configuration.get(CATEGORY_RANK, "addCore", true);
			prop.comment = "Add Core commands";
			addCore = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_RANK, "addExtra", true);
			prop.comment = "Add Extra commands";
			addExtra = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_RANK, "addRegions", true);
			prop.comment = "Add Regions commands";
			addRegions = prop.getBoolean(true);
			
			//OVERRIDES
			for(String name : SimpleModsTransformer.override.keySet())
			{
				prop = configuration.get(CATEGORY_OVERRIDE, name, true);
				prop.comment = SimpleModsTransformer.override.get(name);
				
				if (prop.getBoolean(true))
				{
					SimpleModsTransformer.override.put(name, prop.comment);
				}
				else
				{
					SimpleModsTransformer.override.remove(name);
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println("SimpleMods has a problem loading it's configuration");
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		finally 
			{
				configuration.save();
			}
		}
}
