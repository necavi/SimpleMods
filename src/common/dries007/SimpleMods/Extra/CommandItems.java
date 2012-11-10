package dries007.SimpleMods.Extra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandItems extends CommandBase
{
	public CommandItems()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "items";
    }
    
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/items <killall>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = getCommandSenderAsPlayer(sender);
    	World world = player.worldObj;
    	if(args.length>1) throw new WrongUsageException(getCommandUsage(sender));
    	
    	if(args.length==0)
    	{
    		int amount = 0;
    		for (int id = 0; id < world.loadedEntityList.size(); ++id)
    		{
    			Entity entity = (Entity)world.loadedEntityList.get(id);
    			if(entity instanceof EntityItem) ++amount;
    		}
    		sender.sendChatToPlayer("Amount of items in the world:" + amount);
    	}
    	else if(args[0].equalsIgnoreCase("killall"))
    	{
    		int amount = 0;
    		for (int id = 0; id < world.loadedEntityList.size(); ++id)
    		{
    			Entity entity = (Entity)world.loadedEntityList.get(id);
    			if(entity instanceof EntityItem)
    			{
    				int X = entity.chunkCoordX;
                    int Z = entity.chunkCoordZ;
                    if(entity.addedToChunk)
                    {
                    	++amount;
                    	((EntityItem) entity).setDead();
                    }
    			}
    		}
    		sender.sendChatToPlayer("Amount of items removed:" + amount);
    	}
    	else throw new WrongUsageException(getCommandUsage(sender));
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	if(args.length == 1)
    	{
    		return getListOfStringsMatchingLastWord(args, "killall");
    	}
    	return null;
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM."+getCommandName());
    }

}
