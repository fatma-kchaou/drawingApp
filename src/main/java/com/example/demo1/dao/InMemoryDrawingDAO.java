package com.example.demo1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.demo1.shapes.Shape; // Assuming your Shape class is in com.example.demo1.shapes

/**
 * An in-memory implementation of the DrawingDAO interface.
 * Data stored in this DAO will be lost when the application closes.
 */
public class InMemoryDrawingDAO implements DrawingDAO {

    // A Map to store drawings: key = drawing name, value = list of shapes
    private final Map<String, List<Shape>> drawings = new HashMap<>();

    @Override
    public void saveDrawing(String name, List<Shape> shapes) throws Exception {
        // In-memory: simply store or update the list of shapes for the given name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Drawing name cannot be null or empty.");
        }
        if (shapes == null) {
            // Or handle as an empty list depending on desired behavior
            System.out.println("Warning: Attempting to save a null list of shapes for drawing: " + name);
        }

        drawings.put(name, shapes);
        System.out.println("Drawing '" + name + "' saved to in-memory storage. Total drawings: " + drawings.size());
    }

    @Override
    public List<Shape> loadDrawing(String name) throws Exception {
        // In-memory: retrieve the list of shapes by name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Drawing name cannot be null or empty.");
        }
        List<Shape> loadedShapes = drawings.get(name);
        if (loadedShapes != null) {
            System.out.println("Drawing '" + name + "' loaded from in-memory storage.");
        } else {
            System.out.println("Drawing '" + name + "' not found in in-memory storage.");
        }
        return loadedShapes;
    }
}