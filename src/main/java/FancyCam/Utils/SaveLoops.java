package FancyCam.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveLoops implements  CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("FancyCam.saveloops") || player.isOp()) {
                LoopHandler.saveLoops();
                player.sendMessage("Saving loops");
            }
        }
        return false;
    }
}