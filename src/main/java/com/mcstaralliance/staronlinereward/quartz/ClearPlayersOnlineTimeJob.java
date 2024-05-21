package com.mcstaralliance.staronlinereward.quartz;

import com.mcstaralliance.staronlinereward.util.ConfigManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;

public class ClearPlayersOnlineTimeJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            ConfigManager.clearPlayersOnlineTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

