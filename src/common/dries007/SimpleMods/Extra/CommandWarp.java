package dries007.SimpleMods.Extra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.SimpleMods;
import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandWarp extends CommandBase
{
	public CommandWarp()
	{
		Permissions.addPermission("SP."+getCommandName());
		Permissions.addPermission("SP.warp.admin");
	}
	
    public String getCommandName()
    {
        return "warp";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName() + " <name>";
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] args)
    {
    	if (args.length!=1) throw new WrongUsageException("/" + getCommandName() + " <name>");
    	NBTTagCompound warps = Permissions.worldData.getCompoundTag("warps");
    	EntityPlayer player = getCommandSenderAsPlayer(sender);
    	if (warps.hasKey(args[0]))
    	{
    		PotionEffect effect = new PotionEffect(9, 120 , 4);
    		player.addPotionEffect(effect);
    		effect = new PotionEffect(15, 30 , 4);
    		player.addPotionEffect(effect);
    		NBTTagCompound warp = warps.getCompoundTag(args[0]);
    		if (warp.getInteger("dim")!=player.dimension)  	SimpleMods.tpToDim(player, warp.getInteger("dim"));
    		Double X = warp.getDouble("X");
    		Double Y = warp.getDouble("Y");
    		Double Z = warp.getDouble("Z");
    		Float pitch = warp.getFloat("rotP");
    		Float yaw = warp.getFloat("rotY");;
    		((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
    	}
    	else
    	{
    		sender.sendChatToPlayer("Warp not found!");
    	}
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	NBTTagCompound warps = Permissions.worldData.getCompoundTag("warps");
    	Iterator warpsIt = warps.getTags().iterator();
    	List<String> list = new ArrayList<String>();
    	while(warpsIt.hasNext()) {
    		NBTTagCompound buffer = (NBTTagCompound) warpsIt.next();
    		list.add(buffer.getName());
    	}
        return  list;
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP.warp");
    }
}
