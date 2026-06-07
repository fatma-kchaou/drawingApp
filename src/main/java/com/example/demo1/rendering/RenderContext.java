package com.example.demo1.rendering;

/**
 * Singleton — Contexte de rendu global (inchangé dans son rôle).
 *
 * Avant : stockait une RenderStrategy (Strategy Pattern).
 * Maintenant : stocke une RenderFactory (Abstract Factory Pattern).
 *
 * Les Shape appellent toujours RenderContext.getFactory()
 * et choisissent le bon renderer via createLineRenderer(),
 * createSurfaceRenderer() ou createPointRenderer().
 *
 * DrawingFrame change la fabrique via RenderContext.set(new FlatRenderFactory()).
 */
public class RenderContext {

    // ── Singleton ────────────────────────────────────────────────────────────
    private static final RenderContext instance = new RenderContext();

    private RenderContext() {}

    public static RenderContext getInstance() {
        return instance;
    }

    // ── Fabrique active (2D par défaut) ──────────────────────────────────────
    private static RenderFactory current = new FlatRenderFactory();

    /** Retourne la fabrique de rendu actuellement active. */
    public static RenderFactory getFactory() {
        return current;
    }

    /** Change la fabrique active (appelé par DrawingFrame lors du switch 1D/2D/3D). */
    public static void set(RenderFactory factory) {
        current = factory;
    }

    // ── Helpers pratiques utilisés par les Shape ────────────────────────────

    /** Raccourci : renderer pour formes linéaires (Line). */
    public static RenderFactory.ShapeRenderer lineRenderer() {
        return current.createLineRenderer();
    }

    /** Raccourci : renderer pour formes surfaciques (Circle, Rectangle, Donut). */
    public static RenderFactory.ShapeRenderer surfaceRenderer() {
        return current.createSurfaceRenderer();
    }

    /** Raccourci : renderer pour points (Point). */
    public static RenderFactory.ShapeRenderer pointRenderer() {
        return current.createPointRenderer();
    }
}
