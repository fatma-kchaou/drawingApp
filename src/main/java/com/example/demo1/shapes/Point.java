package com.example.demo1.shapes;
import com.example.demo1.rendering.RenderContext;
import javafx.scene.canvas.GraphicsContext;

public class Point extends Shape {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        double size = RenderContext.get() instanceof com.example.demo1.rendering.ShadowRenderStrategy ? 6 : 4;
        gc.fillOval(x - size / 2, y - size / 2, size, size);
        resetDimensionEffect(gc);
    }

    @Override
    public boolean contains(double px, double py) {
        return distance(x, y, px, py) <= 3;
    }

    @Override
    public void moveBy(int byX, int byY) {
        x += byX;
        y += byY;
    }

    public double distance(Point other) {
        return distance(x, y, other.x, other.y);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}