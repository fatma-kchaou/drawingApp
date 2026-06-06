package com.example.demo1.strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation vers un fichier texte.
 */
public class FileLoggerStrategy implements LoggerStrategy {
    private static final String LOG_FILE_PATH = "application_log.txt"; // Nom du fichier de log
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void log(String action, String details) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s][LOG - FILE] %s: %s%n", timestamp, action, details);

        try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true); // 'true' pour append
             PrintWriter pw = new PrintWriter(fw)) {
            pw.print(logEntry);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier de log : " + e.getMessage());
            e.printStackTrace();
        }
    }
}