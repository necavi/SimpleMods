package dries007.SimpleMods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import cpw.mods.fml.server.FMLServerHandler;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class Data 
{
	/**
	 * Saves given data to a .dat
	 * 
	 * @param filename The filename
	 * @param data The data to be saved
	 * @return boolean Success? 
	 */
	public static boolean saveData(NBTTagCompound data, String filename)
	{
		String folder;
		if (SimpleModsConfiguration.server.isDedicatedServer()) folder = SimpleModsConfiguration.server.getFolderName();
		else folder = "saves" + File.separator + SimpleModsConfiguration.server.getFolderName();
		try
		{
			File file = new File(folder+File.separator+"SimpleMods");
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			File var3 = new File(folder+File.separator+"SimpleMods", filename + "_tmp_.dat");
			File var4 = new File(folder+File.separator+"SimpleMods", filename + ".dat");
			
			CompressedStreamTools.writeCompressed(data, new FileOutputStream(var3));
			
			if (var4.exists())
			{
				var4.delete();
			}

			var3.renameTo(var4);
			return true;
		}
		catch (Exception var5)
		{
			ModLoader.getLogger().severe("Failed to save " + filename + ".dat!");
			return false;
		}
	}
	
	
	/**
	 * Returns a player's rank as string
	 * 
	 * @param filename The filename
	 * @return NBTTagCompound The data asked for
	 */
	public static NBTTagCompound loadData(String filename)
	{
		String folder;
		if (SimpleModsConfiguration.server.isDedicatedServer()) folder = SimpleModsConfiguration.server.getFolderName();
		else folder = "saves" + File.separator + SimpleModsConfiguration.server.getFolderName();
		try
		{
			File var2 = new File(folder+File.separator+"SimpleMods", filename + ".dat");
			
			if (var2.exists())
			{
				return CompressedStreamTools.readCompressed(new FileInputStream(var2));
			}
			else
			{
				return new NBTTagCompound();
			}
		}
		catch (Exception var3)
		{
			ModLoader.getLogger().severe("Failed to load SimperProtect data!");
			return null;
		}
	}
}
