package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSPlayer;
import de.ludwigschindler.lscommands.main.LSUtil;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CMDChatClear implements CommandExecutor {

    private static boolean active = true;
    private static int numberOfEmptyLinesSent = 100;
    private static boolean clearConsole = false;

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        CMDChatClear.active = active;
    }

    public static int getNumberOfEmptyLinesSent() {
        return numberOfEmptyLinesSent;
    }

    public static void setNumberOfEmptyLinesSent(int numberOfEmptyLinesSent) {
        CMDChatClear.numberOfEmptyLinesSent = numberOfEmptyLinesSent;
    }

    public static boolean isClearConsole() {
        return clearConsole;
    }

    public static void setClearConsole(boolean clearConsole) {
        CMDChatClear.clearConsole = clearConsole;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (args.length == 0) {

            if (sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender) {
                LSUtil.chatClear();
                //TODO:  LSUtil.sendConsoleMessage("commands.chatclear.clearAll");
                LSUtil.broadcastMessage("commands.chatclear.clearAll", "Console/Commandblock");

            } else if (sender instanceof Player) {

                LSPlayer p = LSPlayer.senderToLSCPlayer(sender);
                if (p.hasPermission("LSCommands.command.chatclear.clearAll")) {
                    LSUtil.chatClear();
                    LSUtil.broadcastMessage("commands.chatclear.clearAll", p.getDisplayName());
                    //TODO: LSUtil.sendConsoleMessage("commands.chatclear.clearAll");

                } else {
                    p.sendMsg("general.noPermission");
                }

            }
        }
        return true;
    }
}
