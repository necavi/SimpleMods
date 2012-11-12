package dries007.SimpleMods;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLCommonHandler;

public class SimpleModsTranslation {
	public static String wand;
	public static String welcomeMessageCoreDefaultRank;
	public static String welcomeMessageCore;
	public static String noFireSpread;
	public static String noExplosion;
	public static String noPlayerBlock;
	public static String noPlayerItem;
	public static String godmode;
	public static String noPvp;
	public static String noFallDamage;
	public static String noGrowth;
	public static String noChest;
	public static String tpaTimeout;
	public static String wbMessage;
	public static String pingMsg;
	public static String nochestmessage;
	public static String noPlayerBlockMessage;

	

	public static void translation(String modId) {
		final String CATEGORY_TRANSLATION = "SimpleMods Welcomemessages";
		final String CATEGORY_Flags = "SimpleMods Flags";
		final String CATEGORY_COMMANDS = "SimpleMods Commands";
		final String CATEGORY_WANDS = "SimpleMods Wand";



		Configuration config = new Configuration(new File("config/SimpleMods/SimpleMods.lan"));

		config.load();
		Property prop;
		
		//Welcomemessages
		prop = config.get(CATEGORY_TRANSLATION, "welcomeMessageCore", "You have been given the rank of " + Permissions.defaultRank + ".");
		prop.comment = "The first message message you see when you first enter the server, which tells you the defaultrank. Use %d as placeholder for defaultrank";
		welcomeMessageCoreDefaultRank = prop.value;
		prop = config.get(CATEGORY_TRANSLATION, "welcomeMessageCore", "This server uses the SimpleMods permissions system.");
		prop.comment = "The first message, which tells the user,that the server uses the SimpleMods system";
		welcomeMessageCore = prop.value;
		
		//Flags
		prop = config.get(CATEGORY_Flags, "nofirespead","This flag turns off firespread in this region. Mod needs to be a code mod for this!");
		noFireSpread = prop.value;
		prop = config.get(CATEGORY_Flags, "noexplosion","This flag turns off explosions in this region. Mod needs to be a code mod for this!");
		noExplosion = prop.value;
		prop = config.get(CATEGORY_Flags, "noplayerblock", "This flag makes placing and removing blocks impossible, exept for the members.");
		noPlayerBlock = prop.value;
		prop = config.get(CATEGORY_Flags, "noplayeritem", "This flag makes use of items impossible (food too!), exept for the members.");
		noPlayerItem = prop.value;
		prop = config.get(CATEGORY_Flags, "godmode", "This flag makes all players invincible in this region. Overrules pvp.");
		godmode = prop.value;
		prop = config.get(CATEGORY_Flags, "nopvp", "This flag makes pvp impossible in this region.");
		noPvp = prop.value;
		prop = config.get(CATEGORY_Flags, "nofalldamage", ";-)");
		noFallDamage = prop.value;
		prop = config.get(CATEGORY_Flags, "nogrowth", "This flag turns off treegrowth in this region. Mod needs to be a code mod for this!");
		noGrowth = prop.value;
		prop = config.get(CATEGORY_Flags, "nochest", "This tag makes opening chest impossible");
		noChest = prop.value;
		prop = config.get(CATEGORY_Flags, "nochestmessage", "You are not allowed to open chests in this region");
		nochestmessage = prop.value;
		prop = config.get(CATEGORY_Flags, "noplayerblockmessage", "flag noplayerblock cancelt the event.");
		noPlayerBlockMessage = prop.value;
		//Wand
		prop = config.get(CATEGORY_WANDS, "wandName", "Wand");
		wand = prop.value;
		
		//Commands
		prop = config.get(CATEGORY_COMMANDS, "tpaTimeout", "TPA timed out");
		tpaTimeout = prop.value;
		
 		prop = config.get(CATEGORY_COMMANDS, "wbMessage", "World ends here");
		prop.comment = null;
		wbMessage = prop.value;
		
		prop = config.get(CATEGORY_COMMANDS, "PingMsg", "Pong!");
		prop.comment = "Response to the Ping Command";
		pingMsg = prop.value;
		
		config.save();

	}

}
