package dries007.SimpleMods.Extra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandPw extends CommandBase
{
	public CommandPw()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "pw";
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
    	NBTTagCompound data = Permissions.getPlayerSetting(sender.getCommandSenderName()).getCompoundTag("PW");
    	EntityPlayer player = getCommandSenderAsPlayer(sender);
    	if (data.hasKey(args[0]))
    	{
    		PotionEffect effect = new PotionEffect(9, 120 , 4);
    		player.addPotionEffect(effect);
    		effect = new PotionEffect(15, 30 , 4);
    		player.addPotionEffect(effect);
    		String[] pw = data.getString(args[0]).split(";");
    		if (pw.length!=6) sender.sendChatToPlayer("Corrupt PW found!");
    		if (Integer.parseInt(pw[5])!=player.dimension)  
    		{
    			SimpleMods.tpToDim(player, Integer.parseInt(pw[5]));
    		}
    		Double X = Double.valueOf(pw[0]);
    		Double Y = Double.valueOf(pw[1]);
    		Double Z = Double.valueOf(pw[2]);
    		Float pitch = Float.valueOf(pw[3]);
    		Float yaw = Float.valueOf(pw[4]);
    		((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
    		sender.sendChatToPlayer("Poof!");
    	}
    	else
    	{
    		sender.sendChatToPlayer("PW not found!");
    	}
    }
    
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
    	if(args.length==1)
    	{
    		NBTTagCompound data = Permissions.getPlayerSetting(sender.getCommandSenderName()).getCompoundTag("PW");
        	List<String> pws = new ArrayList<String>();
        	Iterator i = data.getTags().iterator();
        	while(i.hasNext())
        	{
        		NBTBase tag = (NBTBase)i.next();
        		pws.add(tag.getName());
        	}
    		return getListOfStringsFromIterableMatchingLastWord(args, pws);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM.pw");
    }
}
