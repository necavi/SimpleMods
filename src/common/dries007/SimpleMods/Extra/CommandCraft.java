package dries007.SimpleMods.Extra;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandCraft extends CommandBase
{
	public CommandCraft()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "craft";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = getCommandSenderAsPlayer(sender);
    	player.incrementWindowID();
    	player.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(player.currentWindowId, 1, "Crafting", 9));
    	player.openContainer = new dries007.SimpleMods.Crafting(player.inventory, player.worldObj, 0, 0, 0);
    	player.openContainer.windowId = player.currentWindowId;
    	player.openContainer.addCraftingToCrafters(player);
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SM."+getCommandName());
    }

}
