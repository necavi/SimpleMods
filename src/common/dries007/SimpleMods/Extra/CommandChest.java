package dries007.SimpleMods.Extra;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandChest extends CommandBase
{
	public CommandChest()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "chest";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = ((EntityPlayerMP) sender);
    	
    	if (player.openContainer != player.inventoryContainer)
    	{
    		player.closeScreen();
    	}
    	player.incrementWindowID();
    	
    	InventoryEnderChest chest = player.getInventoryEnderChest();
		player.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(player.currentWindowId, 0, chest.getInvName(), chest.getSizeInventory()));
    	player.openContainer = new ContainerChest(player.inventory, chest);
    	player.openContainer.windowId = player.currentWindowId;
    	player.openContainer.addCraftingToCrafters(player);
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	 return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM."+getCommandName());
    }

}
