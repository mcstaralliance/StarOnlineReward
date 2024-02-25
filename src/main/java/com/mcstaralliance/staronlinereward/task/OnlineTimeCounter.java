package com.mcstaralliance.staronlinereward.task;

import com.mcstaralliance.staronlinereward.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnlineTimeCounter extends BukkitRunnable {
    // 应确保本任务每分钟执行一次。
    @Override
    public void run() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            int time = ConfigManager.getOnlineTime(player);
            try {
                ConfigManager.setOnlineTime(player, ++time);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
