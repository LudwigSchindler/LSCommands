package de.ludwigschindler.lscommands.listeners;

import de.ludwigschindler.lscommands.main.LSPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerPlayerChat implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        LSPlayer p = LSPlayer.getPlayer(e.getPlayer());

                p.update();

    }

}
