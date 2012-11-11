package dries007.SimpleMods.Regions.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.relauncher.ArgsWrapper;
import cpw.mods.fml.relauncher.FMLRelauncher;
import dries007.SimpleMods.Permissions;
import dries007.SimpleMods.Regions.API;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandTest extends CommandBase
{	
    public String getCommandName()
    {
        return "test";
    }
    
//    public List getCommandAliases()
//    {
//        return Arrays.asList(new String[] {"/test", "Test", "/Test"});
//    }
    
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/test";
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
    	return Permissions.hasPermission(sender.getCommandSenderName(), "SR.all");
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	EntityPlayerMP player = ((EntityPlayerMP)sender);
    	
		int X = ((Double) player.posX).intValue();
		int Y = ((Double) player.posY).intValue();
		int Z = ((Double) player.posZ).intValue();
		
		String region = API.getRegion(player.dimension, X, Y, Z);
		
		sender.sendChatToPlayer("Region:" + region + " Owner: " + API.regionData.getCompoundTag(region).getString("Owner"));
		
		sender.sendChatToPlayer("is member of:" + API.isMemberOfRegion(region, args[0]));
		
    }
    
	public static boolean in_circle(int center_x,int center_z,int radius,int x,int z)
    {
        double dist = ((center_x - x) * (center_x - x)) + ((center_z - z) * (center_z - z));
        return dist <= (radius * radius);
    }
}
