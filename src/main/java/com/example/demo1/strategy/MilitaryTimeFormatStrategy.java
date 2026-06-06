package com.example.demo1.strategy;

/** Military style: 143005 (HHMMSS) */
public class MilitaryTimeFormatStrategy implements TimeFormatStrategy {
    @Override
    public String formatTime(int hour, int minute, int second) {
        return String.format("%02d%02d%02d hrs", hour, minute, second);
    }
}
