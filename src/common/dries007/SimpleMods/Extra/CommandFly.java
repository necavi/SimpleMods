package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandFly extends CommandBase
{
	public CommandFly()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "fly";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName() +" <player>";
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] par2ArrayOfStr)
    {
    	EntityPlayer target = par2ArrayOfStr.length >= 1 ? func_82359_c(sender, par2ArrayOfStr[0]) : getCommandSenderAsPlayer(sender);
    	
    	if (target != sender)
    	{
    		if(target.capabilities.allowFlying)
    		{
    			target.capabilities.allowFlying = false;
    		}
    		else
    		{
    			target.capabilities.allowFlying = true;
    			target.addChatMessage("You've been given wings.");
        		sender.sendChatToPlayer("You have given " + target.username + " wings.");
    		}
    	}
    	else
    	{
    		if(target.capabilities.allowFlying)
    		{
    			target.capabilities.allowFlying = false;
    			target.addChatMessage("Your wings have been taken.");
    		}
    		else
    		{
    			target.capabilities.allowFlying = true;
    			target.addChatMessage("You've got wings.");
    		}
    		
    	}
    	((EntityPlayerMP)target).playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(target.capabilities));
    }

    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM."+getCommandName());
    }
    
    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length != 1 ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
}
