package com.mcstaralliance.staronlinereward.task;

import com.mcstaralliance.staronlinereward.util.ConfigManager;
import com.mcstaralliance.staronlinereward.util.RewardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckRewardCondition extends BukkitRunnable {
    @Override
    public void run() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            if (meetNextCondition(player)) {
                // 满足条件，则发奖励
                int stage = ConfigManager.getRewardStage(player);
                try {
                    RewardUtil.rewardPlayer(player, stage + 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean meetNextCondition(Player player) {
        int onlineTime = ConfigManager.getOnlineTime(player);
        int currentRewardStage = ConfigManager.getRewardStage(player);
        int condition = ConfigManager.getCondition(currentRewardStage + 1);

        return onlineTime >= condition;
    }
}