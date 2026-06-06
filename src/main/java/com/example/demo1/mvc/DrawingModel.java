package com.example.demo1.mvc;

import java.util.ArrayList;
import java.util.List;
import com.example.demo1.shapes.Shape;

public class DrawingModel {

    private List<Shape> shapes = new ArrayList<>();
    private List<Shape> selectedShapes = new ArrayList<>();

    // =========================
    // ADD / REMOVE SHAPES
    // =========================
    public void add(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public void remove(Shape shape) {
        if (shape != null) {
            shapes.remove(shape);
            selectedShapes.remove(shape);
        }
    }

    // =========================
    // SELECTION MANAGEMENT
    // =========================
    public void addSelected(Shape shape) {
        if (shape != null && !selectedShapes.contains(shape)) {
            selectedShapes.add(shape);
        }
    }

    public void removeSelected(Shape shape) {
        selectedShapes.remove(shape);
    }

    public void clearSelected() {
        selectedShapes.clear();
    }

    public List<Shape> getSelectedShapes() {
        return selectedShapes;
    }

    // =========================
    // GET ALL SHAPES
    // =========================
    public List<Shape> getShapes() {
        return shapes;
    }

    // =========================
    // CLEAR ALL
    // =========================
    public void clearShapes() {
        shapes.clear();
        selectedShapes.clear();
    }

    // =========================
    // USEFUL EXTRAS (IMPORTANT)
    // =========================

    public boolean isSelected(Shape shape) {
        return selectedShapes.contains(shape);
    }

    public int getShapeCount() {
        return shapes.size();
    }

    public int getSelectedCount() {
        return selectedShapes.size();
    }
}