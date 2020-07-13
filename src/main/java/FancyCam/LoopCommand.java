package FancyCam;

import FancyCam.Utils.LoopHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoopCommand implements CommandExecutor {

    // loop <stop/list>
    // loop <start> <id>

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.loop") || player.isOp()) {
                if (strings.length == 2) {
                    String id = strings[1];
                    if (strings[0].equalsIgnoreCase("start")) {
                        LoopHandler.startLoop(id, player);
                        player.sendMessage("Starting loop...");
                        return true;
                    } else if (strings[0].equalsIgnoreCase("remove")) {
                        LoopHandler.removeLoop(id);
                        player.sendMessage("Removed loop with id of: " + id);
                        return true;
                    }
                } else if (strings.length == 1) {
                    if (strings[0].equalsIgnoreCase("stop")) {
                        LoopHandler.stopLoop(player);
                        return true;
                    } else if (strings[0].equalsIgnoreCase("list")) {
                        player.sendMessage(LoopHandler.getLoops());
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
