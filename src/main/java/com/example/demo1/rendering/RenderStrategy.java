package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/**
 * Strategy Pattern pour le mode de rendu dimensionnel.
 * Extrait la logique JavaFX hors de la classe Shape (SRP).
 *
 * Shape ne doit pas savoir comment se dessiner en 1D/2D/3D —
 * c'est la responsabilité de la RenderStrategy.
 */
public interface RenderStrategy {
    /** Applique les effets visuels AVANT le dessin */
    void applyBefore(GraphicsContext gc);
    /** Remet le contexte graphique à l'état neutre APRÈS le dessin */
    void applyAfter(GraphicsContext gc);
}
