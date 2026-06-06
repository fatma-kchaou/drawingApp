package com.example.demo1.decorator;

import com.example.demo1.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StrokeColorDecorator extends ShapeDecorator {

    private Color color;

    public StrokeColorDecorator(Shape shape, Color color) {
        super(shape);
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        decoratedShape.draw(gc);
    }

    public Color getColor() { return color; }
}