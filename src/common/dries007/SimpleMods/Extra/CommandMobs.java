package dries007.SimpleMods.Extra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandMobs extends CommandBase
{
	public CommandMobs()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "mobs";
    }
    
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/mobs <killall>";
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
    			if(entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) ++amount;
    		}
    		sender.sendChatToPlayer("Amount of mobs in the world:" + amount);
    	}
    	else if(args[0].equalsIgnoreCase("killall"))
    	{
    		int amount = 0;
    		for (int id = 0; id < world.loadedEntityList.size(); ++id)
    		{
    			Entity entity = (Entity)world.loadedEntityList.get(id);
    			if(entity instanceof EntityLiving && !(entity instanceof EntityPlayer))
    			{
    				int X = entity.chunkCoordX;
                    int Z = entity.chunkCoordZ;
                    if(entity.addedToChunk)
                    {
                    	++amount;
                    	((EntityLiving) entity).heal(-9000);
                    	((EntityLiving) entity).setDead();
                    }
    			}
    		}
    		sender.sendChatToPlayer("Amount of mobs removed:" + amount);
    	}
    	else throw new WrongUsageException(getCommandUsage(sender));
    	
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }

}
