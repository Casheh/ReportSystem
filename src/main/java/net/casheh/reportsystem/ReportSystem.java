package net.casheh.reportsystem;

import net.casheh.reportsystem.commands.ReportCommand;
import net.casheh.reportsystem.commands.ReportsCommand;
import net.casheh.reportsystem.config.Config;
import net.casheh.reportsystem.db.MySQL;
import net.casheh.reportsystem.db.SQLMethods;
import net.casheh.reportsystem.util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ReportSystem extends JavaPlugin {

    public static ReportSystem plugin;

    public MySQL SQL;

    public SQLMethods sqlMethods;

    public Config config;

    public FileManager fileManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        plugin = this;
        SQL = new MySQL(this);
        sqlMethods = new SQLMethods(this);
        config = new Config(this);
        fileManager = new FileManager();

        if (config.getUseSql()) {
            connect();
        } else {
            fileManager.init();
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("Using SQL storage!");
            sqlMethods.createTable();
        }

        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("reports").setExecutor(new ReportsCommand(this));
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    private void connect() {
        try {
            SQL.connect();
        } catch (Exception e) {
            Bukkit.getLogger().info("Using YAML storage!");
        }
    }


    public FileManager getFileManager() { return this.fileManager; }

    public Config getCfg() { return this.config; }
}
