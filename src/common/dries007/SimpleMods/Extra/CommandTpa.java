package dries007.SimpleMods.Extra;

import java.util.List;
import java.util.TimerTask;

import dries007.SimpleMods.SimpleMods;
import dries007.SimpleMods.TickHandler;
import dries007.SimpleMods.*;

import java.util.Timer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandTpa extends CommandBase
{
	public CommandTpa()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "tpa";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName() +" <player|a|d>";
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] args)
    {
    	if(args.length != 1) throw new WrongUsageException(getCommandUsage(sender));
    	if(args[0].equalsIgnoreCase("a"))
    	{
    		EntityPlayer player = getCommandSenderAsPlayer(sender);
    		EntityPlayer source = func_82359_c(sender, player.getEntityData().getString("TPA"));
    		sender.sendChatToPlayer("TPa accepted.");
    		source.sendChatToPlayer("TPa accepted.");
    		player.getEntityData().setString("TPA", ".");
    		((EntityPlayerMP)source).playerNetServerHandler.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
    	}
    	else if(args[0].equalsIgnoreCase("d"))
    	{
    		EntityPlayer player = getCommandSenderAsPlayer(sender);
    		EntityPlayer source = func_82359_c(sender, player.getEntityData().getString("TPA"));
    		sender.sendChatToPlayer("TPa denied.");
    		source.sendChatToPlayer("TPa denied.");
    		player.getEntityData().setString("TPA", ".");
    	}
    	else
    	{
    		EntityPlayer target = args.length >= 1 ? func_82359_c(sender, args[0]) : getCommandSenderAsPlayer(sender);
    		target.sendChatToPlayer(sender.getCommandSenderName() + " wants to TP to you. Use '/tpa a' to allow or '/tpa d' to deny.");
    		target.getEntityData().setString("TPA", sender.getCommandSenderName());
    		target.getEntityData().setInteger("TPAtime", SimpleMods.TPAtimeout);
    		TickHandler.TPA.add(target);
    	}
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
