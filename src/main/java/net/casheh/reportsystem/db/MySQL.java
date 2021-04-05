package net.casheh.reportsystem.db;

import net.casheh.reportsystem.ReportSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final String HOST;
    private final String PORT;
    private final String DATABASE;
    private final String USERNAME;
    private final String PASSWORD;

    public MySQL(ReportSystem plugin) {
        HOST = "localhost";
        PORT = "3306";
        DATABASE = "reports";
        USERNAME = "root";
        PASSWORD = "";
    }

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            HOST + ":" +
                            PORT + "/" +
                            DATABASE + "?useSSL=false",
                    USERNAME, PASSWORD);

        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() { return connection; }

}
