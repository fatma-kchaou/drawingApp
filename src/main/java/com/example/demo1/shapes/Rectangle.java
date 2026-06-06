package com.example.demo1.shapes;
import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        if (!isWireframe()) {
            gc.fillRect(x, y, width, height);
        }
        gc.strokeRect(x, y, width, height);
        resetDimensionEffect(gc);
    }
    @Override
    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public void moveBy(int byX, int byY) {
        x += byX;
        y += byY;
    }

    public double getX()      { return x; }
    public double getY()      { return y; }
    public double getWidth()  { return width; }
    public double getHeight() { return height; }
}