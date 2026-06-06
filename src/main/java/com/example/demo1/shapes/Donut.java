package com.example.demo1.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Donut extends Circle {
    private double innerRadius;

    public Donut(Point center, double innerRadius, double outerRadius) {
        super(center, outerRadius);
        this.innerRadius = innerRadius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        if (!isWireframe()) {
            gc.fillOval(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(),
                    getRadius() * 2, getRadius() * 2);
        }
        gc.strokeOval(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(),
                getRadius() * 2, getRadius() * 2);
        gc.strokeOval(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius,
                innerRadius * 2, innerRadius * 2);
        resetDimensionEffect(gc);
    }

    @Override
    public boolean contains(double x, double y) {
        double dist = getCenter().distance(new Point(x, y));
        return dist <= getRadius() && dist >= innerRadius;
    }

    @Override
    public void moveBy(int byX, int byY) {
        super.moveBy(byX, byY); // moves the center
    }

    public double getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }
}
