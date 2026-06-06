package com.example.demo1.shapes;

import com.example.demo1.rendering.RenderContext;
import javafx.scene.canvas.GraphicsContext;


public abstract class Shape {

    public abstract void draw(GraphicsContext gc);
    public abstract boolean contains(double x, double y);
    public abstract void moveBy(int byX, int byY);

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /** Délègue à la RenderStrategy active — Shape ne sait pas ce qu'elle fait */
    protected void applyDimensionEffect(GraphicsContext gc) {
        RenderContext.get().applyBefore(gc);
    }

    /** Remet le contexte graphique à zéro après dessin */
    protected void resetDimensionEffect(GraphicsContext gc) {
        RenderContext.get().applyAfter(gc);
    }

    /** Retourne true si le mode actuel est wireframe (pas de fill) */
    protected boolean isWireframe() {
        return RenderContext.isWireframe();
    }
}
