package dries007.SimpleMods.Extra;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import dries007.SimpleMods.Crafting;
import dries007.SimpleMods.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CommandSpawn extends CommandBase
{
	public CommandSpawn()
	{
		Permissions.addPermission("SP."+getCommandName());
	}
	
    public String getCommandName()
    {
        return "spawn";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	MinecraftServer server = ModLoader.getMinecraftServerInstance();
    	EntityPlayer player = getCommandSenderAsPlayer(sender);
    	if(Permissions.rankData.getCompoundTag(Permissions.getRank(player)).hasKey("Spawn"))
		{
			NBTTagCompound data = Permissions.rankData.getCompoundTag(Permissions.getRank(player)).getCompoundTag("Spawn");
			Double X =  data.getDouble("X");
			Double Y =  data.getDouble("Y");
			Double Z =  data.getDouble("Z");
			Float yaw =  data.getFloat("yaw");
			Float pitch =  data.getFloat("pitch");
			Integer dim = data.getInteger("Dim");
			if (player.dimension!=dim)server.getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
			((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
			player.sendChatToPlayer("Welcome to " + data.getString("name"));
		}
		else if(SimpleMods.spawnOverride)
		{
			ChunkCoordinates coords = player.worldObj.getSpawnPoint();
			player.setPosition(coords.posX, coords.posY, coords.posZ);
			while (!server.worldServerForDimension(player.dimension).getCollidingBoundingBoxes(player, player.boundingBox).isEmpty())
	        {
				player.setPosition(player.posX, player.posY + 0.5D, player.posZ);
	        }
			((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(coords.posX,coords.posY,coords.posZ,0,0);
		}
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
    	return Permissions.hasPermission(par1ICommandSender.getCommandSenderName(), "SP."+getCommandName());
    }

}
