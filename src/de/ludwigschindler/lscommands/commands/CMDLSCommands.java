package de.ludwigschindler.lscommands.commands;

import de.ludwigschindler.lscommands.main.LSGUI;
import de.ludwigschindler.lscommands.main.LSPlayer;
import de.ludwigschindler.lscommands.main.LSCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDLSCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        //No argument --> General Plugin information
        if (args.length == 0) {
            LSPlayer p = LSPlayer.senderToLSCPlayer(sender);
            p.sendMessage("§6[§bLS§aCommands§6]§3" + " §bLSCommands\n§7\u00A9 Ludwig Schindler 2019");


            /*1 Argument:
            - version
            -  re1oad
            - test
             */


        } else if (args.length == 1) {
            // TODO: Add parameter function: e. g. version


        } else {


            if (args[0].equalsIgnoreCase("test")) {
                String msg;
                sender.sendMessage("§7Test §b" + args[1]);
                switch (args[1]) {

                    case "lang":
                        if (sender instanceof Player) {
                            LSPlayer p = LSPlayer.getPlayer((Player) sender);
                            p.sendMsg(args[2], "TEST_PARAMETER");
                        } else {
                            sender.sendMessage("§4Language test currently not working for you!");
                        }
                        break;



                    case "config":
                        sender.sendMessage("§7Config test:\n§b" + LSCommands.config.getString("console-language"));
                        break;



                    case "cc":
                        for (int i = 150; i > 0; i--) {
                            sender.sendMessage("Count " + i);
                        }
                        break;

                    case "gui":
                        LSPlayer p = LSPlayer.getPlayer((Player) sender);
                        LSGUI gui = new LSGUI();
                        gui.createBigPageGUI(p.getLanguage(), p.getLanguage().getPrefix() + " GUI Test");
                        gui.setAction(new Runnable() {
                            @Override
                            public void run() {
                                
                            }
                        });
                        p.openGUI(gui);
                        break;


                    default:
                        sender.sendMessage("§4Please enter a correct test number");
                }
            } else {
                //TODO: Output wrong argument length message in respective language
            }

        }
        return true;
    }
}
