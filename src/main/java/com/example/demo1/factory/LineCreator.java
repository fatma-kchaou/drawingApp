package com.example.demo1.factory;

import com.example.demo1.shapes.Line;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

/**
 * ConcreteCreator — crée une Line entre start et end.
 */
public class LineCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        return new Line(start, end);
    }
}
