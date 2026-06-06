package com.example.demo1.strategy;

public class LegacyTimeFormatStrategy implements TimeFormatStrategy {
    @Override
    public String formatTime(int hour, int minute, int second) {
        return String.format("%02d-%02d-%02d", hour, minute, second);
    }
}