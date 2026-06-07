package com.example.demo1.strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation vers un fichier texte.
 */
public class FileLoggerStrategy implements LoggerStrategy {
    private static final String LOG_FILE_PATH = "logs/application_log.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void log(String action, String details) {

        try {
            // Création du dossier logs s'il n'existe pas
            File logFile = new File(LOG_FILE_PATH);

            if (logFile.getParentFile() != null
                    && !logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdirs();
            }

            // Affichage du chemin complet du fichier
            System.out.println("Log enregistré dans : "
                    + logFile.getAbsolutePath());

            String timestamp = LocalDateTime.now().format(FORMATTER);

            String logEntry = String.format(
                    "[%s] [LOG-FILE] %s : %s%n",
                    timestamp,
                    action,
                    details
            );

            try (FileWriter fw = new FileWriter(logFile, true);
                 PrintWriter pw = new PrintWriter(fw)) {

                pw.print(logEntry);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du log : "
                    + e.getMessage());
            e.printStackTrace();
        }
    }
}
