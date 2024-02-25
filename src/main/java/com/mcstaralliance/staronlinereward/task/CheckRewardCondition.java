package com.mcstaralliance.staronlinereward.task;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import com.mcstaralliance.staronlinereward.util.ConfigManager;
import com.mcstaralliance.staronlinereward.util.RewardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckRewardCondition extends BukkitRunnable {
    public StarOnlineReward plugin = StarOnlineReward.getInstance();
    @Override
    public void run() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            if (meetNextCondition(player)) {
                int stage = ConfigManager.getRewardStage(player);
                try {
                    RewardUtil.rewardPlayer(player, ++stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean meetNextCondition(Player player) {
        int onlineTime = ConfigManager.getPlayerConfig(player).getInt("online_time");
        int rewardStage = ConfigManager.getPlayerConfig(player).getInt("reward_stage");
        int condition = RewardUtil.getRewards().iterator().next();
        for (int i = 0; i < rewardStage; i++) {
            condition = RewardUtil.getRewards().iterator().next();
        }
        return onlineTime >= condition;
    }

}
