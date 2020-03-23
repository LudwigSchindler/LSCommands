package de.ludwigschindler.lscommands.main;

import de.ludwigschindler.lscommands.main.language.LSLanguage;
import de.ludwigschindler.lscommands.main.language.LSText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.UUID;

public class LSPlayer extends CraftPlayer {

    /**
     * All LSPlayer objects of players who are online
     */
    private static final HashMap<UUID, LSPlayer> PLAYERS = new HashMap<>();

    /**
     * The original player object
     */
    private final Player PLAYER;
    private final String configName;
    /**
     * The player language (within LSCommands, not minecraft)
     */
    private LSLanguage language;

    private LSPlayer(Player player) {

        //Object
        super((CraftServer) Bukkit.getServer(), ((CraftPlayer) player).getHandle());
        if (LSCommands.useDisplayNames()) configName = getDisplayName();
        else {
            configName = getName();
        }
        PLAYERS.put(player.getUniqueId(), this);
        PLAYER = player;

        //Initialize player data
        initPlayerData();

    }

    public static LSPlayer getPlayer(Player p) {
        if (PLAYERS.containsKey(p.getUniqueId())) {
            return PLAYERS.get(p.getUniqueId());
        } else {
            return new LSPlayer(p);
        }
    }

    public static LSPlayer join(Player p) {
        return new LSPlayer(p);
    }

    public static void leave(Player p) {
        PLAYERS.remove(p.getUniqueId());
    }

    public static LSPlayer senderToLSCPlayer(CommandSender sender) {

        return LSPlayer.getPlayer((Player) sender);

    }

    /**
     * Clears the Chat for an array of players
     * Sends a line break 100 times with super.sendMessage method of the players
     *
     * @param players array of players
     */
    public static void chatClear(LSPlayer[] players, int lines) {
        for (LSPlayer p : players) {
            p.chatClear(lines);
        }
    }

    /**
     * Clears the Chat for an array of players
     * Sends a line break 100 times with super.sendMessage method of the players
     *
     * @param p player
     */
    public static void chatClear(Player p) {

        for (int i = 0; i < 100; i++) {
            p.sendMessage("\n");
        }

    }

    public String getConfigName() {
        return configName;
    }

    /**
     * Initializes player data from players.yml
     */
    private void initPlayerData() {
        //TODO: playerInit +language
        //InitPlayerData
        // String playerLang = LSCommands.players.getString(this.getUniqueId() + ".language");
        //language = (playerLang != null) ? playerLang : getDefaultLanguage();
        language = LSLanguage.getLanguage("default");
    }

    /**
     * @return Default player language
     */
    public String getDefaultLanguage() {
        //TODO: defaultLanguage
        //String mcLang = this.getLocale();
        //return (LSCommands.languageIsSupported(mcLang)) ? mcLang : "de_DE";
        return null;
    }

    /**
     * Updates playerData
     * calls initPlayerData();
     */
    public void update() {
        initPlayerData();
    }

    /**
     * Sends a message to the player; replaces wildcards
     *
     * @param messageCode code for the message from language_COUNTRY.yml or config.yml
     * @param parameter   placeholder values
     */
    public void sendMsg(String messageCode, String... parameter) {

        String text = LSText.getText(language, messageCode);

        String name = getName();

        if (LSCommands.useDisplayNames()) {
            name = getDisplayName();
        }

        text = text.replace("#player#", name);

        text = LSText.replaceParameters(text, parameter);

        text = ChatColor.translateAlternateColorCodes('&', text);

        super.sendMessage(text);
    }

    public LSLanguage getLanguage() {
        return language;
    }

    public void setLanguage(LSLanguage language) {
        this.language = language;
    }

    @Override
    public boolean hasPermission(String perm) {
        return PLAYER.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return PLAYER.hasPermission(perm);
    }

    /**
     * clears the players chat
     * sends 100 line breaks
     */
    public void chatClear(int lines) {
        for (int i = 0; i < lines; i++) {
            this.sendMessage("\n");
        }
    }

    public void openGUI(LSGUI gui) {
        openInventory(gui.getInventory());
    }

}
