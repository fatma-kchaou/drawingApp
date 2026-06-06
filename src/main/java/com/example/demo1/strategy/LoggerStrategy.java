package com.example.demo1.strategy;

/**
 * Interface pour définir une stratégie de journalisation.
 */
public interface LoggerStrategy {
    void log(String action, String details);
}