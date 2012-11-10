package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandDay extends CommandBase
{
	public CommandDay()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "day";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName();
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
    	for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3)
        {
            MinecraftServer.getServer().worldServers[var3].setWorldTime((long)0);
        }
    	notifyAdmins(par1ICommandSender, "commands.time.set", new Object[] {Integer.valueOf(0)});
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return null;
    }
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }

}
