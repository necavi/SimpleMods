package dries007.SimpleMods.Regions.Commands;

import dries007.SimpleMods.Permissions;
import dries007.SimpleMods.Regions.API;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.WrongUsageException;

public class CommandOwner extends CommandBase {

	@Override
	public String getCommandName() {
		return "/owner";
	}

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return getCommandName() + " <region> <new owner>";
    }
    
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(args.length==2))
		{
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}
		if(API.regionData.hasKey(args[0])) {
			NBTTagCompound temp = API.regionData.getCompoundTag(args[0]);
			temp.removeTag("Owner");
			temp.setString("Owner", args[1]);
			sender.sendChatToPlayer("New owner " + args[1] + " for region " + args[0] + " was set.");
			
		} else {
			throw new WrongUsageException("Region not found.");
		}
		

	}
	
	
	
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
    	return Permissions.hasPermission(sender.getCommandSenderName(), "SR.all");
    }

}
