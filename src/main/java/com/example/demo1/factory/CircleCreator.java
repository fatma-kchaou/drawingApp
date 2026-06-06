package com.example.demo1.factory;

import com.example.demo1.shapes.Circle;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

/**
 * ConcreteCreator — crée un Circle.
 * start = centre, distance(start→end) = rayon.
 */
public class CircleCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        double radius = start.distance(end);
        return new Circle(new Point(start.getX(), start.getY()), radius);
    }
}
