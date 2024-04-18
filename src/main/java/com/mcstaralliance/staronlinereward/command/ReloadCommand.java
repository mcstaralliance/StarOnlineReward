package com.mcstaralliance.staronlinereward.command;

import com.mcstaralliance.staronlinereward.StarOnlineReward;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    StarOnlineReward plugin = StarOnlineReward.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "你没有执行此命令的权限。");
            return false;
        }
        switch (args[0]) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "插件已重载。");
                break;
            default:
                break;
        }
        return true;
    }
}
