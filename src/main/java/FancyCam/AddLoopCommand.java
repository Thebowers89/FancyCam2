package FancyCam;

import FancyCam.Utils.Loop;
import FancyCam.Utils.LoopHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static FancyCam.Utils.Utils.getHeading;
import static FancyCam.Utils.Utils.getPitch;

public class AddLoopCommand implements CommandExecutor {

    private final double interval = 0.01;

    // TODO refractor and cleanup later
    // addloop <bounce/normal> <id> <radius> [height] [speed] [bounce:frequency]

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.addloop") || player.isOp()) {
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
                }
                return true;
            }
            player.sendMessage("Not enough strings");
            return true;
        }
        return false;
    }

    public void bouncy2(String id, Location origin, double radius, double height, double speed, double frequency) {
        LoopHandler.addLoop(new Loop(origin, id, "bounce", radius, height, speed, frequency));
    }

    public void bouncy(String id, Location origin, double radius, double height, double speed, double frequency) {
        List<Location> list = new ArrayList<Location>();
        for (double i = 0; i < 360; i+=interval*speed) {
            double radians = i;
            double x = origin.getX() + radius * Math.cos(radians);
            double z = origin.getZ() - radius * Math.sin(radians);

            Location l = new Location(origin.getWorld(), x, origin.getY(), z);

            double number = Math.cos(frequency*i);

            l.setY(l.getY() + (number * height));
            l.setPitch(getPitch(l, origin));
            l.setYaw(getHeading(l, origin));

            list.add(l);
        }
        LoopHandler.addLoop(new Loop(id, list));
    }

    public void standard2(String id, Location origin, double radius, double height, double speed) {
        LoopHandler.addLoop(new Loop(origin, id, "normal", radius, height, speed, 0));
    }

    public void standard(String id, Location origin, double radius, double height, double speed) {
        List<Location> list = new ArrayList<Location>();
        for (double i = 0; i < 360; i+=interval*speed) {
            double radians = i;
            double x = origin.getX() + radius * Math.cos(radians);
            double z = origin.getZ() - radius * Math.sin(radians);

            Location l = new Location(origin.getWorld(), x, origin.getY() + height, z);
            l.setPitch(getPitch(l, origin));
            l.setYaw(getHeading(l, origin));

            list.add(l);
        }
        LoopHandler.addLoop(new Loop(id, list));
    }

}
