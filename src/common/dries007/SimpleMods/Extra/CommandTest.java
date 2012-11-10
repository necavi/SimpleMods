package dries007.SimpleMods.Extra;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.Crafting;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandTest extends CommandBase
{
	public CommandTest()
	{
		Permissions.addPermission("SM."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "test";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = getCommandSenderAsPlayer(sender);
    	player.sendChatToPlayer("This is a debug commands for testing purposes only.");
    	
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }

}
