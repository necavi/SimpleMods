package dries007.SimpleMods;

import java.util.*;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements IScheduledTickHandler
{
	public static List TPA = new ArrayList<EntityPlayer>();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		List ToRemove = new ArrayList<EntityPlayer>();
		
		//Does TPA timeout
		try
		{
			Iterator i = TPA.iterator();
			while(i.hasNext())
			{
				EntityPlayerMP player = (EntityPlayerMP) i.next();
				int time = player.getEntityData().getInteger("TPAtime");
				if(player.getEntityData().getString("TPA").equals("."))
				{
					player.getEntityData().setInteger("TPAtime", 0);
					ToRemove.add(player);
				}
				if(time==0)
				{
					ToRemove.add(player);
					EntityPlayerMP source = SimpleMods.server.getConfigurationManager().getPlayerForUsername(player.getEntityData().getString("TPA").trim());
					if(source==null) FMLLog.severe("TPA source was null, Username was: " + player.getEntityData().getString("TPA").trim());
					else source.addChatMessage("TPA timed out");
					player.addChatMessage("TPA timed out");
				}
				else
				{
					player.getEntityData().setInteger("TPAtime", time-1);
				}	
			}
			TPA.removeAll(ToRemove);
		}
		catch(Exception e)
		{
			FMLLog.severe("Dafuq? SimpleCommands Error code: TickHandler.tickStart...");
			FMLLog.severe("Please make a forum post if you get this. http://ssm.dries007.net");
		}		
		//Next!*/
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() 
	{
		return "SimpleCommands";
	}

	@Override
	public int nextTickSpacing() 
	{
		return 20;
	}
	
}
