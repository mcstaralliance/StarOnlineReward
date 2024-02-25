package com.mcstaralliance.staronlinereward;

import com.mcstaralliance.staronlinereward.task.CheckRewardCondition;
import com.mcstaralliance.staronlinereward.task.ClearDailyDataTask;
import com.mcstaralliance.staronlinereward.task.OnlineTimeCounter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.LocalTime;

public final class StarOnlineReward extends JavaPlugin {

    private static StarOnlineReward instance;

    public static StarOnlineReward getInstance() {
        return instance;
    }

    public long getTicksUtilMidnight() {
        LocalTime now = LocalTime.now();
        LocalTime midnight = LocalTime.MIDNIGHT;
        // 计算当前时间与午夜时间之间的持续时间
        Duration durationUntilMidnight = Duration.between(now, midnight);
        // 获取持续时间的秒数
        long secondsUntilMidnight = durationUntilMidnight.getSeconds();
        // 将秒数转换为游戏刻度（20刻/秒）
        return secondsUntilMidnight * 20;
    }
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        BukkitRunnable clearDailyDataTask = new ClearDailyDataTask();
        BukkitRunnable onlineTimeCounter = new OnlineTimeCounter();
        BukkitRunnable checkRewardCondition = new CheckRewardCondition();
        // 使用Bukkit调度器安排一个重复执行任务，在距离午夜的秒数后执行一次，并且每隔一天执行一次
        // 间隔一天（20刻/秒 * 60秒 * 60分 * 24小时）
        clearDailyDataTask.runTaskTimerAsynchronously(this, getTicksUtilMidnight(), 20 * 60 * 60 * 24);
        onlineTimeCounter.runTaskTimerAsynchronously(this, 0, 60L);
        checkRewardCondition.runTaskTimerAsynchronously(this, 0, 60L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
