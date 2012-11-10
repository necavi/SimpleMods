package dries007.SimpleMods.Extra;

import java.util.List;

import dries007.SimpleMods.Permissions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandBuff extends CommandBase
{
	public CommandBuff()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "buff";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
    	return "/" + getCommandName() + " <ID> [Duration] [Strength]";
    }

    public List getCommandAliases()
    {
        return null;
    }
    
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return null;
    }
    
    public void processCommand(ICommandSender sender, String[] par2ArrayOfStr)
    {
    	try
    	{
    		int ID = 0;
    		int dur = 15 * 20;
    		int amp = 0;
    		switch (par2ArrayOfStr.length) 
    		{
    		case 3:
    			amp = Integer.valueOf(par2ArrayOfStr[2]);
    		case 2:
    			dur = Integer.valueOf(par2ArrayOfStr[1]) * 20;
    		case 1:
    			ID = Integer.valueOf(par2ArrayOfStr[0]);
    			break;
    		default:
    			buffhelp(sender);
    		}
    		if (ID!=0)
    		{
    			EntityPlayer target = (EntityPlayer) sender;
    			PotionEffect eff = new PotionEffect(ID, dur, amp);
    			target.addPotionEffect(eff);
    		}
    	}
    	catch (Exception e)
    	{
    		throw new WrongUsageException("/" + getCommandName() + " <ID> [Duration] [Strength]");
    	}
    }

    public void buffhelp(ICommandSender sender)
    {
    	sender.sendChatToPlayer("ID    =>Name");
		sender.sendChatToPlayer("1     =>Speed");
		sender.sendChatToPlayer("2     =>Slow");
		sender.sendChatToPlayer("3     =>Haste");
		sender.sendChatToPlayer("4     =>Fatigue");
		sender.sendChatToPlayer("5     =>Strength");
		sender.sendChatToPlayer("6     =>Heal");
		sender.sendChatToPlayer("7     =>Damage");
		sender.sendChatToPlayer("8     =>Jump");
		sender.sendChatToPlayer("9     =>Nausea");
		sender.sendChatToPlayer("10    =>Regen");
		sender.sendChatToPlayer("11    =>Resistance");
		sender.sendChatToPlayer("12    =>Fire Resistance");
		sender.sendChatToPlayer("13    =>Waterbreathing");
		sender.sendChatToPlayer("14    =>Invisibility");
		sender.sendChatToPlayer("15    =>Blindness");
		sender.sendChatToPlayer("16    =>Nightvision");
		sender.sendChatToPlayer("17    =>Hunger");
		sender.sendChatToPlayer("18    =>Weakness");
		sender.sendChatToPlayer("19    =>Poison");
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }
}
