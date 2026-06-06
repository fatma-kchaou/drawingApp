package com.example.demo1.mvc;

import com.example.demo1.shapes.Shape;
import com.example.demo1.shapes.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawingView extends Pane {

    private Canvas canvas;
    private DrawingController controller;

    private Point startPoint;
    private Shape draggedShape = null;

    private double offsetX, offsetY;

    public DrawingView() {
        canvas = new Canvas(800, 700);
        getChildren().add(canvas);

        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);
    }

    // =========================
    // MOUSE PRESSED
    // =========================
    private void handleMousePressed(MouseEvent e) {

        if (controller == null) return;

        startPoint = new Point(e.getX(), e.getY());

        for (int i = controller.getModel().getShapes().size() - 1; i >= 0; i--) {
            Shape shape = controller.getModel().getShapes().get(i);

            if (shape.contains(e.getX(), e.getY())) {
                draggedShape = shape;
                offsetX = e.getX();
                offsetY = e.getY();
                return;
            }
        }
    }

    // =========================
    // MOUSE DRAGGED
    // =========================
    private void handleMouseDragged(MouseEvent e) {

        if (draggedShape != null) {

            double dx = e.getX() - offsetX;
            double dy = e.getY() - offsetY;

            draggedShape.moveBy((int) dx, (int) dy);

            offsetX = e.getX();
            offsetY = e.getY();

            paint();
        }

        else if (startPoint != null && controller != null) {

            paint();

            GraphicsContext gc = canvas.getGraphicsContext2D();

            String shapeType = controller.getFrame().getSelectedShapeType();

            // 🎨 preview style (simple)
            gc.setStroke(Color.GRAY);

            Point current = new Point(e.getX(), e.getY());

            switch (shapeType) {

                case "Point":
                    gc.strokeOval(current.getX() - 2, current.getY() - 2, 4, 4);
                    break;

                case "Line":
                    gc.strokeLine(startPoint.getX(), startPoint.getY(),
                            current.getX(), current.getY());
                    break;

                case "Rectangle":
                    gc.strokeRect(
                            Math.min(startPoint.getX(), current.getX()),
                            Math.min(startPoint.getY(), current.getY()),
                            Math.abs(current.getX() - startPoint.getX()),
                            Math.abs(current.getY() - startPoint.getY())
                    );
                    break;

                case "Circle":
                    double r = startPoint.distance(current);
                    gc.strokeOval(startPoint.getX() - r, startPoint.getY() - r, r * 2, r * 2);
                    break;

                case "Donut":
                    double outer = startPoint.distance(current);
                    double inner = outer / 2;

                    gc.strokeOval(startPoint.getX() - outer, startPoint.getY() - outer, outer * 2, outer * 2);
                    gc.strokeOval(startPoint.getX() - inner, startPoint.getY() - inner, inner * 2, inner * 2);
                    break; // ✅ FIXED

                case "Hexagon":
                    double hexR = startPoint.distance(current);

                    double[] x = new double[6];
                    double[] y = new double[6];

                    for (int i = 0; i < 6; i++) {
                        double angle = Math.PI / 3 * i;
                        x[i] = startPoint.getX() + hexR * Math.cos(angle);
                        y[i] = startPoint.getY() + hexR * Math.sin(angle);
                    }

                    gc.strokePolygon(x, y, 6);
                    break; // ✅ FIXED
            }
        }
    }

    // =========================
    // MOUSE RELEASED
    // =========================
    private void handleMouseReleased(MouseEvent e) {

        if (draggedShape != null) {
            draggedShape = null;
        }

        else if (startPoint != null && controller != null) {

            Point endPoint = new Point(e.getX(), e.getY());

            controller.createShape(startPoint, endPoint);

            startPoint = null;
        }
    }

    // =========================
    // PAINT
    // =========================
    public void paint() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Pure white canvas
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (controller != null) {
            for (Shape shape : controller.getModel().getShapes()) {
                shape.draw(gc);
            }
        }
    }

    public void setController(DrawingController controller) {
        this.controller = controller;
    }
}