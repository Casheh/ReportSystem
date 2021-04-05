package net.casheh.reportsystem.util;


import net.casheh.reportsystem.ReportSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private File reportsFile;

    private FileConfiguration reportsConfig;

    public void init() {
        reportsFile = new File(ReportSystem.plugin.getDataFolder(), "reports.yml");

        if (!reportsFile.exists()) {
            try {
                reportsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reportsConfig = YamlConfiguration.loadConfiguration(reportsFile);
    }

    public FileConfiguration getReportsConfig() {
        return this.reportsConfig;
    }

    public void save() {
        try {
            reportsConfig.save(reportsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        reportsConfig = YamlConfiguration.loadConfiguration(reportsFile);
    }


}
