package com.example.demo1.strategy;

/** Emoji clock style: 🕐 14h 30m 05s */
public class EmojiTimeFormatStrategy implements TimeFormatStrategy {
    @Override
    public String formatTime(int hour, int minute, int second) {
        return String.format("\uD83D\uDD50 %02dh %02dm %02ds", hour, minute, second);
    }
}
