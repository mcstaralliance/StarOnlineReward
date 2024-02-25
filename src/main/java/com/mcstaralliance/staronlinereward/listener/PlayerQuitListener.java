package com.mcstaralliance.staronlinereward.listener;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;


public class PlayerQuitListener implements Listener {

    public StarOnlineReward plugin = StarOnlineReward.getInstance();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }
}
