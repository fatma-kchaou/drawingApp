package com.example.demo1.mvc;

import com.example.demo1.command.Command;
import com.example.demo1.command.CmdShapeAdd;
import com.example.demo1.command.CmdStack;

import com.example.demo1.factory.ShapeCreator;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

import com.example.demo1.dao.DrawingDAO;
import com.example.demo1.dao.JsonFileDrawingDAO;

import com.example.demo1.strategy.LoggerStrategy;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class DrawingController {

    private DrawingModel model;
    private DrawingFrame frame;

    private CmdStack cmdStack = new CmdStack();

    private List<LoggerStrategy> loggers;

    public DrawingController(DrawingModel model,
            DrawingFrame frame,
            LoggerStrategy... loggers) {

					this.model = model;
					this.frame = frame;
					
					this.loggers = List.of(loggers);
					
					log("System", "Controller initialized");
					}

    // helper
    private void log(String action, String details) {
        for (LoggerStrategy logger : loggers) {
            logger.log(action, details);
        }
    }
    // CREATE SHAPE
    public void createShape(Point startPoint, Point endPoint) {

        String shapeType = frame.getSelectedShapeType();

        if (shapeType == null) {
            showAlert("Selection Error", "Please select a shape first.");
            log("Error", "No shape selected");
            return;
        }

        Color stroke = frame.getStrokeColor();
        Color fill = frame.getFillColor();

        try {
            ShapeCreator creator = ShapeCreator.forType(shapeType);
            Shape shape = creator.assembleShape(startPoint, endPoint, stroke, fill);

            Command cmd = new CmdShapeAdd(model, shape);
            cmd.execute();
            cmdStack.push(cmd);

            frame.getView().paint();
            frame.updateCounter();

            log("Drawing", shapeType + " created");

        } catch (IllegalArgumentException ex) {
            showAlert("Error", "Unknown shape type: " + shapeType);
            log("Error", ex.getMessage());
        }
    }

    // UNDO
    public void Undo() {
        if (cmdStack.canUndo()) {
            Command cmd = cmdStack.popUndo();
            cmd.unexecute();
            cmdStack.pushRedo(cmd);

            frame.getView().paint();
            frame.updateCounter();

            log("Undo", cmd.getClass().getSimpleName());
        } else {
            showAlert("Undo", "Nothing to undo.");
        }
    }

    // REDO
    public void Redo() {
        if (cmdStack.canRedo()) {
            Command cmd = cmdStack.popRedo();
            cmd.execute();
            cmdStack.pushRedo_internal(cmd);

            frame.getView().paint();
            frame.updateCounter();

            log("Redo", cmd.getClass().getSimpleName());
        } else {
            showAlert("Redo", "Nothing to redo.");
        }
    }

    // SAVE
    public void saveToDatabase(String name) {
        try {
            DrawingDAO dao = new JsonFileDrawingDAO();
            dao.saveDrawing(name, model.getShapes());

            showAlert("Save", "Drawing saved: " + name);
            log("Save", name);

        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    // LOAD
    public void loadFromDatabase() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load");
        dialog.setHeaderText("Enter drawing name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                DrawingDAO dao = new JsonFileDrawingDAO();
                List<Shape> shapes = dao.loadDrawing(name);

                model.clearShapes();
                for (Shape s : shapes) model.add(s);

                frame.getView().paint();
                frame.updateCounter();

                log("Load", name);

            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        });
    }

    // UTIL
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // getters
    public DrawingModel getModel() { return model; }
    public DrawingFrame getFrame() { return frame; }
}
