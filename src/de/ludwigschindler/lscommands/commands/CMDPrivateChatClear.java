package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDPrivateChatClear implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {


            if(args.length == 0){
                if(sender instanceof Player){

                    LSPlayer p = LSPlayer.senderToLSCPlayer(sender);

                    if(p.hasPermission("lscommands.commands.privatechatclear")){

                    }
                }
            }


        return true;
    }
}
