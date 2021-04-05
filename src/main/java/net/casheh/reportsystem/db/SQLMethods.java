package net.casheh.reportsystem.db;

import net.casheh.reportsystem.ReportSystem;
import org.bukkit.entity.Player;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLMethods {

    private ReportSystem plugin;

    public SQLMethods(ReportSystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS reports (id MEDIUMINT NOT NULL AUTO_INCREMENT,name VARCHAR(100),reporter VARCHAR(100),uuid VARCHAR(100),report VARCHAR(100),PRIMARY KEY (id))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reportPlayer(Player player, String reporter, String reportReason) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO reports (NAME, REPORTER, UUID, REPORT) VALUES (?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, reporter);
            ps.setString(3, uuid.toString());
            ps.setString(4, reportReason);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, String> getReports(Player player) {
        LinkedHashMap<String, String> reports = new LinkedHashMap<>();
        try {
           PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT report,reporter FROM reports WHERE UUID=?");
           ps.setString(1, player.getUniqueId().toString());
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               reports.put(rs.getString("reporter"), rs.getString("report"));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }


}
