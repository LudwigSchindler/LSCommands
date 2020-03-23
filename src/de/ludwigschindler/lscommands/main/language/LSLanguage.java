package de.ludwigschindler.lscommands.main.language;

import de.ludwigschindler.lscommands.main.LSCommands;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LSLanguage {

    private static final ArrayList<LSLanguage> LANGUAGES = new ArrayList<>();

    private final YamlConfiguration yml;

    private final List<String> aliases;

    private final String name;

    private final boolean autoPrefix;

    private final String prefix;

    private final ChatColor textChatColor;

    private final ChatColor errorChatColor;

    private final ChatColor objectChatColor;

    private final ChatColor numberChatColor;

    private final ChatColor playerChatColor;


    private LSLanguage(YamlConfiguration yml, List<String> aliases, String name, boolean autoPrefix, String prefix, ChatColor textChatColor, ChatColor errorChatColor, ChatColor objectChatColor, ChatColor numberChatColor, ChatColor playerChatColor) {
        this.yml = yml;
        this.aliases = aliases;
        this.name = name;
        this.aliases.add(name);
        this.autoPrefix = autoPrefix;
        this.prefix = prefix;
        this.textChatColor = textChatColor;
        this.errorChatColor = errorChatColor;
        this.objectChatColor = objectChatColor;
        this.numberChatColor = numberChatColor;
        this.playerChatColor = playerChatColor;

        LANGUAGES.add(this);
    }

    public static LSLanguage getLanguage(String alias) {
        return LANGUAGES.stream().filter(lang -> lang.aliases.contains(alias)).findFirst().orElse(null);
    }

    public static ArrayList<LSLanguage> getLanguages() {
        return LANGUAGES;
    }

    public static void initLanguages(String langFilePath) {

        initLanguage((YamlConfiguration) LSCommands.config);

        File[] langFile = Objects.requireNonNull(new File(langFilePath).listFiles());

        for (File file : langFile) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            initLanguage(yml);
        }
    }

    private static void initLanguage(YamlConfiguration yml) {
        new LSLanguage(
                yml,
                yml.getStringList("general.language.aliases"),
                yml.getString("general.language.name"),
                yml.getBoolean("general.language.autoPrefix"),
                ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(yml.getString("general.language.prefix"))),
                ChatColor.getByChar(Objects.requireNonNull(yml.getString("general.language.text-chat-color"))),
                ChatColor.getByChar(Objects.requireNonNull(yml.getString("general.language.error-chat-color"))),
                ChatColor.getByChar(Objects.requireNonNull(yml.getString("general.language.object-chat-color"))),
                ChatColor.getByChar(Objects.requireNonNull(yml.getString("general.language.number-chat-color"))),
                ChatColor.getByChar(Objects.requireNonNull(yml.getString("general.language.player-chat-color"))));
    }

    public static ArrayList<LSLanguage> getLANGUAGES() {
        return LANGUAGES;
    }

    public String formatString(String s) {
        if (autoPrefix) {
            s = prefix + " " + s;
        }

        s = s.replace("#prefix#", prefix);
        s = s.replace("#tcc#", "" + textChatColor);
        s = s.replace("#ecc#", "" + errorChatColor);
        s = s.replace("#occ#", "" + objectChatColor);
        s = s.replace("#ncc#", "" + numberChatColor);
        s = s.replace("#pcc#", "" + playerChatColor);

        return s;
    }

    public YamlConfiguration getYml() {
        return yml;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getTextChatColor() {
        return textChatColor;
    }

    public ChatColor getErrorChatColor() {
        return errorChatColor;
    }

    public ChatColor getObjectChatColor() {
        return objectChatColor;
    }

    public ChatColor getNumberChatColor() {
        return numberChatColor;
    }

    public ChatColor getPlayerChatColor() {
        return playerChatColor;
    }

    public boolean isAutoPrefix() {
        return autoPrefix;
    }
}
