package de.ludwigschindler.lscommands.main.language;

import de.ludwigschindler.lscommands.main.LSCommands;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.logging.Level;

public class LSText {


    /**
     * All LSTexts.
     */
    private static final ArrayList<LSText> LSTEXTS = new ArrayList<>();

    /**
     * Language.
     */
    private final LSLanguage language;

    /**
     * Message code for this text.
     * <p>
     * Every text has a unique message code which is used to access it.
     */
    private final String messageCode;

    /**
     * Textvalue.
     */
    private final String text;


    /**
     * Creates a new LSText Object.
     *
     * @param language
     * @param messageCode
     * @param text
     */
    public LSText(LSLanguage language, String messageCode, String text) {
        this.language = language;
        this.messageCode = messageCode;
        this.text = text;

        LSTEXTS.add(this);
    }

    /**
     * Finds the first LSText Object for the given language and message code.
     *
     * @param lang        language
     * @param messageCode Messagecode
     * @return LSText → null wenn nicht gefunden
     */
    public static LSText getLSText(LSLanguage lang, String messageCode) {
        //Erster gefundener Text
        return LSTEXTS.stream().filter(text -> text.getLanguage() == lang && text.getMessageCode().equals(messageCode)).findFirst().orElseGet(() -> LSTEXTS.stream().filter(text -> text.getLanguage() == LSLanguage.getLanguageByAlias("default") && text.getMessageCode().equals(messageCode)).findFirst().orElse(null));
    }

    /**
     * Delivers the text value of the first Text for the given language and message code.
     *
     * @param lang
     * @param messageCode
     * @return Textvalue
     */
    public static String getText(LSLanguage lang, String messageCode) {
        LSText t = LSTEXTS.stream().filter(text -> text.getLanguage() == lang && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
        if (t != null) {
            return t.getText();
        } else {
            LSText defaultTemp = LSTEXTS.stream().filter(text -> text.getLanguage() == LSLanguage.getLanguageByAlias("default") && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
            if (defaultTemp != null) {
                return defaultTemp.getText();
            } else {
                LSCommands.plugin.getLogger().log(Level.WARNING, LSCommands.getConsoleNoMessageData());
                return LSCommands.getNoMessageData();
            }
        }
    }

    /**
     * Initializes the texts for every language.
     */
    public static void initTexts() {
        LSLanguage.getLanguages().forEach(LSText::initTexts);
    }

    public static String replaceParameters(String text, String... parameter) {
        for (int i = 0; i < parameter.length; i++) {
            text = text.replace("#" + (i + 1) + "#", parameter[i]);
        }
        return text;
    }

    /**
     * Initializes the texts for a specific language.
     *
     * @param lang language
     */
    private static void initTexts(LSLanguage lang) {
        YamlConfiguration yml = lang.getYml();

        for (String s : yml.getKeys(true)) {

            if (!s.startsWith("general.language") && !s.contains(".settings")) {

                String rawText = yml.getString(s);
                assert rawText != null;
                if (!yml.isConfigurationSection(s)) {
                    new LSText(lang, s.replace(".messages", ""), lang.formatString(rawText));
                }
            }
        }
    }

    public static ArrayList<LSText> getLstexts() {
        return LSTEXTS;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public LSLanguage getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

}
