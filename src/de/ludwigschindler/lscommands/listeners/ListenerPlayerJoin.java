package de.ludwigschindler.lscommands.listeners;

import de.ludwigschindler.lscommands.main.LSCommands;
import de.ludwigschindler.lscommands.main.LSPlayer;
import de.ludwigschindler.lscommands.main.LSUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerPlayerJoin implements Listener{

    @EventHandler
    public static void onJoin(PlayerJoinEvent e){

        LSPlayer p = LSPlayer.join(e.getPlayer());

        e.setJoinMessage(null);
        LSUtil.broadcastMessage("events.join.playerJoin", p.getConfigName());
    }
}
