package com.example.demo1.strategy;

/** 12-hour AM/PM format: 02:30:45 PM */
public class AmPmTimeFormatStrategy implements TimeFormatStrategy {
    @Override
    public String formatTime(int hour, int minute, int second) {
        String period = hour < 12 ? "AM" : "PM";
        int h = hour % 12;
        if (h == 0) h = 12;
        return String.format("%02d:%02d:%02d %s", h, minute, second, period);
    }
}
