package dries007.SimpleMods.asm;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.IClassTransformer;

public class SimpleModsTransformer implements IClassTransformer
{
	public static HashMap<String, String> override = new HashMap<String, String>();
	
	@Override
	public byte[] transform(String name, byte[] bytes) 
	{
		if(override.containsKey(name))
		{
			bytes = overrideBytes(name, bytes, SimpleModsPlugin.myLocation);
		}
		return bytes;
	}
	
	public static void addClassOverride(String name, String discription)
	{
		override.put(name , discription);
	}
	
	public static byte[] overrideBytes(String name, byte[] bytes, File location)
	{
		try
		{
			ZipFile zip = new ZipFile(location);
			ZipEntry entry = zip.getEntry(name.replace('.', '/')+".class");
			if(entry == null)
				System.out.println(name+" not found in "+location.getName());
			else
			{
				InputStream zin = zip.getInputStream(entry);
				bytes = new byte[(int) entry.getSize()];
				zin.read(bytes);
				zin.close();
				System.out.println(name+" was overriden from "+location.getName());
			}
			zip.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error overriding "+name+" from "+location.getName(), e);
		}
		return bytes;
	}
}
