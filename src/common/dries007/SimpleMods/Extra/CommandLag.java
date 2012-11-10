package dries007.SimpleMods.Extra;

import java.text.DecimalFormat;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraftforge.common.DimensionManager;

public class CommandLag extends CommandBase
{
	public CommandLag()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "lag";
    }
    
    private static final DecimalFormat field_79020_a = new DecimalFormat("########0.000");

    public void processCommand(ICommandSender sender, String[] args)
    {
    	if(!FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
    	MinecraftServer server = SimpleMods.server;
        long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sender.sendChatToPlayer("");
        sender.sendChatToPlayer("Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)");
        sender.sendChatToPlayer("Threads: " + TcpConnection.field_74471_a.get() + " + " + TcpConnection.field_74469_b.get());
        sender.sendChatToPlayer("Avg tick: " + field_79020_a.format(this.func_79015_a(server.tickTimeArray) * 1.0E-6D) + " ms");
        sender.sendChatToPlayer("Avg sent: " + (int)this.func_79015_a(server.sentPacketCountArray) + ", Avg size: " + (int)this.func_79015_a(server.sentPacketSizeArray));
        sender.sendChatToPlayer("Avg rec: " + (int)this.func_79015_a(server.receivedPacketCountArray) + ", Avg size: " + (int)this.func_79015_a(server.receivedPacketSizeArray));

        if (server.worldServers != null)
        {
            int x = 0;
            for (Integer id : DimensionManager.getIDs())
            {
            	sender.sendChatToPlayer("Lvl " + id + " tick: " + field_79020_a.format(this.func_79015_a(server.worldTickTimes.get(id)) * 1.0E-6D) + " ms");
                x++;
            }
        }
        sender.sendChatToPlayer("");
    }
    
    private double func_79015_a(long[] par1ArrayOfLong)
    {
        long var2 = 0L;
        long[] var4 = par1ArrayOfLong;
        int var5 = par1ArrayOfLong.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            long var7 = var4[var6];
            var2 += var7;
        }

        return (double)var2 / (double)par1ArrayOfLong.length;
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }

}
