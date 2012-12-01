package dries007.SimpleMods;

import java.util.Iterator;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker 
{
	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if(SimpleModsConfiguration.server.isDedicatedServer())
			{
				if(!Permissions.playerData.getCompoundTag(player.username).hasKey("Rank"))
				{
					NBTTagCompound data = new NBTTagCompound();
					data.setString("Rank", Permissions.defaultRank);
					Permissions.playerData.setCompoundTag(player.username, data);
					player.addChatMessage(SimpleModsTranslation.welcomeMessageCoreDefaultRank.replaceAll("%d", Permissions.defaultRank));
					player.addChatMessage(SimpleModsTranslation.welcomeMessageCore);
					if(Permissions.rankData.getCompoundTag(Permissions.getRank(player)).hasKey("Spawn"))
					{
						data = Permissions.rankData.getCompoundTag(Permissions.getRank(player)).getCompoundTag("Spawn");
						Double X =  data.getDouble("X");
						Double Y =  data.getDouble("Y");
						Double Z =  data.getDouble("Z");
						Float yaw =  data.getFloat("yaw");
						Float pitch =  data.getFloat("pitch");
						Integer dim = data.getInteger("Dim");
						if (player.dimension!=dim) ModLoader.getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
						((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
						player.sendChatToPlayer("Welcome to " + data.getString("name"));
					}
				}
			}
			else
			{
				if(!Permissions.playerData.getCompoundTag(player.username).hasKey("Rank"))
				{
					NBTTagCompound data = new NBTTagCompound();
					data.setString("Rank", Permissions.opRank);
					Permissions.playerData.setCompoundTag(player.username, data);
					if(Permissions.rankData.getCompoundTag(Permissions.getRank(player)).hasKey("Spawn"))
					{
						data = Permissions.rankData.getCompoundTag(Permissions.getRank(player)).getCompoundTag("Spawn");
						Double X =  data.getDouble("X");
						Double Y =  data.getDouble("Y");
						Double Z =  data.getDouble("Z");
						Float yaw =  data.getFloat("yaw");
						Float pitch =  data.getFloat("pitch");
						Integer dim = data.getInteger("Dim");
						if (player.dimension!=dim) ModLoader.getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension(((EntityPlayerMP) player), dim);
						((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(X, Y, Z, yaw, pitch);
						player.sendChatToPlayer("Welcome to " + data.getString("name"));
					}
				}
			}
		}
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{
		MinecraftServer server = ModLoader.getMinecraftServerInstance();
		ChunkCoordinates var4 = ((EntityPlayerMP) player).getBedLocation();
		if (var4 == null)
		{
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
			else if(SimpleModsConfiguration.spawnOverride)
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
		
		
	}

}
