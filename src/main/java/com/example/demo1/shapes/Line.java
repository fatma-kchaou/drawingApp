package com.example.demo1.shapes;
import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        gc.strokeLine(
                start.getX(),
                start.getY(),
                end.getX(),
                end.getY()
        );
        resetDimensionEffect(gc);
    }
    @Override
    public boolean contains(double x, double y) {
        double distance = Math.abs((end.getY() - start.getY()) * x - (end.getX() - start.getX()) * y + end.getX() * start.getY() - end.getY() * start.getX())
                / start.distance(end);
        return distance < 5; // tolerance
    }

    @Override
    public void moveBy(int byX, int byY) {
        start = new Point(start.getX() + byX, start.getY() + byY);
        end   = new Point(end.getX()   + byX, end.getY()   + byY);
    }

    public Point getStart() { return start; }
    public Point getEnd()   { return end; }
}