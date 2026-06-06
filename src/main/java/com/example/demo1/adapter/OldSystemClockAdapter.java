package com.example.demo1.adapter;

import com.example.demo1.observer.Observer; // Import the target interface

/**
 * The Adapter: Implements the Observer interface (the Target) and
 * wraps an OldSystemClock instance (the Adaptee), translating
 * the update method's arguments into a format the Adaptee understands.
 */
public class OldSystemClockAdapter implements Observer {
    private OldSystemClock oldClock; // The Adaptee instance

    public OldSystemClockAdapter(OldSystemClock oldClock) {
        this.oldClock = oldClock;
    }

    @Override
    public void update(int hour, int minute, int second) {
        // This is the core of the Adapter pattern:
        // Adapt the (hour, minute, second) format from the Subject's notification
        // into a String format ("HH-MM-SS") that the OldSystemClock (Adaptee) understands.
        String formattedTimeString = String.format("%02d-%02d-%02d", hour, minute, second);

        // Now, call the Adaptee's method with the adapted data
        oldClock.displayFormattedTime(formattedTimeString);
    }
}