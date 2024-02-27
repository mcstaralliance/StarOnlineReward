package com.mcstaralliance.staronlinereward.util;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RewardUtil {
    public static StarOnlineReward plugin = StarOnlineReward.getInstance();
    public static List<Integer> getRewards() {
        FileConfiguration config = plugin.getConfig();
        return config.getKeys(false).stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static void rewardPlayer(Player player, int stage) throws IOException {
        // stage 参数指定要发放第几阶段的奖励
        FileConfiguration config = plugin.getConfig();
        String stageName = String.valueOf(ConfigManager.getCondition(stage));
        List<String> commands = config.getStringList( stageName+ ".commands").stream()
                .map(command -> command.replace("%player%", player.getName()))
                .collect(Collectors.toList());
        for (String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
        // 记录今日已领取奖励次数
        ConfigManager.setRewardStage(player, stage);
        int time = ConfigManager.getCondition(stage);
        player.sendMessage("你已在线 " + time + " 分钟，奖励已自动发放。");
    }
}
