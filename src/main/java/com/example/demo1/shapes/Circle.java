package com.example.demo1.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {

    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        if (!isWireframe()) {
            gc.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        }
        gc.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        resetDimensionEffect(gc);
    }

    @Override
    public boolean contains(double x, double y) {
        return center.distance(new Point(x, y)) <= radius;
    }

    @Override
    public void moveBy(int byX, int byY) {
        center = new Point(
                center.getX() + byX,
                center.getY() + byY
        );
    }

    // =========================
    // GETTERS
    // =========================
    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}