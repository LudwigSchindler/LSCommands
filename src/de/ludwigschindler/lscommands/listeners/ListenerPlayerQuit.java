package de.ludwigschindler.lscommands.listeners;

import de.ludwigschindler.lscommands.main.LSPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerQuit implements Listener {

    public void onQuit(PlayerQuitEvent e){
        LSPlayer.leave(e.getPlayer());
    }
}
