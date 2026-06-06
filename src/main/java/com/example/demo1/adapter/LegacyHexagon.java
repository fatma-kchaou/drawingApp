package com.example.demo1.adapter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LegacyHexagon {
    private int x, y;
    private int r;
    private Color areaColor;
    private Color borderColor;
    private boolean selected;

    public LegacyHexagon(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.areaColor = Color.LIGHTPINK;
        this.borderColor = Color.BLACK;
        this.selected = false;
    }

    public void paint(GraphicsContext gc) {
        double[] xPoints = new double[6];
        double[] yPoints = new double[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            xPoints[i] = x + r * Math.cos(angle);
            yPoints[i] = y + r * Math.sin(angle);
        }

        gc.setFill(areaColor);
        gc.fillPolygon(xPoints, yPoints, 6);

        gc.setStroke(borderColor);
        gc.setLineWidth(selected ? 3 : 1);
        gc.strokePolygon(xPoints, yPoints, 6);
    }

    public boolean doesContain(int px, int py) {
        double distance = Math.sqrt(Math.pow(px - x, 2) + Math.pow(py - y, 2));
        return distance <= r;
    }

    // Getters and setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getR() { return r; }
    public void setR(int r) { this.r = r; }
    public Color getAreaColor() { return areaColor; }
    public void setAreaColor(Color areaColor) { this.areaColor = areaColor; }
    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}