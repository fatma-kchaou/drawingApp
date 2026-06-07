package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract Factory Pattern — remplace RenderStrategy.
 *
 * Chaque fabrique concrète (1D, 2D, 3D) produit une famille cohérente
 * de comportements de rendu selon le type de forme :
 *   - createLineRenderer()    → pour Line
 *   - createSurfaceRenderer() → pour Circle, Rectangle, Donut, SurfaceShape
 *   - createPointRenderer()   → pour Point
 *
 * RenderContext conserve la fabrique active et l'expose aux Shape.
 * Les Shape appellent RenderContext.getFactory() et choisissent
 * le bon renderer selon leur nature.
 */
public interface RenderFactory {

    /** Retourne un renderer adapté aux formes linéaires (Line). */
    ShapeRenderer createLineRenderer();

    /** Retourne un renderer adapté aux formes surfaciques (Circle, Rectangle, Donut). */
    ShapeRenderer createSurfaceRenderer();

    /** Retourne un renderer adapté aux points (Point). */
    ShapeRenderer createPointRenderer();

    /**
     * Produit de l'Abstract Factory : comportement de rendu atomique.
     * Chaque fabrique produit sa propre implémentation de cette interface.
     */
    interface ShapeRenderer {
        /** Applique les effets AVANT le dessin (lineWidth, dashes, shadow…). */
        void applyBefore(GraphicsContext gc);
        /** Remet le contexte graphique à l'état neutre APRÈS le dessin. */
        void applyAfter(GraphicsContext gc);
        /** Indique si le remplissage (fillOval, fillRect…) doit être omis. */
        boolean skipFill();
    }
}
