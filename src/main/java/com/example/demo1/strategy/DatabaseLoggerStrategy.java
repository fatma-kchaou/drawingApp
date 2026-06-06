package com.example.demo1.strategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation vers une base de données SQLite.
 */
public class DatabaseLoggerStrategy implements LoggerStrategy {
    // Le chemin vers le fichier de la base de données SQLite
    private static final String DB_URL = "jdbc:sqlite:logs.db"; // Crée un fichier logs.db dans le dossier du projet

    // Initialisation de la table logs si elle n'existe pas
    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS logs (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "timestamp TEXT NOT NULL," + // SQLite n'a pas de type DATETIME natif, TEXT est courant
                             "action TEXT NOT NULL," +
                             "details TEXT" +
                             ")")) {
            stmt.executeUpdate();
            System.out.println("Table 'logs' de SQLite initialisée ou vérifiée.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données SQLite : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void log(String action, String details) {
        String sql = "INSERT INTO logs (timestamp, action, details) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Formater la date/heure pour SQLite (TEXT)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            stmt.setString(1, timestamp);
            stmt.setString(2, action);
            stmt.setString(3, details);
            stmt.executeUpdate();
            System.out.println("[LOG - DB] Action '" + action + "' logged to SQLite database.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'écriture du log dans la base de données SQLite : " + e.getMessage());
            e.printStackTrace();
        }
    }
}