package de.ludwigschindler.lscommands.main;

import de.ludwigschindler.lscommands.commands.*;
import de.ludwigschindler.lscommands.listeners.ListenerPlayerChat;
import de.ludwigschindler.lscommands.listeners.ListenerPlayerJoin;
import de.ludwigschindler.lscommands.listeners.ListenerPlayerKick;
import de.ludwigschindler.lscommands.listeners.ListenerPlayerQuit;
import de.ludwigschindler.lscommands.main.language.LSLanguage;
import de.ludwigschindler.lscommands.main.language.LSText;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class LSCommands extends JavaPlugin {

    public static final boolean DEBUG = true;

    /**
     * Text that is being put out to the player when no respective message is found
     */
    private static final String NO_MESSAGE_DATA = "§7No message data found for this command. Please contact an administrator!";
    /**
     * Text that is being put out to the console
     */
    private static final String CONSOLE_NO_MESSAGE_DATA = "§4NO MESSAGE DATA FOUND! PLEASE SET UP MESSAGE FILES OR THE CONFIG.YML AND RELOAD THE PLUGIN/SERVER!";

    //Variables
    public static JavaPlugin plugin;
    /**
     * General YAMLConfigurationFile config.yml
     */
    public static FileConfiguration config;

    //Files

    /**
     * File with playerdata (File)
     */
    private static File playersFile = new File("plugins//LSCommands//players.yml");

    /**
     * YAMLConfigurationFile with playerdata
     */
    public static YamlConfiguration players = YamlConfiguration.loadConfiguration(playersFile);

    /**
     * Warp file (File)
     */
    private static File warpFile = new File("plugins//LSCommands//warps.yml");

    /**
     * YAMLConfigurationFile with warps data
     */
    public static YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);

    /**
     * Indicates if the plugins uses display names or real names
     */
    private static boolean useDisplayNames;


    public static boolean useDisplayNames() {
        return useDisplayNames;
    }

    public static String getNoMessageData() {
        return NO_MESSAGE_DATA;
    }

    public static String getConsoleNoMessageData() {
        return CONSOLE_NO_MESSAGE_DATA;
    }




    //onEnable
    @Override
    public void onEnable() {

        plugin = this;

        initConfig();

        initMessages();
        useDisplayNames = true;

        //registering commands
        registerCommands();

        //registering events
        registerEvents();

        //enable message
        System.out.println("ENABLED " + this.getFile().getName());

    }

    private void registerEvents() {
        getLogger().info("Registering events...");
        getServer().getPluginManager().registerEvents(new ListenerPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerKick(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerQuit(), this);
    }

    private void initMessages(){
        getLogger().info("Initializing languages...");
        LSLanguage.initLanguages(this.getDataFolder().getAbsolutePath() + File.separator + "messages");
        LSText.initTexts();
    }

    //onDisable
    @Override
    public void onDisable() {

        System.out.println("DISABLED " + this.getFile().getName());

    }

    private void registerCommands() {
        getLogger().info("Registering commands...");

        //lscomands
        this.getCommand("lscommands").setExecutor(new CMDLSCommands());

        //language
        this.getCommand("language").setExecutor(new CMDLanguage());

        //chatclear
        this.getCommand("chatclear").setExecutor(new CMDChatClear());
        CMDChatClear.setActive(getConfig().getBoolean("commands.chatclear.settings.active"));
        CMDChatClear.setNumberOfEmptyLinesSent(getConfig().getInt("commands.chatclear.settings.numberOfEmtpyLinesSent"));
        CMDChatClear.setClearConsole(getConfig().getBoolean("commands.chatclear.settings.clearConsole"));

        //privatechatclear
        this.getCommand("privatechatclear").setExecutor(new CMDPrivateChatClear());

        //skull
        this.getCommand("skull").setExecutor(new CMDSkull());

        //broadcast
        this.getCommand("broadcast").setExecutor(new CMDBroadcast());
        CMDBroadcast.setActive(getConfig().getBoolean("commands.broadcast.settings.active"));
        CMDBroadcast.setSendToConsole(getConfig().getBoolean("commands.broadcast.settings.sendToConsole"));

    }


    /**
     * Initializes the config.yml
     */
    private void initConfig() {
        getLogger().info("Initializing config.yml...");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        LSCommands.config = getConfig();
    }



}
