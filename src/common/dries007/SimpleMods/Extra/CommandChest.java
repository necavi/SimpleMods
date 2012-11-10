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
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "chest";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = ((EntityPlayerMP) sender);
    	
    	if (player.craftingInventory != player.inventorySlots)
    	{
    		player.closeScreen();
    	}
    	player.incrementWindowID();
    	
    	InventoryEnderChest chest = player.getInventoryEnderChest();
		player.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(player.currentWindowId, 0, chest.getInvName(), chest.getSizeInventory()));
    	player.craftingInventory = new ContainerChest(player.inventory, chest);
    	player.craftingInventory.windowId = player.currentWindowId;
    	player.craftingInventory.addCraftingToCrafters(player);
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	 return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }

}
