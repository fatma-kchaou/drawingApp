package com.example.demo1.shapes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Extension de Shape pour les formes fermées avec surface colorée.
 * Utilisée par HexagonAdapter (via Adapter Pattern).
 */
public abstract class SurfaceShape extends Shape {
    private Color insideColor;
    private Color outsideColor;
    private boolean selected;

    public abstract void fill(GraphicsContext gc);

    public Color   getInsideColor()              { return insideColor; }
    public void    setInsideColor(Color c)        { this.insideColor = c; }
    public Color   getOutsideColor()             { return outsideColor; }
    public void    setOutsideColor(Color c)       { this.outsideColor = c; }
    public boolean isSelected()                  { return selected; }
    public void    setSelected(boolean selected)  { this.selected = selected; }
}
