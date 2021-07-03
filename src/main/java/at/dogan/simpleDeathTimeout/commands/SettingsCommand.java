package at.dogan.simpleDeathTimeout.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;

import at.dogan.simpleDeathTimeout.SimpleDeathTimeout;
import at.dogan.simpleDeathTimeout.configs.BanEndConfig;
import at.dogan.simpleDeathTimeout.configs.DeathCountConfig;
import at.dogan.simpleDeathTimeout.uuid.PlayerUUID;

public class SettingsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("simpleDeathTimeout.settings")) {
            sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cYou have no permission to perform this command");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cThe command needs 2 arguments");
            return true;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                UUID uuid = PlayerUUID.getUUID(args[0]);
                if (uuid == null) {
                    sender.sendMessage(SimpleDeathTimeout.PREFIX + "§cPlayer does not exist");
                    return;
                }

                switch (args[1]) {
                case "unban" -> {
                    BanEndConfig.unbanPlayer(uuid.toString());
                    Bukkit.broadcastMessage(
                            String.format("%s§6%s§f has been unbanned", SimpleDeathTimeout.PREFIX, args[0]));
                }

                case "resetDeaths" -> {
                    DeathCountConfig.resetDeaths(uuid.toString());
                    Bukkit.broadcastMessage(
                            String.format("%s§6%s§f deaths has been reseted", SimpleDeathTimeout.PREFIX, args[0]));
                }

                default -> {
                    sender.sendMessage(SimpleDeathTimeout.PREFIX + "Use: §c/sdc <playername> <option>");
                }
                }
            }
        }.runTaskAsynchronously(SimpleDeathTimeout.getPlugin(SimpleDeathTimeout.class));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args.length) {
        case 1 -> {
            return new LinkedList<String>() {
                {
                    for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                        add(player.getName());
                    }
                }
            };
        }

        case 2 -> {
            return new LinkedList<String>() {
                {
                    add("unban");
                    add("resetDeaths");
                }
            };
        }

        default -> {
            return new LinkedList<String>();
        }
        }
    }
}
