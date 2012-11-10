package dries007.SimpleMods.Regions.Commands;

import java.util.Iterator;
import java.util.List;

import dries007.SimpleMods.Permissions;
import dries007.SimpleMods.Regions.API;

import net.minecraft.src.*;

public class CommandRegion extends CommandBase
{
	public String getCommandName()
	{
		return "/region";
	}
	
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return getCommandName() + " <add OR remove> <name> [owner] OR just //regions for a list of regions";
    }
	
	public void processCommand(ICommandSender sender, String[] args)
	{
		if(!(args.length==2) && !(args.length == 3) && !(args.length == 0))
		{
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}
		
		EntityPlayerMP player = ((EntityPlayerMP)sender);
		ItemStack stack = player.inventory.getCurrentItem();
		if(args.length == 0) {
			sender.sendChatToPlayer("List of regions: ");
			 String msg = "";
			 int l = 1;
			 for(String st :  API.getRegions()) {
				 msg = msg + st + ", ";
			     if(l == 3) {
					sender.sendChatToPlayer(msg);
					msg = "";
					l = 1;
		    	} else {
		    		l++;
		    			
		    	}
			 }
				sender.sendChatToPlayer(msg);
			 return;
		}
		if(args[0].equalsIgnoreCase("add"))
		{
			NBTTagCompound regionSelection = (NBTTagCompound) stack.getTagCompound().copy();
		
			if (regionSelection.hasKey("pos1")&&regionSelection.hasKey("pos2"))
			{
				if (API.regionData.hasKey(args[1]))
				{
					throw new WrongUsageException("Region name already used.");
				}
				else
				{
					String owner = args.length == 3 ? args[2] : sender.getCommandSenderName();
					regionSelection.setString("Owner", owner);
					API.regionData.setCompoundTag(args[1], regionSelection);
					API.availableRegions.add(args[1]);
					sender.sendChatToPlayer("Region " + args[1] + " added. "+ owner + " have been set as owner.");
				}
			}
			else
			{
				throw new WrongUsageException("Selection has to be square.");
			}
		}
		else if(args[0].equalsIgnoreCase("remove"))
		{
			if(API.regionData.hasKey(args[1]))
			{
				NBTTagCompound newData = new NBTTagCompound();
				Iterator i = API.regionData.getTags().iterator();
				while(i.hasNext())
				{
					NBTBase tag = (NBTBase) i.next();
					if (!tag.getName().equals(args[1]))
					{
						newData.setTag(args[1], tag);
					}
					else
					{
						sender.sendChatToPlayer("Region " + args[1] + " removed.");
						API.availableRegions.remove(tag.getName());
						API.regionData.removeTag(args[1]);
					
						return;
					}
				}
			}
			else
			{
				sender.sendChatToPlayer("Region " + args[1] + " not found!");
			}
		}
		else
		{
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}
	}
		
	public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
    	return Permissions.hasPermission(sender.getCommandSenderName(), "SR.all");
    }
}
