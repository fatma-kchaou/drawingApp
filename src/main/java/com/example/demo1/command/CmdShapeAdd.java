package com.example.demo1.command;

import com.example.demo1.shapes.Shape;
import com.example.demo1.mvc.DrawingModel;

public class CmdShapeAdd implements Command {
    private DrawingModel model;
    private Shape shape;

    public CmdShapeAdd(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.add(shape);
    }

    @Override
    public void unexecute() {
        model.remove(shape);
    }
}