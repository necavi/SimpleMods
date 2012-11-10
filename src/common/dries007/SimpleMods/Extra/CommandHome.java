package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandHome extends CommandBase
{
	public CommandHome()
	{
		Permissions.addPermission("SM.home");
	}
	
    public String getCommandName()
    {
        return "home";
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
    	if(Permissions.getPlayerSetting(player).hasKey("home"))
    	{
    		PotionEffect effect = new PotionEffect(9, 120 , 4);
    		player.addPotionEffect(effect);
    		effect = new PotionEffect(15, 30 , 4);
    		player.addPotionEffect(effect);
    		NBTTagCompound home = Permissions.getPlayerSetting(player).getCompoundTag("home");
    		if (home.getInteger("dim")!=player.dimension) SimpleMods.tpToDim(player, home.getInteger("dim"));
    		Double X = home.getDouble("X");
    		Double Y = home.getDouble("Y");
    		Double Z = home.getDouble("Z");
    		Float pitch = home.getFloat("rotP");
    		Float yaw = home.getFloat("rotY");;
    		((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
    		sender.sendChatToPlayer("Poof!");
    	}
    	else
    	{
    		sender.sendChatToPlayer("No home set!");
    	}
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	NBTTagCompound data = Permissions.getPlayerSetting(sender.getCommandSenderName()).getCompoundTag("PW");
        return (List) data.getTags();
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM.home");
    }
}
