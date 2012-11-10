package dries007.SimpleMods;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLServerStartedEvent;

import dries007.SimpleMods.Core.*;
import dries007.SimpleMods.Extra.*;
import dries007.SimpleMods.Regions.API;
import dries007.SimpleMods.Regions.Commands.*;

import dries007.SimpleMods.asm.SimpleModsTransformer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.ServerCommandManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class SimpleMods 
{
	public static MinecraftServer server;
	public static File configFile;
	public static boolean postModlist;
	public static String postLocation;
	public static boolean spawnOverride;
	public static boolean addCore;
	public static boolean addExtra;
	public static String PingMsg;
	public static int TPAtimeout;
	private static boolean addRegions;

	public static void makeConfig(File configFile)
	{
		SimpleMods.configFile = configFile;
		
		final String CATEGORY_CORE = "Core";
		final String CATEGORY_RANK = "Ranks";
		final String CATEGORY_MESSAGES = "Messages";
		final String CATEGORY_OVERRIDE = "OverrideClasses";
		final String CATEGORY_MODULES = "Modules";
		
		Configuration configuration = new Configuration(configFile);
		try
		{
			configuration.load();
			Property prop;
			
			//CORE CONFIG
			prop = configuration.get(CATEGORY_CORE, "postModlist", true);
			prop.comment = "Make a file that conatains all the mods with version and URL.";
			SimpleMods.postModlist = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_CORE, "postLocation", "mods.txt");
			prop.comment = "Use / as seperator.";
			SimpleMods.postLocation = prop.value;
			
	    	prop = configuration.get(CATEGORY_CORE, "spawnOverride", true);
			prop.comment = "When a player repsawns to the server spawn, override the location to allow 1 block specific spawn zone. Use setspawn to set the spawn, you can specify ranks for different spawn per rank.";
			SimpleMods.spawnOverride = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_CORE, "tap-makeup", "%Rc[%Rn]%U");
			prop.comment = "%U = username ; %Hn = Health numaric ; %Rn = rankname ; %Rc = ranks color ; Request MOAR @ ssm.dries007.net";
			VanillaInterface.tapMakeup = prop.value;
			
			//EXTRA COMMANDS
			prop = configuration.get(Configuration.CATEGORY_GENERAL, "TPAtimeout", 20);
			SimpleMods.TPAtimeout = prop.getInt();
			
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
			SimpleMods.PingMsg = prop.value;
			
			//MODULES
			prop = configuration.get(CATEGORY_RANK, "addCore", true);
			prop.comment = "Add Core commands";
			SimpleMods.addCore = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_RANK, "addExtra", true);
			prop.comment = "Add Extra commands";
			SimpleMods.addExtra = prop.getBoolean(true);
			
			prop = configuration.get(CATEGORY_RANK, "addRegions", true);
			prop.comment = "Add Regions commands";
			SimpleMods.addRegions = prop.getBoolean(true);
			
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
		}
		finally 
		{
			configuration.save();
		}
	}
	
	public static void writemodlist(FMLServerStartedEvent event)
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			FileWriter fstream = new FileWriter(postLocation);
			PrintWriter out = new PrintWriter(fstream);
			out.println("# --- ModList ---");
			out.println("# Generated: " + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR) + " (Server time)");
			out.println("# Change the lacation of this file in " + configFile.getName());
			out.println();
			
			for(ModContainer mod : Loader.instance().getModList())
			{
				String url = "";
				if(!mod.getMetadata().url.isEmpty()) url = mod.getMetadata().url;
				if(!mod.getMetadata().updateUrl.isEmpty()) url = mod.getMetadata().updateUrl;
				out.println(mod.getName() + " Version:" + mod.getVersion() + " URL:" + url);
			}
				
			out.close();
		}
		catch (Exception e)
		{	
			FMLLog.severe("Error writing to modlist");
			FMLLog.severe(e.getLocalizedMessage());
		}
	}

	public static void addCommands()
	{
		ICommandManager commandManager = server.getCommandManager();
		ServerCommandManager manager = ((ServerCommandManager) commandManager); 
		
		if(addCore) addCoreCommands(manager);
		if(addExtra) addExtraCommands(manager);
		if(addRegions) 
		{
			addRegionCommands(manager);
			addRegionOtherStuf();
		}
	}

	private static void addRegionOtherStuf() 
	{
		API.addFlag("nofirespread", "This flag turns off firespread in this region. Mod needs to be a code mod for this!");		//1
		API.addFlag("nogrowth", "This flag turns off treegrowth in this region. Mod needs to be a code mod for this!");			//2
		API.addFlag("noexplosions", "This flag turns off explosions in this region. Mod needs to be a code mod for this!");		//3
		API.addFlag("noplayerblock", "This flag makes placing and removing blocks impossible, exept for the members.");			//4
		API.addFlag("noplayeritem", "This flag makes use of items impossible (food too!), exept for the members.");				//5
		API.addFlag("godmode", "This flag makes all players invincible in this region. Overrules pvp.");						//6
		API.addFlag("nopvp", "This flag makes pvp impossible in this region.");													//7
		API.addFlag("nofalldamage", ";-)");																						//8
		API.addFlag("nochest", "This tag makes opening chest impossible");
	}

	private static void addRegionCommands(ServerCommandManager manager) 
	{
		manager.registerCommand(new CommandSet());
		manager.registerCommand(new CommandRegion());
		manager.registerCommand(new CommandMembers());
		manager.registerCommand(new CommandRegen());
		manager.registerCommand(new CommandExpand());
		manager.registerCommand(new CommandSphere());
		manager.registerCommand(new CommandReplace());
		manager.registerCommand(new CommandExtinguish());
		manager.registerCommand(new CommandFix());
		manager.registerCommand(new CommandFlags());
		manager.registerCommand(new CommandDrain());
		manager.registerCommand(new CommandTest());		//DEBUG
	}

	private static void addExtraCommands(ServerCommandManager manager) 
	{
		manager.registerCommand(new CommandPing());		
		manager.registerCommand(new CommandGm()); 		
		manager.registerCommand(new CommandDay());		
		manager.registerCommand(new CommandNight());	
		manager.registerCommand(new CommandRain());		
		manager.registerCommand(new CommandTellops());	
		manager.registerCommand(new CommandMilk());		
		manager.registerCommand(new CommandHeal());		
		manager.registerCommand(new CommandFly());		
		manager.registerCommand(new CommandGod());		
		manager.registerCommand(new CommandCi());		
		manager.registerCommand(new CommandBuff());		
		manager.registerCommand(new CommandBuffp());	
		manager.registerCommand(new CommandHome());		
		manager.registerCommand(new CommandSethome());	
		manager.registerCommand(new CommandCraft());	
		manager.registerCommand(new CommandChest());	
		manager.registerCommand(new CommandSpawn());
		manager.registerCommand(new CommandLag());
		manager.registerCommand(new CommandItems());
		manager.registerCommand(new CommandMobs());
		manager.registerCommand(new CommandKit());
		manager.registerCommand(new CommandServerSettings());
		//PW
		manager.registerCommand(new CommandPw()); 		
		manager.registerCommand(new CommandPwSet());	
		manager.registerCommand(new CommandPwDel());	
		//Warp
		manager.registerCommand(new CommandWarp());		
		manager.registerCommand(new CommandWarpSet());	
		manager.registerCommand(new CommandWarpDel());	
		//TP
		manager.registerCommand(new CommandTp());		
		manager.registerCommand(new CommandTpa());		
		//
		manager.registerCommand(new CommandTest());		//DEBUG
	}

	public static void addCoreCommands(ServerCommandManager manager) 
	{
		manager.registerCommand(new CommandPermissions());
		manager.registerCommand(new CommandPromote());
		manager.registerCommand(new CommandAddrank());
		manager.registerCommand(new CommandRanks());
		manager.registerCommand(new CommandPlayer());
		manager.registerCommand(new CommandRank());
		manager.registerCommand(new CommandSetSpawn());
		manager.registerCommand(new CommandWorldborder());
	}

	public static void tpToDim(EntityPlayer player, int dim)
	{
		server.getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
	}
	
	public static float rot(float par0)
	{
		par0 %= 360.0F;
		if (par0<0)
		{
			par0 +=360.0F;
		}	
		return par0;
	}	
}
