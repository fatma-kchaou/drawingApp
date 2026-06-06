package com.example.demo1.rendering;

/**
 * Contexte de rendu global — remplace le champ static dans Shape.
 *
 * Contient la RenderStrategy active (1D/2D/3D).
 * Les Shapes l'interrogent pour savoir comment se dessiner,
 * sans connaître ni stocker la logique de rendu elles-mêmes.
 */
public class RenderContext {

    private static RenderStrategy current = new FlatRenderStrategy(); // défaut 2D

    public static RenderStrategy get()                          { return current; }
    public static void           set(RenderStrategy strategy)  { current = strategy; }

    /** Indique si le mode actuel est wireframe (pas de remplissage) */
    public static boolean isWireframe() {
        return current instanceof WireframeRenderStrategy;
    }
}
