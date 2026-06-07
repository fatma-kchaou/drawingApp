package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/**
 * Concrete Factory 1D — rendu filaire.
 *
 * Produit une famille cohérente de rendus "fil de fer" :
 *   - aucun remplissage (skipFill = true)
 *   - contour en pointillés
 *   - pas d'effet (DropShadow désactivé)
 */
public class WireframeRenderFactory implements RenderFactory {

    @Override
    public ShapeRenderer createLineRenderer() {
        return new WireframeRenderer(1.2);
    }

    @Override
    public ShapeRenderer createSurfaceRenderer() {
        return new WireframeRenderer(1.5);
    }

    @Override
    public ShapeRenderer createPointRenderer() {
        // Point en 1D : petit carré vide, très fin
        return new WireframeRenderer(1.0);
    }

    // ── Produit concret commun à toutes les formes en mode 1D ──────────────
    private static class WireframeRenderer implements ShapeRenderer {
        private final double lineWidth;

        WireframeRenderer(double lineWidth) {
            this.lineWidth = lineWidth;
        }

        @Override
        public void applyBefore(GraphicsContext gc) {
            gc.save();                       // isole le contexte graphique
            gc.setLineWidth(lineWidth);
            gc.setLineDashes(6, 4);          // pointillés caractéristiques du 1D
            gc.setEffect(null);
        }

        @Override
        public void applyAfter(GraphicsContext gc) {
            gc.restore();                    // remet tout l'état précédent
        }

        @Override
        public boolean skipFill() {
            return true;                     // 1D = jamais de remplissage
        }
    }
}
