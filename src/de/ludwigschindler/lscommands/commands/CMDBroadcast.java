package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDBroadcast implements CommandExecutor {

    private static boolean active;

    private static boolean sendToConsole = false;

    public static boolean isSendToConsole() {
        return sendToConsole;
    }

    public static void setSendToConsole(boolean sendToConsole) {
        CMDBroadcast.sendToConsole = sendToConsole;
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        CMDBroadcast.active = active;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!isActive()) {
            LSUtil.sendSenderMessage(sender, "general.commandNotActive");
            return true;
        }

        if (!sender.hasPermission("lscommands.broadcast.broadcastMessage")) {
            LSUtil.sendSenderMessage(sender, "general.noPermission");
            return true;
        }


        if (args.length == 0) {
            LSUtil.sendSenderMessage(sender, "general.enterMessage");
            LSUtil.sendSenderMessage(sender, "commands.broadcast.usage", label);
        } else {
            StringBuffer sb = new StringBuffer();
            for (String s : args) {
                sb.append(s);
            }

            LSUtil.broadcastMessage("commands.broadcast.broadcastMessage", sb.toString());
        }

        return true;
    }
}