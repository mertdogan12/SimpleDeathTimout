package at.dogan.simpleDeathTimeout.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import at.dogan.simpleDeathTimeout.configs.BanEndConfig;
import at.dogan.simpleDeathTimeout.configs.DeathCountConfig;

public class PlayerDeathListener implements Listener {
    @EventHandler
    private void on(PlayerDeathEvent event) {
        final Player player = (Player) event.getEntity();
        String uuid = player.getUniqueId().toString();

        DeathCountConfig.addDeath(uuid);

        if (DeathCountConfig.getDeaths(uuid) > 2) {
            BanEndConfig.banPlayer(player);
        }
    }
}
