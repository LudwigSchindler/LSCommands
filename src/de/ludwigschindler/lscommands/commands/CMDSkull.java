package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSPlayer;
import de.ludwigschindler.lscommands.main.LSCommands;
import de.ludwigschindler.lscommands.main.LSUtil;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CMDSkull implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("skull")){
            if(sender instanceof  Player){
                LSPlayer p = LSPlayer.getPlayer((Player) sender);

                if(p.hasPermission("lscommands.command.skull")){
                    if(args.length == 0){
                        p.getInventory().addItem(LSUtil.getCustomSkull(p.getName()));
                    }else if(args.length == 1){
                        p.getInventory().addItem(LSUtil.getCustomSkull(args[0]));
                    }
                }
            }
        }

        return true;
    }
}