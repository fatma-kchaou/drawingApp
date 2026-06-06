package com.example.demo1.decorator;

import com.example.demo1.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {

    protected Shape decoratedShape;

    public ShapeDecorator(Shape shape) {
        this.decoratedShape = shape;
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
    }

    @Override
    public boolean contains(double x, double y) {
        return decoratedShape.contains(x, y);
    }

    @Override
    public void moveBy(int byX, int byY) {
        decoratedShape.moveBy(byX, byY);
    }

    /** Exposed for serialization — allows unwrapping the decorator chain */
    public Shape getDecoratedShape() { return decoratedShape; }
}