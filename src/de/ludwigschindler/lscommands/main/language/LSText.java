package de.ludwigschindler.lscommands.main.language;

import de.ludwigschindler.lscommands.main.LSCommands;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class LSText {


    private static final ArrayList<LSText> LSTexts = new ArrayList<>();

    private final LSLanguage language;

    private final String messageCode;

    private final String text;


    public LSText(LSLanguage language, String messageCode, String text) {
        this.language = language;
        this.messageCode = messageCode;
        this.text = text;

        LSTexts.add(this);
    }

    public static LSText getLSText(LSLanguage lang, String messageCode) {
        LSText t = LSTexts.stream().filter(text -> text.getLanguage() == lang && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
        if (t != null) {
            return t;
        } else {
            return LSTexts.stream().filter(text -> text.getLanguage() == LSLanguage.getLanguage("default") && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
        }
    }

    public static String getText(LSLanguage lang, String messageCode) {
        LSText t = LSTexts.stream().filter(text -> text.getLanguage() == lang && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
        if (t != null) {
            return t.getText();
        } else {
            LSText defaultTemp = LSTexts.stream().filter(text -> text.getLanguage() == LSLanguage.getLanguage("default") && text.getMessageCode().equals(messageCode)).findFirst().orElse(null);
            if(defaultTemp != null){
                return defaultTemp.getText();
            }else{
                LSCommands.plugin.getLogger().log(Level.WARNING, LSCommands.getConsoleNoMessageData());
                return LSCommands.getNoMessageData();
            }
        }
    }

    public static void initTexts() {
        LSLanguage.getLanguages().forEach(LSText::initTexts);
    }

    public static String replaceParameters(String text, String... parameter) {
        for (int i = 0; i < parameter.length; i++) {
            text = text.replace("#" + (i + 1) + "#", parameter[i]);
        }
        return text;
    }

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

    public static ArrayList<LSText> getLSTexts() {
        return LSTexts;
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
