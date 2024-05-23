package com.mcstaralliance.staronlinereward.listener;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import com.mcstaralliance.staronlinereward.util.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.IOException;
import java.time.LocalDate;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        ConfigManager.createPlayerConfig(player);
        if (isFirstJoinToday(player)) {
            ConfigManager.clearOnlineTime(player);
        }
        ConfigManager.setLastLoginTime(player);
    }

    public boolean isFirstJoinToday(Player player) {
        long lastLoginTime = ConfigManager.getPlayerConfig(player).getLong("last_login");
        LocalDate lastLoginDate = LocalDate.ofEpochDay(lastLoginTime);
        LocalDate today = LocalDate.now();
        return lastLoginDate.isBefore(today);
    }
}
