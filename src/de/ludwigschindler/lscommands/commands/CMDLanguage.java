package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSPlayer;
import de.ludwigschindler.lscommands.main.language.LSLanguage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Command structure:
 * /language [language]
 */
public class CMDLanguage implements CommandExecutor {

    @java.lang.Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, java.lang.@NotNull String label, @NotNull java.lang.@NotNull String[] args) {

            if (sender instanceof ConsoleCommandSender) {
                if (args.length == 0) {

                } else if (args.length == 1) {

                }
            } else if (sender instanceof Player) {
                LSPlayer p = LSPlayer.senderToLSCPlayer(sender);
                if (args.length == 0) {
                    p.sendMsg("commands.language.displayCurrentLanguage", p.getLanguage().getName());
                } else if (args.length == 1) {
                    LSLanguage lang = LSLanguage.getLanguageByAlias(args[0]);
                    if(lang != null){
                        if(p.getLanguage() != lang){
                        p.setLanguage(lang);
                        p.sendMsg("commands.language.languageSet", lang.getName());
                        }else{
                            p.sendMsg("commands.language.languageIdentical", lang.getName());
                        }
                    }else{
                        p.sendMsg("commands.language.languageNotSupported", args[0]);
                    }
                }
            }

        return true;
    }
}
