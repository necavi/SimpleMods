package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandCi extends CommandBase
{
	public CommandCi()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "ci";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/ci <player>";
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
    		int var1;

            for (var1 = 0; var1 < target.inventory.mainInventory.length; ++var1)
            {
                if (target.inventory.mainInventory[var1] != null)
                {
                	target.inventory.player.dropPlayerItemWithRandomChoice(target.inventory.mainInventory[var1], true);
                	target.inventory.mainInventory[var1] = null;
                }
            }

            for (var1 = 0; var1 < target.inventory.armorInventory.length; ++var1)
            {
                if (target.inventory.armorInventory[var1] != null)
                {
                	target.inventory.player.dropPlayerItemWithRandomChoice(target.inventory.armorInventory[var1], true);
                	target.inventory.armorInventory[var1] = null;
                }
            }
    		target.addChatMessage("Your inventory has been cleaned!");
    		sender.sendChatToPlayer("You have cleaned " + target.username + "'s inventory.");
    	}
    	else
    	{
    		int var1;

            for (var1 = 0; var1 < target.inventory.mainInventory.length; ++var1)
            {
                if (target.inventory.mainInventory[var1] != null)
                {
                	target.inventory.player.dropPlayerItemWithRandomChoice(target.inventory.mainInventory[var1], true);
                	target.inventory.mainInventory[var1] = null;
                }
            }

            for (var1 = 0; var1 < target.inventory.armorInventory.length; ++var1)
            {
                if (target.inventory.armorInventory[var1] != null)
                {
                	target.inventory.player.dropPlayerItemWithRandomChoice(target.inventory.armorInventory[var1], true);
                	target.inventory.armorInventory[var1] = null;
                }
            }
    		target.addChatMessage("Your inventory has been cleaned!");
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
