package com.example.demo1.strategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

/**
 * Logger vers base de données SQLite (logs.db)
 */
public class DatabaseLoggerStrategy implements LoggerStrategy {

    private static final String DB_URL = "jdbc:sqlite:logs.db";

    // Bloc exécuté UNE SEULE FOIS au chargement de la classe
    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            System.out.println("SQLite connecté -> "
                    + new File("logs.db").getAbsolutePath());

            String sql = "CREATE TABLE IF NOT EXISTS logs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "timestamp TEXT NOT NULL," +
                    "action TEXT NOT NULL," +
                    "details TEXT" +
                    ")";

            stmt.executeUpdate(sql);

            System.out.println("Table logs prête ✔");

        } catch (SQLException e) {
            System.out.println("Erreur initialisation SQLite ❌");
            e.printStackTrace();
        }
    }

    @Override
    public void log(String action, String details) {

        String sql = "INSERT INTO logs (timestamp, action, details) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            stmt.setString(1, timestamp);
            stmt.setString(2, action);
            stmt.setString(3, details);

            stmt.executeUpdate();

            System.out.println("[DB LOG] " + action + " -> " + details);

        } catch (SQLException e) {
            System.out.println("Erreur log SQLite ❌");
            e.printStackTrace();
        }
    }
}
