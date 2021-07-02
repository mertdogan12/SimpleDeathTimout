package at.dogan.simpleDeathTimeout.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import at.dogan.simpleDeathTimeout.configs.BanEndConfig;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        long unbanTime = BanEndConfig.unbanTime(uuid);

        if (unbanTime > 0) {
            int days = (int) unbanTime / 60 / 60 / 24;
            int hour = (int) unbanTime / 60 / 60 - days * 24;
            int sec = (int) unbanTime / 60 - hour * 60;

            player.kickPlayer(String.format("§cYou can join the server again in %d:%h:%s", days, hour, sec));
        }
    }
}
