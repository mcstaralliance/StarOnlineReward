package com.mcstaralliance.staronlinereward.task;

import com.mcstaralliance.staronlinereward.util.ConfigManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;


public class ClearDailyDataTask extends BukkitRunnable {
    @Override
    public void run() {
        try {
            ConfigManager.clearPlayersOnlineTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
