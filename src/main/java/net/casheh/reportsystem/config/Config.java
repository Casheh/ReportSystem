package net.casheh.reportsystem.config;

import net.casheh.reportsystem.ReportSystem;

public class Config {

    private ReportSystem plugin;

    private boolean useSql;

    public Config(ReportSystem plugin) {
        this.plugin = plugin;
        assign();
    }

    public void assign() {
        plugin.reloadConfig();
        this.useSql = plugin.getConfig().getBoolean("use-sql");
    }

    public boolean getUseSql() {
        return this.useSql;
    }


}
