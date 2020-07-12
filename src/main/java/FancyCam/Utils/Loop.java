package FancyCam.Utils;

import FancyCam.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.UUID;

public class Loop implements Runnable {

    private String id;
    private List<Location> loop;
    private Player camera;
    private int i;
    private BukkitTask bt;

    public Loop(String id, List<Location> loop) {
        this.id = id;
        this.loop = loop;
    }

    public String getId() {
        return this.id;
    }

    public UUID getCamera() {
        return this.camera.getUniqueId();
    }

    public void startLoop(Player camera) {
        this.camera = camera;
        this.bt = Bukkit.getScheduler().runTaskTimer(MainClass.plugin, this, 0, 1);
    }

    public void removeCamera() {
        this.camera = null;
        if (this.bt != null) this.bt.cancel();
    }

    public void run() {
        if (camera == null) {
            this.bt.cancel();
        }
        if (camera != null && loop.size() > 0) {
            if (i == loop.size()) i = 0;
            camera.teleport(loop.get(i));
            i++;
        }
    }
}
