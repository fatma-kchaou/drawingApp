package com.example.demo1.command;

import com.example.demo1.mvc.DrawingModel;
import com.example.demo1.shapes.Shape;

public class CmdShapeRemove implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdShapeRemove(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.remove(shape);
    }

    @Override
    public void unexecute() {
        model.add(shape);
    }
}