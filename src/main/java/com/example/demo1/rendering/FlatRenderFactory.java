package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;

/**
 * Concrete Factory 2D — rendu plat solide (défaut).
 *
 * Produit une famille de rendus "2D standard" :
 *   - remplissage activé (skipFill = false)
 *   - contour plein, pas de pointillés
 *   - pas d'effet
 *   - épaisseur de trait différenciée selon le type de forme
 */
public class FlatRenderFactory implements RenderFactory {

    @Override
    public ShapeRenderer createLineRenderer() {
        return new FlatRenderer(2.0);        // ligne plus épaisse
    }

    @Override
    public ShapeRenderer createSurfaceRenderer() {
        return new FlatRenderer(1.5);        // contour moyen pour les surfaces
    }

    @Override
    public ShapeRenderer createPointRenderer() {
        return new FlatRenderer(1.0);        // trait fin, point se dessine en fill
    }

    // ── Produit concret pour le rendu 2D ───────────────────────────────────
    private static class FlatRenderer implements ShapeRenderer {
        private final double lineWidth;

        FlatRenderer(double lineWidth) {
            this.lineWidth = lineWidth;
        }

        @Override
        public void applyBefore(GraphicsContext gc) {
            gc.save();
            gc.setLineWidth(lineWidth);
            gc.setLineDashes(0);             // contour plein
            gc.setEffect(null);
        }

        @Override
        public void applyAfter(GraphicsContext gc) {
            gc.restore();
        }

        @Override
        public boolean skipFill() {
            return false;                    // 2D = remplissage activé
        }
    }
}
