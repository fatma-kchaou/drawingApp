package com.example.demo1.factory;

import com.example.demo1.adapter.HexagonAdapter;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;
import javafx.scene.paint.Color;

/**
 * ConcreteCreator — crée un Hexagone via HexagonAdapter.
 * Illustre aussi la collaboration Factory Method + Adapter.
 */
public class HexagonCreator extends ShapeCreator {

    @Override
    public Shape factoryMethod(Point start, Point end) {
        int radius = (int) start.distance(end);
        return new HexagonAdapter(start, radius, Color.LIGHTPINK, Color.BLACK);
    }
}
