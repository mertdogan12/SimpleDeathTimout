package at.dogan.simpleDeathTimeout.configs;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import at.dogan.simpleDeathTimeout.SimpleDeathTimeout;

/**
 * Time wen the ban ends
 */
public class BanEndConfig {
    private static File file = new File("plugins/simpleDeathTimeout/ban-end.yml");
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    /**
     * Bans the player for x days. x can be definded in the config.
     *
     * @param player
     */
    public static void banPlayer(final Player player) {
        config.set(player.getUniqueId().toString(),
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(Config.deathTime));
        DeathCountConfig.resetDeaths(player.getUniqueId().toString());

        try {
            config.save(file);
        } catch (IOException e) {
            sender.sendMessage(
                    SimpleDeathTimeout.PREFIX + "§cError while saveing the ban end config§f: \n" + e.getMessage());
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(SimpleDeathTimeout.getPlugin(SimpleDeathTimeout.class), 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                long mil = config.getLong(player.getUniqueId().toString()) - System.currentTimeMillis();
                int sec = (int) (mil / 1000) % 60;
                int min = (int) ((mil / (1000 * 60)) % 60);
                int hours = (int) ((mil / (1000 * 60 * 60)) % 24);
                int day = (int) (mil / (1000 * 60 * 60 * 24));

                player.kickPlayer(String.format("§c%s can join the Server again in %d:%d:%d:%d", player.getName(), day,
                        hours, min, sec));

                Bukkit.broadcastMessage(String.format("§c%s can join the Server again in %d:%d:%d:%d", player.getName(),
                        day, hours, min, sec));
            }
        }.runTaskLater(SimpleDeathTimeout.getPlugin(SimpleDeathTimeout.class), 30L);
    }

    /**
     * Unbans an player
     *
     * @param uuid
     */
    public static void unbanPlayer(String uuid) {
        config.set(uuid, 0);

        try {
            config.save(file);
        } catch (IOException e) {
            sender.sendMessage(
                    SimpleDeathTimeout.PREFIX + "§cError while saveing the ban end config§f: \n" + e.getMessage());
        }
    }

    /**
     * Gets the time wenn the player gets unbanned
     *
     * @param uuid
     */
    public static long unbanTime(String uuid) {
        if (!config.isSet(uuid))
            return 0;

        long unbanMillisec = config.getLong(uuid) - System.currentTimeMillis();

        return unbanMillisec;
    }

    /**
     * Loads the config file
     */
    public static void loadConfiguration() {
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();

                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cBan end §6config file is created");
            } catch (IOException e) {
                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cError while creating ban end config§f: \n" + e);
                return;
            }
        }

        sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cBan end §6config is loaded");
    }
}
