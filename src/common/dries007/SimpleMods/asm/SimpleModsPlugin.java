package dries007.SimpleMods.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import dries007.SimpleMods.SimpleMods;

@TransformerExclusions(value={"dries007.SimpleMods.asm"})
public class SimpleModsPlugin implements IFMLLoadingPlugin, IFMLCallHook
{
	public static RelaunchClassLoader cl;
	public static File minecraftDir;
	public static File myLocation;
	
	@Override
	public Void call() throws Exception 
	{
		SimpleMods.makeConfig();
		SimpleMods.addOverrides();
		return null;
	}

	@Override
	public String[] getLibraryRequestClass() 
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass() 
	{
		return new String[]	{"dries007.SimpleMods.asm.SimpleModsTransformer"};
	}

	@Override
	public String getModContainerClass() 
	{
		return "dries007.SimpleMods.asm.SimpleModsModContainer";
	}

	@Override
	public String getSetupClass() 
	{
		return "dries007.SimpleMods.asm.SimpleModsPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) 
	{
		cl = (RelaunchClassLoader) data.get("classLoader");
		if(data.containsKey("mcLocation"))
		{
			minecraftDir = (File) data.get("mcLocation");
		}
		if(data.containsKey("coremodLocation"))
		{
			myLocation = (File) data.get("coremodLocation");
		}
	}

}
