package net.casheh.reportsystem.commands;

import net.casheh.reportsystem.ReportSystem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ReportsCommand implements CommandExecutor {

    private ReportSystem plugin;

    public ReportsCommand(ReportSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || sender.hasPermission("reports.checkhistory")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Invalid syntax!");
                sender.sendMessage(ChatColor.RED + "Correct usage: /reports <player>");
                return false;
            }

            Player offender = plugin.getServer().getPlayer(args[0]);

            if (offender == null) {
                sender.sendMessage(ChatColor.RED + "That is not a valid online player!");
                return false;
            }

            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.RED + "Reports for " + ChatColor.GOLD + offender.getName() + ":");

            if (plugin.SQL.isConnected() && plugin.getCfg().getUseSql()) {
                LinkedHashMap<String, String> reports = plugin.sqlMethods.getReports(offender);
                Set<String> reportReasons = reports.keySet();

                for (String reporter : reportReasons) {
                    sender.sendMessage(ChatColor.GOLD + offender.getName() + " was reported by " + ChatColor.GREEN +  reporter + ChatColor.GOLD + " for " + ChatColor.GREEN + "\"" + reports.get(reporter) + "\"");
                }
            } else {
                List<String> reports = plugin.getFileManager().getReportsConfig().getStringList(offender.getName());
                for (String s : reports) {
                    sender.sendMessage(ChatColor.GOLD + offender.getName() + " was reported for " + ChatColor.GREEN + "\"" + s + "\"");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action!");
        }
        return true;
    }
}
