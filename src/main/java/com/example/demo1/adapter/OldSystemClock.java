package com.example.demo1.adapter;

import javafx.scene.control.Label;

/**
 * The Adaptee: Represents an "old system" clock that only knows how to display
 * time as a pre-formatted string. It's incompatible with our Observer's int format.
 */
public class OldSystemClock {
    private Label legacyDisplayLabel; // A JavaFX Label to show the "legacy" output

    public OldSystemClock(Label label) {
        this.legacyDisplayLabel = label;
    }

    /**
     * This is the method that the "old system" uses to display time.
     * It expects a time string in a specific format (e.g., "HH-MM-SS").
     * @param formattedTime The time string to display.
     */
    public void displayFormattedTime(String formattedTime) {
        legacyDisplayLabel.setText("[LEGACY]: " + formattedTime);
    }
}