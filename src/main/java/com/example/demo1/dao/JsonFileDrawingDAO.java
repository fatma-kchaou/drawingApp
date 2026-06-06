package com.example.demo1.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.example.demo1.shapes.Shape;
import com.example.demo1.gsonadapters.ShapeTypeAdapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implémentation du DAO pour sauvegarder et charger des dessins dans des fichiers JSON.
 */
public class JsonFileDrawingDAO implements DrawingDAO {
    private static final String DRAWINGS_DIR = "drawings_data";
    private Gson gson;
    private Type shapeListType;

    public JsonFileDrawingDAO() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                // registerTypeHierarchyAdapter intercepts ALL subclasses of Shape
                // (StrokeColorDecorator, FillColorDecorator, Circle, etc.)
                // registerTypeAdapter(Shape.class, ...) only intercepts Shape itself
                .registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter())
                .create();

        shapeListType = new TypeToken<List<Shape>>() {}.getType();

        try {
            Files.createDirectories(Paths.get(DRAWINGS_DIR));
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du dossier : " + e.getMessage());
        }
    }

    private Path getFilePath(String name) {
        return Paths.get(DRAWINGS_DIR, name + ".json");
    }

    @Override
    public void saveDrawing(String name, List<Shape> shapes) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Drawing name cannot be null or empty.");
        }
        if (shapes == null) {
            shapes = Collections.emptyList();
        }

        Path filePath = getFilePath(name);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(shapes, shapeListType, writer);
        } catch (IOException e) {
            throw new Exception("Error saving drawing to JSON file: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Shape> loadDrawing(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Drawing name cannot be null or empty.");
        }

        Path filePath = getFilePath(name);
        if (!Files.exists(filePath)) {
            throw new Exception("Drawing '" + name + "' not found.");
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            List<Shape> loadedShapes = gson.fromJson(reader, shapeListType);
            return loadedShapes != null ? loadedShapes : new ArrayList<>();
        } catch (IOException e) {
            throw new Exception("Error loading drawing from JSON file: " + e.getMessage(), e);
        }
    }
}