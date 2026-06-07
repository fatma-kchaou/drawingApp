package com.example.demo1.rendering;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

/**
 * Concrete Factory 3D — rendu avec illusion de profondeur.
 *
 * Produit une famille de rendus "3D simulé" :
 *   - remplissage activé (skipFill = false)
 *   - DropShadow externe sur les surfaces (donne du relief)
 *   - InnerShadow sur les points (effet sphère)
 *   - Épaisseur de trait renforcée
 *
 * Chaque createXxxRenderer() produit un ShapeRenderer spécialisé,
 * illustrant l'intérêt de l'Abstract Factory : la famille 3D est
 * cohérente mais chaque produit est adapté à son type de forme.
 */
public class ShadowRenderFactory implements RenderFactory {

    @Override
    public ShapeRenderer createLineRenderer() {
        // Ligne 3D : DropShadow léger + trait épais = impression de tube
        return new ShadowRenderer(2.5,
                buildDropShadow(3, 3, 5, Color.color(0, 0, 0, 0.4)));
    }

    @Override
    public ShapeRenderer createSurfaceRenderer() {
        // Surface 3D : DropShadow fort + offset marqué = relief prononcé
        return new ShadowRenderer(2.0,
                buildDropShadow(4, 4, 8, Color.color(0, 0, 0, 0.45)));
    }

    @Override
    public ShapeRenderer createPointRenderer() {
        // Point 3D : InnerShadow = effet boule/sphère
        return new PointShadowRenderer();
    }

    // ── Fabrique de DropShadow (helper interne) ─────────────────────────────
    private static DropShadow buildDropShadow(double ox, double oy, double radius, Color color) {
        DropShadow ds = new DropShadow();
        ds.setOffsetX(ox);
        ds.setOffsetY(oy);
        ds.setRadius(radius);
        ds.setColor(color);
        return ds;
    }

    // ── Produit concret pour Line et Surface en 3D ─────────────────────────
    private static class ShadowRenderer implements ShapeRenderer {
        private final double lineWidth;
        private final DropShadow shadow;

        ShadowRenderer(double lineWidth, DropShadow shadow) {
            this.lineWidth = lineWidth;
            this.shadow    = shadow;
        }

        @Override
        public void applyBefore(GraphicsContext gc) {
            gc.save();
            gc.setLineWidth(lineWidth);
            gc.setLineDashes(0);
            gc.setEffect(shadow);
        }

        @Override
        public void applyAfter(GraphicsContext gc) {
            gc.restore();
        }

        @Override
        public boolean skipFill() {
            return false;
        }
    }

    // ── Produit concret spécialisé pour Point en 3D ────────────────────────
    private static class PointShadowRenderer implements ShapeRenderer {

        @Override
        public void applyBefore(GraphicsContext gc) {
            gc.save();
            gc.setLineWidth(1.0);
            gc.setLineDashes(0);
            // InnerShadow = effet sphère sur le point
            InnerShadow inner = new InnerShadow();
            inner.setOffsetX(-1.5);
            inner.setOffsetY(-1.5);
            inner.setRadius(4);
            inner.setColor(Color.color(1, 1, 1, 0.6));
            gc.setEffect(inner);
        }

        @Override
        public void applyAfter(GraphicsContext gc) {
            gc.restore();
        }

        @Override
        public boolean skipFill() {
            return false;   // le point en 3D est toujours rempli
        }
    }
}
