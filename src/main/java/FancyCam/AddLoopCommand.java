package FancyCam;

import FancyCam.Utils.Loop;
import FancyCam.Utils.LoopHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddLoopCommand implements CommandExecutor {

    //  addloop <bounce/normal> <id> <radius> [height] [speed] [bounce:frequency]

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.addloop") || player.isOp()) {
                if (strings.length == 0) {
                    return false;
                }
                if (strings[0].equalsIgnoreCase("normal")) {
                    double height = 0;
                    double radius = Double.parseDouble(strings[2]);
                    double speed = 1;
                    String id = strings[1];
                    switch (strings.length) {
                        case 5:
                            height = Double.parseDouble(strings[3]);
                            speed = Double.parseDouble(strings[4]);
                            if (!standard2(id, player.getLocation(), radius, height, speed)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        case 4:
                            height = Double.parseDouble(strings[3]);
                            if (!standard2(id, player.getLocation(), radius, height, speed)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        case 3:
                            if (!standard2(id, player.getLocation(), radius, height, speed)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        default:
                            player.sendMessage(ChatColor.RED + "Invalid Arguments");
                            return false;
                    }
                    String message = String.format("&6Created a loop with the stats:\n  ID: &a%s\n  &6Type: &a%s\n  &6Radius:  &a%s\n  &6Height:  &a%s\n  &6Speed: &a%s", strings[1], strings[0], radius, height, speed);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                } else if (strings[0].equalsIgnoreCase("bounce")) {
                    double height = 1;
                    double radius = Double.parseDouble(strings[2]);
                    double speed = 1;
                    double frequency = 5;
                    String id = strings[1];
                    switch (strings.length) {
                        case 6:
                            frequency = Double.parseDouble(strings[5]);
                            height = Double.parseDouble(strings[3]);
                            speed = Double.parseDouble(strings[4]);
                            if (!bouncy2(id, player.getLocation(), radius, height, speed, frequency)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        case 5:
                            height = Double.parseDouble(strings[3]);
                            speed = Double.parseDouble(strings[4]);
                            if (!bouncy2(id, player.getLocation(), radius, height, speed, frequency)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        case 4:
                            height = Double.parseDouble(strings[3]);
                            if (!bouncy2(id, player.getLocation(), radius, height, speed, frequency)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        case 3:
                            if (!bouncy2(id, player.getLocation(), radius, height, speed, frequency)) {
                                player.sendMessage(ChatColor.RED + "A loop with that ID already exists!");
                                return true;
                            }
                            break;
                        default:
                            player.sendMessage(ChatColor.RED + "Invalid Arguments");
                            return false;
                    }
                    String message = String.format("&6Created a loop with the stats:\n  ID: &a%s\n  &6Type: &a%s\n  &6Radius:  &a%s\n  &6Height:  &a%s\n  &6Speed: &a%s\n  &6Frequency: &a%s", strings[1], strings[0], radius, height, speed, frequency);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return true;
                } else {
                    player.sendMessage(String.format(ChatColor.RED + "%s is not a valid type, try 'normal' or 'bounce'", strings[0]));
                    return true;
                }
            }
            player.sendMessage("You do not have permission to execute this command!");
            return true;
        }
        return false;
    }

    public boolean bouncy2(String id, Location origin, double radius, double height, double speed, double frequency) {
        return LoopHandler.addLoop(new Loop(origin, id, "bounce", radius, height, speed, frequency));
    }

    public boolean standard2(String id, Location origin, double radius, double height, double speed) {
        return LoopHandler.addLoop(new Loop(origin, id, "normal", radius, height, speed, 0));
    }
}
