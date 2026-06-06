package com.example.demo1.mvc;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class DrawingApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        DrawingModel model = new DrawingModel();
        DrawingFrame frame = new DrawingFrame();
        DrawingController controller = new DrawingController(model, frame);

        frame.setController(controller);

        Scene scene = new Scene(frame, 1100, 750);

        // Load CSS
        try {
            String css = getClass().getResource("/drawing.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("CSS not found: " + e.getMessage());
        }

        // Keyboard shortcuts: Ctrl+Z = Undo, Ctrl+Y = Redo
        scene.getAccelerators().put(
            new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN),
            controller::Undo
        );
        scene.getAccelerators().put(
            new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN),
            controller::Redo
        );

        primaryStage.setScene(scene);
        primaryStage.setTitle("✏  JavaFX Drawing App");
        primaryStage.show();
    }
}
