package at.dogan.simpleDeathTimeout.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import at.dogan.simpleDeathTimeout.SimpleDeathTimeout;

/**
 * Death count from {@link org.bukkit.entity.Player}s
 */
public class DeathCountConfig {
    private static File file = new File("plugins/simpleDeathTimeout/deaths.yml");
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private static ConsoleCommandSender sender = Bukkit.getConsoleSender();

    /**
     * Gets the deaths of an {@link org.bukkit.entity.Player}
     *
     * @param uuid
     * @return
     */
    public static int getDeaths(String uuid) {
        if (!config.isSet(uuid)) {
            config.set(uuid, 0);

            try {
                config.save(file);
            } catch (IOException e) {
                sender.sendMessage(
                        SimpleDeathTimeout.PREFIX + "§cError while saveing the deaths config§f: \n" + e.getMessage());
                return 3;
            }
        }

        return config.getInt(uuid);
    }

    /**
     * Adds a Death
     *
     * @param uuid
     */
    public static void addDeath(String uuid) {
        if (!config.isSet(uuid)) {
            config.set(uuid, 1);
        } else
            config.set(uuid, config.getInt(uuid) + 1);

        try {
            config.save(file);
        } catch (IOException e) {
            sender.sendMessage(
                    SimpleDeathTimeout.PREFIX + "§cError while saveing the deaths config§f: \n" + e.getMessage());
        }
    }

    /**
     * Resets the Deaths of an {@link org.bukkit.entity.Player}
     *
     * @param uuid
     */
    public static void resetDeaths(String uuid) {
        config.set(uuid, 0);

        try {
            config.save(file);
        } catch (IOException e) {
            sender.sendMessage(
                    SimpleDeathTimeout.PREFIX + "§cError while saveing the deaths config§f: \n" + e.getMessage());
        }
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

                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cDeaths §6config file is created");
            } catch (IOException e) {
                sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cError while creating deaths config§f: \n" + e);
                return;
            }
        }

        sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cDeaths §6config is loaded");
    }
}
