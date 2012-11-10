package dries007.SimpleMods.Core;

import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.Permissions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandPermissions extends CommandBase
{
    public String getCommandName()
    {
        return "permissions";
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
    	sender.sendChatToPlayer("List of permissions: ");
    	String msg = "";
    	int l = 1;
    	for(String st : Permissions.getPermissions()) {
    		msg = msg + st + ", ";
    		if(l == 3) {
    	    	sender.sendChatToPlayer(msg);
    	    	msg = "";
    	    	l = 1;
    		} else {
    			l++;
    			
    		}
    	}
		sender.sendChatToPlayer(msg);

    }
    
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
    	return true;
    }
}
