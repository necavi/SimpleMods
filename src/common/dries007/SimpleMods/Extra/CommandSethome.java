package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.SimpleMods;
import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandSethome extends CommandBase
{
	public CommandSethome()
	{
		//Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "sethome";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName();
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayer player = getCommandSenderAsPlayer(sender);
    	NBTTagCompound home = new NBTTagCompound();
    	home.setInteger("dim", player.dimension);
    	home.setDouble("X", player.posX);
    	home.setDouble("Y", player.posY);
    	home.setDouble("Z", player.posZ);
    	home.setFloat("rotP", player.rotationPitch);
    	home.setFloat("rotY", player.rotationYaw);
    	NBTTagCompound settings = Permissions.getPlayerSetting(player);
    	settings.setCompoundTag("home", home);
    	Permissions.setPlayerSetting(player, settings);
    	sender.sendChatToPlayer("Home set!");
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	return null; 
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM.home");
    }
}
