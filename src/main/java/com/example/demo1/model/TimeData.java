package com.example.demo1.model;

import com.example.demo1.observer.Observer;
import java.util.ArrayList;
import java.util.List;

public class TimeData implements Subject {
    private List<Observer> observers;
    private int hour;
    private int minute;
    private int second;

    public TimeData() {
        observers = new ArrayList<>();
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(hour, minute, second);
        }
    }

    // Method to change the time and notify observers
    public void setTime(int hour, int minute, int second) {
        // Basic validation
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
            System.err.println("Invalid time values provided.");
            return;
        }
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        notifyObservers(); // Notify all registered observers
    }

    // Getters for the current time (optional, but good practice)
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}