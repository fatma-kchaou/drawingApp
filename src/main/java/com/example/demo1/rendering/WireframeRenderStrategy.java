package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/** 1D — filaire, pointillés, sans remplissage */
public class WireframeRenderStrategy implements RenderStrategy {
    @Override
    public void applyBefore(GraphicsContext gc) {
        gc.setLineWidth(1.5);
        gc.setLineDashes(6, 4);
        gc.setEffect(null);
    }
    @Override
    public void applyAfter(GraphicsContext gc) {
        gc.setLineDashes(0);
        gc.setLineWidth(1);
        gc.setEffect(null);
    }
}
