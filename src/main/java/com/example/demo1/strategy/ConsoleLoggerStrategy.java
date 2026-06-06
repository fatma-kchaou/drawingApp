package com.example.demo1.strategy;

/**
 * Stratégie de journalisation vers la console.
 * N'affiche que les vraies actions utilisateur (dessin, save, load, undo, redo).
 */
public class ConsoleLoggerStrategy implements LoggerStrategy {
    @Override
    public void log(String action, String details) {
        System.out.println("[LOG] " + action + ": " + details);
    }
}