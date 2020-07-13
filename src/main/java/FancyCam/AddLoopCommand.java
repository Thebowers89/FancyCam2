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

    private final double interval = 0.01;

    // addloop <bounce/normal> <id> <radius> [height] [speed] [bounce:frequency]

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.addloop") || player.isOp()) {
                if (strings.length == 0) {
                    return false;
                }
                if (strings[0].equalsIgnoreCase("normal")) {
                    boolean success = true;
                    double height = 0;
                    double radius = Double.parseDouble(strings[2]);
                    double speed = 1;
                    String id = strings[1];
                    switch (strings.length) {
                        case 5:
                            height = Double.parseDouble(strings[3]);
                            speed = Double.parseDouble(strings[4]);
                            standard2(id, player.getLocation(), radius, height, speed);
                            break;
                        case 4:
                            height = Double.parseDouble(strings[3]);
                            standard2(id, player.getLocation(), radius, height, speed);
                            break;
                        case 3:
                            standard2(id, player.getLocation(), radius, height, speed);
                            break;
                        default:
                            player.sendMessage("Invalid Arguments");
                            success = false;
                    }
                    if (success) {
                        player.sendMessage("Created a loop with stats:\n  ID: " + strings[1]
                                + "\n  Type: " + strings[0]
                                + "\n  Radius: " + radius
                                + "\n  Height: " + height
                                + "\n  Speed: " + speed);
                    }
                } else if (strings[0].equalsIgnoreCase("bounce")) {
                    boolean success = true;
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
                            bouncy2(id, player.getLocation(), radius, height, speed, frequency);
                            break;
                        case 5:
                            height = Double.parseDouble(strings[3]);
                            speed = Double.parseDouble(strings[4]);
                            bouncy2(id, player.getLocation(), radius, height, speed, frequency);
                            break;
                        case 4:
                            height = Double.parseDouble(strings[3]);
                            bouncy2(id, player.getLocation(), radius, height, speed, frequency);
                            break;
                        case 3:
                            bouncy2(id, player.getLocation(), radius, height, speed, frequency);
                            break;
                        default:
                            player.sendMessage("Invalid Arguments");
                            success = false;
                    }
                    if (success) {
                        player.sendMessage("Created a loop with stats:\n  ID: " + strings[1]
                                + "\n  Type: " + strings[0]
                                + "\n  Radius: " + radius
                                + "\n  Height: " + height
                                + "\n  Speed: " + speed
                                + "\n  Frequency: " + frequency);
                    }
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

    public void bouncy2(String id, Location origin, double radius, double height, double speed, double frequency) {
        LoopHandler.addLoop(new Loop(origin, id, "bounce", radius, height, speed, frequency));
    }

    public void standard2(String id, Location origin, double radius, double height, double speed) {
        LoopHandler.addLoop(new Loop(origin, id, "normal", radius, height, speed, 0));
    }
}
