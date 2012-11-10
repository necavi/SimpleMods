package dries007.SimpleMods.asm;

import java.util.Arrays;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import dries007.SimpleMods.*;
import dries007.SimpleMods.Regions.API;

public class SimpleModsModContainer extends DummyModContainer
{
	public SimpleModsModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta =	getMetadata();
        meta.modId       =	"SimpleMods";
        meta.name        =	"SimpleMods";
        meta.version     =	"0";
        meta.authorList  =	Arrays.asList("Dries007", "Weneg");
        meta.credits	 =	"Dries007, Weneg, ChickenBones for making his mods open-source!";
        meta.description = 	"All is one SimpleMods Package";
        meta.url         =	"http://ssm.dries007.net";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new VanillaInterface());
		return true;
	}
	
	@Subscribe
	public void init(FMLInitializationEvent event) 
	{
		if (event.getSide().isClient())
		{
			net.minecraftforge.client.MinecraftForgeClient.preloadTexture(SimpleMods.WANDTEXTURE);
		}
		SimpleMods.ItemWand = new dries007.SimpleMods.Regions.ItemWand(SimpleMods.ItemWandID);
		LanguageRegistry.addName(SimpleMods.ItemWand, "Wand");
		GameRegistry.addShapelessRecipe(new ItemStack(SimpleMods.ItemWand, 1), new Object[] {new ItemStack(Item.stick, 1)});
	}
	
	
	@Subscribe
    public void serverStarting(FMLServerStartingEvent event)
	{
		SimpleMods.server = FMLCommonHandler.instance().getMinecraftServerInstance();
		SimpleMods.addCommands();
		
		NBTTagInt example = new NBTTagInt("Example", 42);
		Permissions.addDefaultSetting(example);
		
		Permissions.worldData = Data.loadData("worldData");
		Permissions.playerData=Data.loadData("playerData");
		Permissions.rankData=Data.loadData("rankData");
		API.regionData = Data.loadData("regionData");
		
		Permissions.addPermission("SM.all");
		Permissions.addPermission("SM.admin");
		
		if(!Permissions.rankData.hasKey(Permissions.opRank)) Permissions.newRank(Permissions.opRank);
		if(!Permissions.rankData.hasKey(Permissions.defaultRank)) Permissions.newRank(Permissions.defaultRank);
		
		for(Object base : Permissions.rankData.getTags())
		{
			NBTBase tag = (NBTBase) base;
			Permissions.addRank(tag.getName());
		}
		
		TickRegistry.registerScheduledTickHandler(new TickHandler(), Side.SERVER);
		TickRegistry.registerScheduledTickHandler(new WorldBorder(), Side.SERVER);
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}
	
	@Subscribe
	public void serverStarted(FMLServerStartedEvent event)
	{
		if(event.getSide().isServer() && SimpleMods.postModlist) SimpleMods.writemodlist(event);
	}
	
	@ForgeSubscribe
	public void chuckSave(WorldEvent.Save event)
	{
		Data.saveData(Permissions.worldData, "worldData");
		Data.saveData(Permissions.playerData, "playerData");
		Data.saveData(Permissions.rankData, "rankData");
		Data.saveData(API.regionData, "regionData");
	}
	
	@Subscribe
	public void serverStopping(FMLServerStoppingEvent event)
	{
		Data.saveData(Permissions.worldData, "worldData");
		Data.saveData(Permissions.playerData, "playerData");
		Data.saveData(Permissions.rankData, "rankData");
		Data.saveData(API.regionData, "regionData");
	}
}
