package dries007.SimpleMods.Core;

import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.Permissions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandRanks extends CommandBase
{
    public String getCommandName()
    {
        return "ranks";
    }

    public String getCommandUsage(ICommandSender sender)
    {
    	return "/" + getCommandName();
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] args)
    {
    	Iterator ranks = Permissions.rankData.getTags().iterator();
    	String[] rankNames = new String[Permissions.rankData.getTags().size()-1];
    	String rankNames1 = new String();
    	int i = 0;
    	while (ranks.hasNext())
    	{
    		NBTTagCompound rank = (NBTTagCompound) ranks.next();
    		rankNames[i]=rank.getName().trim().toLowerCase();
    		rankNames1 = rankNames1 + rank.getName().trim().toLowerCase() + ","; 
    	}
    	sender.sendChatToPlayer("List of ranks: " + rankNames1.substring(0, rankNames1.length()-1) + ".");
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
    	return true;
    }
}
