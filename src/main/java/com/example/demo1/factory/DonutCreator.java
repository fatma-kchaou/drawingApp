package com.example.demo1.factory;

import com.example.demo1.shapes.Donut;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;

/**
 * ConcreteCreator — crée un Donut.
 * outerRadius = distance(start→end), innerRadius = outer/2.
 */
public class DonutCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        double outer = start.distance(end);
        double inner = outer / 2.0;
        return new Donut(new Point(start.getX(), start.getY()), inner, outer);
    }
}
