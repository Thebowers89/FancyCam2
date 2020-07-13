package FancyCam.Utils;

import FancyCam.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static FancyCam.Utils.Utils.getHeading;
import static FancyCam.Utils.Utils.getPitch;

public class Loop implements Runnable {

    private String id;
    private List<Location> loop;
    private Player camera;
    private int i;
    private BukkitTask bt;

    private String type;
    private Location origin;
    private double radius;
    private double height;
    private double speed;
    private double frequency;

    private double interval = 0.01;

    public Loop(Location origin, String id, String type, double radius, double height, double speed, double frequency) {
        this.origin = origin;
        this.id = id;
        this.type = type;
        this.radius = radius;
        this.height = height;
        this.speed = speed;
        this.frequency = frequency;
    }

    public String getId() {
        return this.id;
    }

    public UUID getCamera() {
        return this.camera.getUniqueId();
    }

    public void startLoop2(Player camera) {
        List<Location> list = new ArrayList<Location>();
        for (double i = 0; i < 360; i+=interval*speed) {
            double x = origin.getX() + radius * Math.cos(i);
            double z = origin.getZ() - radius * Math.sin(i);

            Location l = new Location(origin.getWorld(), x, origin.getY(), z);

            double number = Math.cos(frequency * i);
            l.setY(l.getY() + (number * height));
            l.setPitch(getPitch(l, origin));
            l.setYaw(getHeading(l, origin));

            list.add(l);
        }
        this.camera = camera;
        this.loop = list;
        this.bt = Bukkit.getScheduler().runTaskTimer(MainClass.plugin, this, 0, 1);
    }

    public void stop() {
        this.camera = null;
        this.loop = null;
        if (this.bt != null) this.bt.cancel();
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

    public Location getOrigin() {
        return this.origin;
    }

    public void setOrigin(Location newOrigin) {
        this.origin = newOrigin;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double newRadius) {
        this.radius = newRadius;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double newHeight) {
        this.height = newHeight;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    public double getFrequency() {
        return this.frequency;
    }

    public void setFrequency(double newFreq) {
        this.frequency = newFreq;
    }

}