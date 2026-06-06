package com.example.demo1.observer;

import com.example.demo1.strategy.TimeFormatStrategy;
import javafx.scene.control.Label;

public class TimeDisplay implements Observer {
    private Label displayLabel;
    private TimeFormatStrategy formatStrategy;

    public TimeDisplay(Label displayLabel, TimeFormatStrategy formatStrategy) {
        this.displayLabel = displayLabel;
        this.formatStrategy = formatStrategy;
    }

    // NEW METHOD: Allows changing the formatting strategy at runtime
    public void setStrategy(TimeFormatStrategy formatStrategy) {
        this.formatStrategy = formatStrategy;
    }

    @Override
    public void update(int hour, int minute, int second) {
        String timeString = formatStrategy.formatTime(hour, minute, second);
        displayLabel.setText(timeString);
        // No console print — only updates the label silently
    }
}