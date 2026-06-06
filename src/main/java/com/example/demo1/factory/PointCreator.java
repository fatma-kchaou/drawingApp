package com.example.demo1.factory;

import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

/**
 * ConcreteCreator — crée un Point.
 * Utilise uniquement endPoint (le point de relâchement de la souris).
 */
public class PointCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        return new Point(end.getX(), end.getY());
    }
}
