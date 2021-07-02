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
            int sec = (int) (unbanTime / 1000) % 60;
            int min = (int) ((unbanTime / (1000 * 60)) % 60);
            int hours = (int) ((unbanTime / (1000 * 60 * 60)) % 24);
            int days = (int) (unbanTime / (1000 * 60 * 60 * 24));

            player.kickPlayer(String.format("§c%s can join the Server again in %d:%d:%d:%d", player.getName(), days,
                    hours, min, sec));
        }
    }
}
