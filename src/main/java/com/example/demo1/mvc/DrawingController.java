package com.example.demo1.mvc;

import com.example.demo1.command.Command;
import com.example.demo1.command.CmdShapeAdd;
import com.example.demo1.command.CmdStack;

import com.example.demo1.factory.ShapeCreator;   // ← Factory Method

import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

import com.example.demo1.dao.DrawingDAO;
import com.example.demo1.dao.JsonFileDrawingDAO;

import com.example.demo1.strategy.LoggerStrategy;
import com.example.demo1.strategy.ConsoleLoggerStrategy;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

public class DrawingController {

    private DrawingModel model;
    private DrawingFrame frame;

    private CmdStack      cmdStack = new CmdStack();
    private LoggerStrategy logger;

    public DrawingController(DrawingModel model, DrawingFrame frame) {
        this.model  = model;
        this.frame  = frame;
        this.logger = new ConsoleLoggerStrategy();
        this.logger.log("System", "Controller initialized");
    }

    // ─────────────────────────────────────────────────────
    // CREATE SHAPE  — délègue entièrement à la Factory Method
    // ─────────────────────────────────────────────────────
    public void createShape(Point startPoint, Point endPoint) {

        String shapeType = frame.getSelectedShapeType();

        if (shapeType == null) {
            showAlert("Selection Error", "Please select a shape first.");
            logger.log("Error", "No shape selected");
            return;
        }

        Color stroke = frame.getStrokeColor();
        Color fill   = frame.getFillColor();

        Shape shape;
        try {
            // 1. Obtenir le Creator concret via le registre de la Factory
            ShapeCreator creator = ShapeCreator.forType(shapeType);

            // 2. assembleShape() = factoryMethod() + décorateurs
            //    Toute la logique de construction est dans la Factory Method
            shape = creator.assembleShape(startPoint, endPoint, stroke, fill);

        } catch (IllegalArgumentException ex) {
            showAlert("Error", "Unknown shape type: " + shapeType);
            logger.log("Error", ex.getMessage());
            return;
        }

        // 3. Command Pattern — encapsule l'ajout pour Undo/Redo
        Command cmd = new CmdShapeAdd(model, shape);
        cmd.execute();
        cmdStack.push(cmd);

        frame.getView().paint();
        frame.updateCounter();

        logger.log("Drawing", shapeType + " created (stroke=" + stroke + ", fill=" + fill + ")");
    }

    // ─────────────────────────────────────────────────────
    // UNDO
    // ─────────────────────────────────────────────────────
    public void Undo() {
        if (cmdStack.canUndo()) {
            Command cmd = cmdStack.popUndo();
            cmd.unexecute();
            cmdStack.pushRedo(cmd);
            frame.getView().paint();
            frame.updateCounter();
            logger.log("Undo", cmd.getClass().getSimpleName() + " undone");
        } else {
            showAlert("Undo", "Nothing to undo.");
        }
    }

    // ─────────────────────────────────────────────────────
    // REDO
    // ─────────────────────────────────────────────────────
    public void Redo() {
        if (cmdStack.canRedo()) {
            Command cmd = cmdStack.popRedo();
            cmd.execute();
            cmdStack.pushRedo_internal(cmd);
            frame.getView().paint();
            frame.updateCounter();
            logger.log("Redo", cmd.getClass().getSimpleName() + " redone");
        } else {
            showAlert("Redo", "Nothing to redo.");
        }
    }

    // ─────────────────────────────────────────────────────
    // SAVE
    // ─────────────────────────────────────────────────────
    public void saveToDatabase(String name) {
        try {
            DrawingDAO dao = new JsonFileDrawingDAO();
            dao.saveDrawing(name, model.getShapes());
            showAlert("Save", "Drawing saved: " + name);
            logger.log("Save", name);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────
    // LOAD
    // ─────────────────────────────────────────────────────
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
                logger.log("Load", name);
            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        });
    }

    // ─────────────────────────────────────────────────────
    // GETTERS
    // ─────────────────────────────────────────────────────
    public DrawingModel getModel() { return model; }
    public DrawingFrame getFrame() { return frame; }

    // ─────────────────────────────────────────────────────
    // UTIL
    // ─────────────────────────────────────────────────────
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
