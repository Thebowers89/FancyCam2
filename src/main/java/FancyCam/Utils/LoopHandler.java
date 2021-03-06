package FancyCam.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static FancyCam.MainClass.saveFile;

public class LoopHandler {

    public static List<Loop> loops = new ArrayList<Loop>();
    public static Map<UUID, Loop> cameras = new HashMap<UUID, Loop>();

    public static boolean addLoop(Loop loop) {
        if (!doesExist(loop.getId())) {
            loops.add(loop);
            return true;
        } else {
            return false;
        }
    }

    public static void saveLoops() {
        saveFile.clear();
        for (Loop loop : loops) {
            loop.saveLoop();
        }
    }

    public static void loadLoops() {
        for (String key : saveFile.singleLayerKeySet("Loops")) {
            saveFile.setPathPrefix("Loops." + key);
            Location origin = new Location(
                    Bukkit.getWorld(saveFile.getString("origin.world")),
                    saveFile.getDouble("origin.x"),
                    saveFile.getDouble("origin.y"),
                    saveFile.getDouble("origin.z")
            );
            addLoop(new Loop(origin,
                    key,
                    saveFile.getString("type"),
                    saveFile.getDouble("radius"),
                    saveFile.getDouble("height"),
                    saveFile.getDouble("speed"),
                    saveFile.getDouble("frequency")
            ));
        }
    }

    public static void removeLoop(String id) {
        if (doesExist(id)) {
            Loop l = null;
            for (Loop loop : loops) {
                if (loop.getId().equalsIgnoreCase(id)) {
                    l = loop;
                }
            }
            l.removeCamera();
            loops.remove(l);
        }
    }

    public static void startLoop(String id, Player player) {
        Loop l = getLoop(id);
        if (cameras.containsKey(player.getUniqueId())) {
            cameras.get(player.getUniqueId()).removeCamera();
        }
        if (l != null) {
            cameras.put(player.getUniqueId(), l);
            l.startLoop2(player);
        }
    }

    public static void stopLoop(Player player) {
        if (cameras.containsKey(player.getUniqueId())) {
            cameras.get(player.getUniqueId()).removeCamera();
            cameras.get(player.getUniqueId()).stop();
            player.sendMessage("Stopping loop...");
        } else {
            player.sendMessage(ChatColor.RED + "Loop does not exist!");
        }
    }

    public static boolean doesExist(String id) {
        for (Loop l : loops) {
            if (l.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static String getLoops() {
        String out = "&6List of current loops:\n";
        for (Loop l : loops) {
            out += "  &a" + l.getId() + "\n";
        }
        return out;
    }

    public static Loop getLoop(UUID camera) {
        for (Loop l : loops) {
            if (l.getCamera().equals(camera)) return l;
        }
        return null;
    }

    public static Loop getLoop(String id) {
        if (doesExist(id)) {
            for (Loop l : loops) {
                if (l.getId().equalsIgnoreCase(id)) return l;
            }
        }
        return null;
    }

}
