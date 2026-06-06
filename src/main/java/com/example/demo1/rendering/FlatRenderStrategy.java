package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/** 2D — rendu solide standard (défaut) */
public class FlatRenderStrategy implements RenderStrategy {
    @Override
    public void applyBefore(GraphicsContext gc) {
        gc.setLineWidth(2);
        gc.setLineDashes(0);
        gc.setEffect(null);
    }
    @Override
    public void applyAfter(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.setEffect(null);
    }
}
