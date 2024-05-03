package com.mcstaralliance.staronlinereward.util;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家数据文件结构：
 * online_time 存当日在线时间
 * last_login 上次登录时间
 * reward_stage 今日已领取奖励个数
 */
public class ConfigManager {
    private static final StarOnlineReward plugin = StarOnlineReward.getInstance();

    public static FileConfiguration getPlayerConfig(Player player) {
        return YamlConfiguration.loadConfiguration(new File(getDataDir(), player.getName() + ".yml"));
    }

    public static File getDataDir() {
        return new File(plugin.getDataFolder(), "data");
    }
    public static void setRewardStage(Player player, int stage) throws IOException {
        FileConfiguration data = getPlayerConfig(player);
        data.set("reward_stage", stage);
        data.save(getPlayerFile(player));
    }
    public static int getRewardStage(Player player) {
        FileConfiguration data = getPlayerConfig(player);
        return data.getInt("reward_stage");
    }
    public static void clearPlayersOnlineTime() throws IOException {
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : onlinePlayers) {
            clearOnlineTime(player);
        }
    }

    public static void clearOnlineTime(Player player) throws IOException {
        FileConfiguration data = getPlayerConfig(player);
        data.set("online_time", 0);
        data.set("reward_stage", 0);
        data.save(getPlayerFile(player));
    }

    public static int getOnlineTime(Player player) {
        FileConfiguration data = getPlayerConfig(player);
        return data.getInt("online_time");
    }

    public static void setOnlineTime(Player player, int time) throws IOException {
        FileConfiguration data = getPlayerConfig(player);
        data.set("online_time", time);
        data.save(getPlayerFile(player));
    }

    public static void createPlayerConfig(Player player) {
        createDataDir();

        File file = new File(getDataDir(), player.getName() + ".yml");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    plugin.getLogger().warning(player.getName() + ".yml was not created successfully.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createDataDir() {
        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs()) {
                plugin.getLogger().warning("Data dir was not created successfully.");
            }
        }
    }
    public static File getPlayerFile(Player player) {
        return new File(ConfigManager.getDataDir(), player.getName() + ".yml");
    }

    public static void setLastLoginTime(Player player) throws IOException {
        FileConfiguration data = ConfigManager.getPlayerConfig(player);
        LocalDate today = LocalDate.now();
        data.set("last_login", today.toEpochDay());
        data.save(getPlayerFile(player));
    }

    public static int getCondition(int stage) {
        List<Integer> rewards = RewardUtil.getRewards();
        if (stage > rewards.size()) {
            return Integer.MAX_VALUE;
        }
        int stageIndex = stage - 1; // stage - 1 是 stage 在列表里的索引
        return rewards.get(stageIndex);
    }
}
