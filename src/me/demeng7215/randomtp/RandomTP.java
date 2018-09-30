package me.demeng7215.randomtp;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomTP extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("randomteleport").setExecutor(this);
        Bukkit.getConsoleSender().sendMessage("§aRandomTP v" + getDescription().getVersion() + " by Demeng7215 " +
                "has been enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§cRandomTP v" + getDescription().getVersion() + " by Demeng7215 " +
                "has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("randomteleport")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        getConfig().getString("console")));
                return false;
            }

            Player p = (Player) sender;
            Location corner1 = new Location(Bukkit.getWorld(getConfig().getString("normal_world")),
                    getConfig().getDouble("corner1.x"),
                    getConfig().getDouble("corner1.y"),
                    getConfig().getDouble("corner1.z"));
            Location corner2 = new Location(Bukkit.getWorld(getConfig().getString("normal_world")),
                    getConfig().getDouble("corner2.x"),
                    getConfig().getDouble("corner2.y"),
                    getConfig().getDouble("corner2.z"));

            Location tpLocation = randomLocation(corner1, corner2);

            if (p.getLocation().getWorld().getName().equalsIgnoreCase(getConfig().getString("void_world")) ||
                    p.getLocation().getWorld().getName().equalsIgnoreCase(getConfig().getString("normal_world"))) {

                tpLocation = tpLocation.getWorld().getHighestBlockAt(tpLocation).getLocation();

                p.teleport(tpLocation);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        getConfig().getString("teleported").replace("{x}", String.valueOf(tpLocation.getBlockX()))
                                .replace("{y}", String.valueOf(tpLocation.getBlockY()))
                                .replace("{z}", String.valueOf(tpLocation.getBlockZ()))));
                return true;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    getConfig().getString("fail_teleport")));
            return false;
        }
        return false;
    }

    private Location randomLocation(Location min, Location max) {
        Location range = new Location(min.getWorld(), Math.abs(max.getX() - min.getX()), min.getY(), Math.abs(max.getZ() - min.getZ()));
        return new Location(min.getWorld(), (Math.random() * range.getX()) + (min.getX() <= max.getX() ? min.getX() : max.getX()), range.getY(), (Math.random() * range.getZ()) + (min.getZ() <= max.getZ() ? min.getZ() : max.getZ()));
    }
}