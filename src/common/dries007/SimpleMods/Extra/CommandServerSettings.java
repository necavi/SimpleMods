package dries007.SimpleMods.Extra;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;

import dries007.SimpleMods.SimpleMods;
import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandServerSettings extends CommandBase
{
	public CommandServerSettings()
	{
		Permissions.addPermission("SM."+getCommandName());
		Permissions.addPermission("SM.ss");
	}
	
    public String getCommandName()
    {
        return "serversettings";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName() + " [name] [state]";
    }

    public List getCommandAliases()
    {
    	return Arrays.asList(new String[] {"ss"});
    }
    
    List<String> options = Arrays.asList(new String[] {"pvp", "buildlimit", "online", "animals", "npcs", "flight", "motd", "gamemode", "gm", "monsters"});
    
    public void processCommand(ICommandSender sender, String[] args)
    {
    	MinecraftServer server = SimpleMods.server;
    	if(args.length==0)
    	{
    		sender.sendChatToPlayer("List of available settings:");
    		sender.sendChatToPlayer("pvp, buildlimit, online, animals, npcs, flight, motd, gamemode, gm, monsters");
    	}
    	else if(args.length==1)
    	{
    		get(sender, args[0]);
    	}
    	else if(args.length==2)
    	{
    		set(sender, args[0], args[1]);
    	}
    	else if(args[0].equalsIgnoreCase("motd"))
    	{
    		String msg = args[1];
    		for(int i = 2; i < args.length; i++)
    		{
    			msg = msg + " " + args[i];
    		}
			server.setMOTD(msg);
			sender.sendChatToPlayer("MOTD is set to:'" + server.getMOTD() + "'");
		}
    	else throw new WrongUsageException(getCommandUsage(sender));
    	if (server.isDedicatedServer())
    	{
    		((DedicatedServer) server).saveProperties();
    	}
    }
    
    public void get(ICommandSender sender, String name)
    {
    	MinecraftServer server = SimpleMods.server;
    	if(name.equalsIgnoreCase("pvp"))
		{
			sender.sendChatToPlayer("PVP is now:" + server.isPVPEnabled());
		}
		else if(name.equalsIgnoreCase("buildlimit"))
		{
			sender.sendChatToPlayer("Buildlimit is now:" + server.getBuildLimit());
		}
		else if(name.equalsIgnoreCase("online"))
		{
			sender.sendChatToPlayer("Online is now:" + server.isServerInOnlineMode());
		}
		else if(name.equalsIgnoreCase("animals"))
		{
			sender.sendChatToPlayer("Animals are now:" + server.getCanSpawnAnimals());
		}
		else if(name.equalsIgnoreCase("monsters"))
		{
			for (int var2 = 0; var2 < server.worldServers.length; ++var2)
	        {
				Object value = ObfuscationReflectionHelper.getPrivateValue(World.class, server.worldServers[var2], "spawnHostileMobs");
	            sender.sendChatToPlayer("For dimension " + var2 + ": " + value.toString());
	        }
			
		}
		else if(name.equalsIgnoreCase("npcs"))
		{
			sender.sendChatToPlayer("NPCs are now:" + server.getCanSpawnNPCs());
		}
		else if(name.equalsIgnoreCase("flight"))
		{
			sender.sendChatToPlayer("Flight is now:" + server.isFlightAllowed());
		}
		else if(name.equalsIgnoreCase("motd"))
		{
			sender.sendChatToPlayer("MOTD is now:'" + server.getMOTD() + "'");
		}
		else if(name.equalsIgnoreCase("gamemode") || name.equalsIgnoreCase("gm"))
		{
			for (int var2 = 0; var2 < server.worldServers.length; ++var2)
	        {
	            sender.sendChatToPlayer("For dimension " + var2 + ": " + server.worldServers[var2].getWorldInfo().getGameType().name());
	        }	
		}
		else
		{
			sender.sendChatToPlayer(MCColor.RED + name + " is not a recognized setting.");
		}
    }
    
    public void set(ICommandSender sender, String name, String value)
    {
    	MinecraftServer server = SimpleMods.server;
    	if(name.equalsIgnoreCase("pvp"))
		{
    		try
    		{
    			boolean bl = Boolean.parseBoolean(value);
    			server.setAllowPvp(bl);
    			sender.sendChatToPlayer("PVP set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}
		}
		else if(name.equalsIgnoreCase("buildlimit"))
		{
			int i = this.parseIntBounded(sender, value, 0, server.worldServers[0].getHeight());
			sender.sendChatToPlayer("Buildlimit set to:" + i);
		}
		else if(name.equalsIgnoreCase("online"))
		{
			try
    		{
    			boolean bl = Boolean.parseBoolean(value);
    			server.setOnlineMode(bl);
    			sender.sendChatToPlayer("Online set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}
		}
		else if(name.equalsIgnoreCase("animals"))
		{
			try
    		{
    			boolean bl = Boolean.parseBoolean(value);
    			server.setCanSpawnAnimals(bl);
    			sender.sendChatToPlayer("Animals set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}
		}
		else if(name.equalsIgnoreCase("monsters"))
		{
			try
    		{
				boolean bl = Boolean.parseBoolean(value);
				for (int var2 = 0; var2 < server.worldServers.length; ++var2)
		        {
					ObfuscationReflectionHelper.setPrivateValue(World.class, server.worldServers[var2], bl, "spawnHostileMobs");
		        }
    			sender.sendChatToPlayer("Monsters set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}			
		}
		else if(name.equalsIgnoreCase("npcs"))
		{
			try
    		{
    			boolean bl = Boolean.parseBoolean(value);
    			server.setCanSpawnNPCs(bl);
    			sender.sendChatToPlayer("NPCs set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}
		}
		else if(name.equalsIgnoreCase("flight"))
		{
			try
    		{
    			boolean bl = Boolean.parseBoolean(value);
    			server.setAllowFlight(bl);
    			sender.sendChatToPlayer("Flight set to :" + bl);
    		}
    		catch(Exception e)
    		{
    			sender.sendChatToPlayer(MCColor.RED + value + "is not true or false!");
    		}
		}
		else if(name.equalsIgnoreCase("motd"))
		{
			server.setMOTD(value);
			sender.sendChatToPlayer("MOTD is set to:'" + server.getMOTD() + "'");
		}
		else if(name.equalsIgnoreCase("gamemode") || name.equalsIgnoreCase("gm"))
		{
			int i = this.parseIntBounded(sender, value, 0, EnumGameType.values().length);
			EnumGameType gm = EnumGameType.getByID(i);
			server.setGameType(gm);
			sender.sendChatToPlayer("Global gamemode set to:"+ gm.getName());
		}
		else
		{
			sender.sendChatToPlayer(MCColor.RED + name + " is not a recognized setting.");
		}
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	if(args.length==1)
    	{
    		return getListOfStringsFromIterableMatchingLastWord(args, options);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM.serversettings") || Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM.ss") ;
    }
}
