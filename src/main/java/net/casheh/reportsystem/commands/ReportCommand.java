package net.casheh.reportsystem.commands;

import net.casheh.reportsystem.ReportSystem;
import net.casheh.reportsystem.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReportCommand implements CommandExecutor {

    private ReportSystem plugin;

    public ReportCommand(ReportSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Invalid syntax!");
            sender.sendMessage(ChatColor.RED + "Correct usage: /report <player> <reason>");
            return false;
        }

        Player offender = ReportSystem.plugin.getServer().getPlayer(args[0]);
        String reportReason;

        if (offender == null) {
            sender.sendMessage(ChatColor.RED + "That is not a valid online player!");
            return false;
        }

        if (offender.getName().equals(sender.getName())) {
            sender.sendMessage(ChatColor.RED + "You cannot report yourself!");
            return false;
        }

        reportReason = Utils.convert(args);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("reports.view") && !p.getName().equals(sender.getName())) {
                p.sendMessage(ChatColor.GREEN + sender.getName() + ChatColor.GOLD + " has reported " + ChatColor.RED + offender.getName() +
                        ChatColor.GOLD + " for " + ChatColor.RED + reportReason);
            }
        }

        sender.sendMessage(ChatColor.GREEN + "You have reported " + ChatColor.RED + offender.getName() + ChatColor.GREEN + " for " + ChatColor.RED + reportReason);

        if (plugin.getCfg().getUseSql() && plugin.SQL.isConnected()) {
            plugin.sqlMethods.reportPlayer(offender, sender.getName(), reportReason);
        } else {
            FileConfiguration reportsFile = plugin.fileManager.getReportsConfig();
            List<String> reportsForUser = (reportsFile.getStringList(offender.getName()) == null) ? new ArrayList<>() : reportsFile.getStringList(offender.getName());
            reportsForUser.add(reportReason);
            plugin.getFileManager().getReportsConfig().set(offender.getName(), reportsForUser);
            plugin.getFileManager().save();
        }


        return true;
    }
}
