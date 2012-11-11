package dries007.SimpleMods;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
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
import net.minecraft.src.Item;
import net.minecraft.src.ServerCommandManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class SimpleMods 
{


	
	public static void writemodlist(FMLServerStartedEvent event)
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			FileWriter fstream = new FileWriter(SimpleModsConfiguration.postLocation);
			PrintWriter out = new PrintWriter(fstream);
			out.println("# --- ModList ---");
			out.println("# Generated: " + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR) + " (Server time)");
			out.println("# Change the location of this file in " + SimpleModsConfiguration.configFileName);
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
		ICommandManager commandManager = SimpleModsConfiguration.server.getCommandManager();
		ServerCommandManager manager = ((ServerCommandManager) commandManager); 
		
		if(SimpleModsConfiguration.addCore) addCoreCommands(manager);
		if(SimpleModsConfiguration.addExtra) addExtraCommands(manager);
		if(SimpleModsConfiguration.addRegions) 
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
		manager.registerCommand(new CommandOwner());
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
	
	public static void addOverrides() 
	{
		if(ObfuscationReflectionHelper.obfuscation)
		{
			SimpleModsTransformer.addClassOverride("ec", "Needed to display rank on the tap-screen. (Packet201PlayerInfo)");
			SimpleModsTransformer.addClassOverride("aiu", "Needed to protect from fire. (BlockFire)");
			SimpleModsTransformer.addClassOverride("gz", "Needed to protect from players. (NetServerHandler)");
			SimpleModsTransformer.addClassOverride("akp", "Needed to protect from growth. (BlockSapling)");
			SimpleModsTransformer.addClassOverride("wz", "Needed to protect from explosions. (Explosion)");
		}
	}

	public static void tpToDim(EntityPlayer player, int dim)
	{
		SimpleModsConfiguration.server.getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
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
