package de.ludwigschindler.lscommands.main.language;

import de.ludwigschindler.lscommands.main.LSCommands;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LSLanguage {

    /**
     * All languages
     */
    private static final ArrayList<LSLanguage> LANGUAGES = new ArrayList<>();

    /**
     * YAMLConfigurationFile of the language.
     */
    private final YamlConfiguration yml;

    /**
     * Aliases for the language. Can be used when selecting the language.
     */
    private final List<String> aliases;

    /**
     * Name of the language for clear identification.
     */
    private final String name;

    /**
     * Defines if the plugin prefix should be automatically added to every text of this language.
     */
    private final boolean autoPrefix;

    /**
     * Prefix for texts in the respective language
     */
    private final String prefix;

    /**
     * ChatColor for normal text.
     */
    private final ChatColor textChatColor;
    /**
     * ChatColor for error text.
     */
    private final ChatColor errorChatColor;
    /**
     * ChatColor for object text. This can be used to highlight certain information like selection.
     */
    private final ChatColor objectChatColor;
    /**
     * ChatColor for numbers. This can be used to highlight certain (changed) values.
     */
    private final ChatColor numberChatColor;
    /**
     * ChatColor for playner names. This can be used to highlight player names.
     * <p>
     * Is eventually overwritten by the players display name (if configured so).
     *
     * @see LSCommands#useDisplayNames
     */
    private final ChatColor playerChatColor;


    /**
     * Initializes a language with all necessary parameter.
     *
     * @param yml             YAMLConfiguration File
     * @param aliases         Aliases
     * @param name            Name
     * @param autoPrefix      Automatically attach prefix
     * @param prefix          Prefix
     * @param textChatColor   TextChatColor
     * @param errorChatColor  ErrorChatColor
     * @param objectChatColor ObjectChatColor
     * @param numberChatColor NumberChatColor
     * @param playerChatColor PlayerChatColor
     */
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

    /**
     * Gets the language by an alias.
     *
     * @param alias
     * @return language
     */
    public static LSLanguage getLanguageByAlias(String alias) {
        return LANGUAGES.stream().filter(lang -> lang.aliases.contains(alias)).findFirst().orElse(null);
    }

    /**
     * Gets the language by name.
     *
     * @param name Name
     * @return language
     */
    public static LSLanguage getLanguageByName(String name) {
        return LANGUAGES.stream().filter(lang -> lang.name.equals(name)).findFirst().orElse(null);
    }

    /**
     * Gets all languages
     *
     * @return
     */
    public static ArrayList<LSLanguage> getLanguages() {
        return LANGUAGES;
    }

    /**
     * Finds and initializes all languages by their file.
     *
     * @param langFilePath Directory with the language files
     */
    public static void initLanguages(String langFilePath) {

        initLanguage((YamlConfiguration) LSCommands.config);

        File[] langFile = Objects.requireNonNull(new File(langFilePath).listFiles());

        for (File file : langFile) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            initLanguage(yml);
        }
    }

    /**
     * Initializes a language with all its necessary parameters.
     *
     * @param yml YAMLConfigurationFile of the language (in the language directory)
     */
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

    /**
     * Formats a string with the format and color values of the language-
     *
     * @param s String to format
     * @return Formatted string
     */
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
