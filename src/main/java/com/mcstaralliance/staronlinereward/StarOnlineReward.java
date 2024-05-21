package com.mcstaralliance.staronlinereward;

import com.mcstaralliance.staronlinereward.command.ReloadCommand;
import com.mcstaralliance.staronlinereward.listener.PlayerJoinListener;
import com.mcstaralliance.staronlinereward.quartz.ClearPlayersOnlineTimeJob;
import com.mcstaralliance.staronlinereward.task.CheckRewardCondition;
import com.mcstaralliance.staronlinereward.task.OnlineTimeCounter;
import com.mcstaralliance.staronlinereward.util.StarOnlineRewardExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public final class StarOnlineReward extends JavaPlugin {

    public static int onlineTimeCounterTaskId;
    public static int checkRewardConditionTaskId;
    private static StarOnlineReward instance;

    public static StarOnlineReward getInstance() {
        return instance;
    }
    public void registerPlaceholderExpansion() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new StarOnlineRewardExpansion(this).register();
        }
    }
    public void launchQuartzScheduler() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(ClearPlayersOnlineTimeJob.class)
                    .withIdentity("clearPlayersOnlineTimeJob", "group1")
                    .build();
            // 创建触发器，使用 cron 表达式设置为每天凌晨 0 点
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("dailyTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerPlaceholderExpansion();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        BukkitRunnable onlineTimeCounter = new OnlineTimeCounter();
        BukkitRunnable checkRewardCondition = new CheckRewardCondition();

        onlineTimeCounter.runTaskTimer(this, 0, 1200L);
        checkRewardCondition.runTaskTimer(this, 0, 1200L);
        launchQuartzScheduler();
        onlineTimeCounterTaskId = onlineTimeCounter.getTaskId();
        checkRewardConditionTaskId = checkRewardCondition.getTaskId();
        Bukkit.getPluginCommand("sor").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        cancelTasks();
    }

    public void cancelTasks() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTask(onlineTimeCounterTaskId);
        scheduler.cancelTask(checkRewardConditionTaskId);
    }
}
