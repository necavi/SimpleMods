package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.StatCollector;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WrongUsageException;

public class CommandGod extends CommandBase
{
	public CommandGod()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "god";
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
    		if(target.capabilities.disableDamage)
    		{
    			target.capabilities.disableDamage = false;
    		}
    		else
    		{
    			target.capabilities.disableDamage = true;
    		}
    		target.addChatMessage("You've been given The Power!");
    		sender.sendChatToPlayer("You have given " + target.username + " The Power!");
    	}
    	else
    	{
    		if(target.capabilities.disableDamage)
    		{
    			target.capabilities.disableDamage = false;
    		}
    		else
    		{
    			target.capabilities.disableDamage = true;
    		}
    		target.addChatMessage("You've got The Power!");
    	}
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }
    
    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length != 1 ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
}
