package com.example.demo1.dao;

import java.util.List;
import com.example.demo1.shapes.Shape;

public interface DrawingDAO {
    void saveDrawing(String name, List<Shape> shapes) throws Exception;
    List<Shape> loadDrawing(String name) throws Exception;
}