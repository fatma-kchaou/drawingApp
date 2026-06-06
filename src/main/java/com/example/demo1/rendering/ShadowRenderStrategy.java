package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/** 3D — DropShadow pour l'illusion de profondeur */
public class ShadowRenderStrategy implements RenderStrategy {
    @Override
    public void applyBefore(GraphicsContext gc) {
        gc.setLineWidth(2.5);
        gc.setLineDashes(0);
        DropShadow ds = new DropShadow();
        ds.setOffsetX(4);
        ds.setOffsetY(4);
        ds.setRadius(8);
        ds.setColor(Color.color(0, 0, 0, 0.45));
        gc.setEffect(ds);
    }
    @Override
    public void applyAfter(GraphicsContext gc) {
        gc.setEffect(null);
        gc.setLineWidth(1);
    }
}
