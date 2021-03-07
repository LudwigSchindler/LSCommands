package de.ludwigschindler.lscommands.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public abstract class LSUtil {

    public static ItemStack getCustomSkull(String n) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta metaskull = (SkullMeta) skull.getItemMeta();
        metaskull.setOwner(n);
        skull.setItemMeta(metaskull);

        return skull;
    }

    public static ItemStack getCustomSkull(String n, String name, String b) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta metaskull = (SkullMeta) skull.getItemMeta();
        metaskull.setOwner(n);
        metaskull.setDisplayName(name);
        ArrayList<String> slist = new ArrayList<String>();
        slist.add(b);
        metaskull.setLore(slist);
        skull.setItemMeta(metaskull);

        return skull;
    }

    public static ItemStack getCustomSkull(String n, String name, String b, String b2) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta metaskull = (SkullMeta) skull.getItemMeta();
        metaskull.setOwner(n);
        metaskull.setDisplayName(name);
        ArrayList<String> slist = new ArrayList<String>();
        slist.add(b);
        slist.add(b2);
        metaskull.setLore(slist);
        skull.setItemMeta(metaskull);

        return skull;
    }

    /**
     * Clears the Chat for all players
     * Sends a line break 100 times with broadcast
     */
    public static void chatClear() {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("\n");
        }
    }

    public static void broadcastMessage(String path, String... parameters) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            LSPlayer lp = LSPlayer.getPlayer(p);
            lp.sendMsg(path, parameters);
        }
    }

    public static void sendConsoleMessage(String messageCode, String... parameter){
        //TODO: Console message (+parameter)
        LSCommands.plugin.getLogger().info(messageCode);
    }

    public static void sendSenderMessage(CommandSender sender, String messageCode, String... parameter){
        if(sender instanceof ConsoleCommandSender){
            sendConsoleMessage(messageCode, parameter);
        }else if(sender instanceof Player){
            LSPlayer p = LSPlayer.getPlayer((Player) sender);
            p.sendMsg(messageCode, parameter);
        }
    }
}
