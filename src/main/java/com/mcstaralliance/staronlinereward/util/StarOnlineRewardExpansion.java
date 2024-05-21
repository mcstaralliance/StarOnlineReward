package com.mcstaralliance.staronlinereward.util;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StarOnlineRewardExpansion extends PlaceholderExpansion {
    private final StarOnlineReward plugin;

    public StarOnlineRewardExpansion(StarOnlineReward plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "sor";
    }

    @Override
    public @NotNull String getAuthor() {
        return "LDS_XiaoYe";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("time")) {
            return String.valueOf(ConfigManager.getOnlineTime(player.getPlayer()));
        }

        if (params.equalsIgnoreCase("stage")) {
            return String.valueOf(ConfigManager.getRewardStage(player.getPlayer()));
        }

        return null;
    }
}
