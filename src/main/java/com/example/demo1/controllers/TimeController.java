package com.example.demo1.controllers;
import com.example.demo1.model.TimeData; // <--- Crucial
import javafx.fxml.FXML; // Standard JavaFX import
import javafx.scene.control.Label; // Standard JavaFX import
import javafx.scene.control.TextField; // Standard JavaFX import
// ... rest of code

public class TimeController {
    // FXML elements from your UI (we'll manually set these up in MainApp for simplicity)
    public TextField hourField;
    public TextField minuteField;
    public TextField secondField;
    public Label displayLabel1; // Display for the first observer
    public Label displayLabel2; // Display for the second observer

    private TimeData timeData;

    // This method would typically be called by FXML if you used a .fxml file
    public void setTimeData(TimeData timeData) {
        this.timeData = timeData;
    }

    @FXML // This annotation is usually for methods connected to FXML actions
    public void handleSetTime() {
        try {
            int hour = Integer.parseInt(hourField.getText());
            int minute = Integer.parseInt(minuteField.getText());
            int second = Integer.parseInt(secondField.getText());
            timeData.setTime(hour, minute, second); // Update the model, which notifies observers
        } catch (NumberFormatException e) {
            System.err.println("Invalid time input. Please enter numbers for hour, minute, and second.");
            // Optionally, show an alert to the user
        }
    }
}