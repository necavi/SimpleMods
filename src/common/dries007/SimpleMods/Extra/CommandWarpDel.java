package dries007.SimpleMods.Extra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.SimpleMods;
import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandWarpDel extends CommandBase
{
	public CommandWarpDel()
	{
		//Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "warpdel";
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
    	if (warps.hasKey(args[0]))
    	{
    		warps.removeTag(args[0]);
    		sender.sendChatToPlayer("Warp deleted!");
    		return;
//    		What do you try to do here Dries007?
//    		NBTTagCompound newWarps = new NBTTagCompound();
//    		Iterator pws = warps.getTags().iterator();
//    		while (pws.hasNext())
//    		{
//    			NBTTagCompound pw = (NBTTagCompound) pws.next();
//    			if (!pw.getName().equalsIgnoreCase(args[0]))
//    			{
//    				newWarps.setCompoundTag(pw.getName(), pw);
//    			}
//    			else
//    			{
//    				sender.sendChatToPlayer("Warp deleted!");
//    				SimpleMods.worldData.setCompoundTag("warps", warps);
//    				return;
//    			}
//    		}
    	}
    	else
    	{
    		sender.sendChatToPlayer("Warp not found");
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
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP.warp.admin");
    }

}
