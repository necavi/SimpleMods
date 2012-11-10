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

public class CommandGm extends CommandBase
{
	public CommandGm()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "gm";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/gm <gamemode> [player]";
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType var3 = this.getGameModeFromCommand(sender, par2ArrayOfStr[0]);
            EntityPlayer var4 = par2ArrayOfStr.length >= 2 ? func_82359_c(sender, par2ArrayOfStr[0]) : getCommandSenderAsPlayer(sender);
            var4.sendGameTypeToPlayer(var3);
            String var5 = StatCollector.translateToLocal("gameMode." + var3.getName());

            if (var4 != sender)
            {
                notifyAdmins(sender, 1, "commands.gamemode.success.other", new Object[] {var4.getEntityName(), var5});
            }
            else
            {
                notifyAdmins(sender, 1, "commands.gamemode.success.self", new Object[] {var5});
            }
        }
        else
        {
        	EntityPlayer var4 = getCommandSenderAsPlayer(sender);
        	if(var4.capabilities.isCreativeMode)
        	{
        		var4.sendGameTypeToPlayer(EnumGameType.SURVIVAL);
        	}
        	else
        	{
        		var4.sendGameTypeToPlayer(EnumGameType.CREATIVE);
        	}
        }
    }

    /**
     * Gets the Game Mode specified in the command.
     */
    protected EnumGameType getGameModeFromCommand(ICommandSender par1ICommandSender, String par2Str)
    {
        return !par2Str.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !par2Str.equalsIgnoreCase("s") ? (!par2Str.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !par2Str.equalsIgnoreCase("c") ? (!par2Str.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !par2Str.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(parseIntBounded(par1ICommandSender, par2Str, 0, EnumGameType.values().length - 2)) : EnumGameType.ADVENTURE) : EnumGameType.CREATIVE) : EnumGameType.SURVIVAL;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"survival", "creative", "adventure"}): (par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null);
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM."+getCommandName());
    }
}
