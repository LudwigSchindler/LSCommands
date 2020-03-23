package de.ludwigschindler.lscommands.listeners;

import de.ludwigschindler.lscommands.main.LSPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class ListenerPlayerKick implements Listener {

    public void onKick(PlayerKickEvent e){
        LSPlayer.leave(e.getPlayer());
    }
}
