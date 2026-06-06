package com.example.demo1.factory;

import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Rectangle;
import com.example.demo1.shapes.Shape;

/**
 * ConcreteCreator — crée un Rectangle.
 * Calcule x/y/width/height à partir des deux points de la souris.
 */
public class RectangleCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        double x      = Math.min(start.getX(), end.getX());
        double y      = Math.min(start.getY(), end.getY());
        double width  = Math.abs(end.getX() - start.getX());
        double height = Math.abs(end.getY() - start.getY());
        return new Rectangle(x, y, width, height);
    }
}
