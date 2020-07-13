package FancyCam;

import FancyCam.Utils.Loop;
import FancyCam.Utils.LoopHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditLoopCommand implements CommandExecutor {
    // editloop <id> <stat> <newvalue>
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.editloop") || player.isOp()) {
                if (strings.length == 3) {
                    String id = strings[0];
                    String stat = strings[1];
                    String value = strings[2];
                    if (LoopHandler.doesExist(id)) {
                        Loop loop = LoopHandler.getLoop(id);
                        double newValue;
                        try {
                            newValue = Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            player.sendMessage(ChatColor.RED + "The given value must be a number, please double check your command!");
                            return true;
                        }
                        if (stat.equalsIgnoreCase("radius")) {
                            double old = loop.getRadius();
                            player.sendMessage(String.format("Loop: '%s' radius %s -> %s", id, old, newValue));
                            loop.setRadius(newValue);
                            return true;
                        } else if (stat.equalsIgnoreCase("height")) {
                            double old = loop.getHeight();
                            player.sendMessage(String.format("Loop: '%s' height %s -> %s", id, old, newValue));
                            loop.setHeight(newValue);
                            return true;
                        } else if (stat.equalsIgnoreCase("speed")) {
                            double old = loop.getSpeed();
                            player.sendMessage(String.format("Loop: '%s' speed %s -> %s", id, old, newValue));
                            loop.setSpeed(newValue);
                            return true;
                        } else if (stat.equalsIgnoreCase("frequency") || stat.equalsIgnoreCase("freq")) {
                            double old = loop.getFrequency();
                            player.sendMessage(String.format("Loop: '%s' frequency %s -> %s", id, old, newValue));
                            loop.setFrequency(newValue);
                            return true;
                        } else {
                            player.sendMessage(ChatColor.RED + "You have not given a valid stat to change, please check your spelling!");
                            return true;
                        }
                    } else {
                        player.sendMessage("That loop does not exist, please check your spelling!");
                        return true;
                    }
                } else if (strings.length == 2) {
                    String id = strings[0];
                    if (LoopHandler.doesExist(id)) {
                        if (strings[1].equalsIgnoreCase("origin")) {
                            LoopHandler.getLoop(id).setOrigin(player.getLocation());
                            player.sendMessage(String.format("Loop: '%s' center has been updated to your current position", id));
                            return true;
                        }
                    } else {
                        player.sendMessage("That loop does not exist, please check your spelling!");
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "Invalid arguments!");
                return false;
            }
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
            return true;
        }
        return false;
    }
}
