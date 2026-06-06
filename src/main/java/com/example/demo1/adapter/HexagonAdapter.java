package com.example.demo1.adapter;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.SurfaceShape;

public class HexagonAdapter extends SurfaceShape {
    private LegacyHexagon hexagon;

    public HexagonAdapter(Point center, int r, Color insideColor, Color outsideColor) {
        this.hexagon = new LegacyHexagon((int)center.getX(), (int)center.getY(), r);
        this.hexagon.setAreaColor(insideColor);
        this.hexagon.setBorderColor(outsideColor);
        this.setInsideColor(insideColor);
        this.setOutsideColor(outsideColor);
    }

    public HexagonAdapter() {
        this.hexagon = new LegacyHexagon(0, 0, 30);
    }

    // Adapter methods - translating between interfaces
    @Override
    public void draw(GraphicsContext gc) {
        applyDimensionEffect(gc);
        hexagon.paint(gc); // Adapting draw() to paint()
        resetDimensionEffect(gc);
    }

    @Override
    public void fill(GraphicsContext gc) {
        // Fill is handled in the paint method
        hexagon.paint(gc);
    }

    @Override
    public boolean contains(double x, double y) {
        return hexagon.doesContain((int)x, (int)y); // Adapting contains() to doesContain()
    }

    @Override
    public void moveBy(int byX, int byY) {
        hexagon.setX(hexagon.getX() + byX);
        hexagon.setY(hexagon.getY() + byY);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        hexagon.setSelected(selected); // Sync selection state
    }

    @Override
    public boolean isSelected() {
        return super.isSelected();
    }

    public int compareTo(Object o) {
        if (o instanceof HexagonAdapter) {
            HexagonAdapter other = (HexagonAdapter) o;
            return Integer.compare(this.hexagon.getR(), other.hexagon.getR());
        }
        return 0;
    }

    // Adapting color methods
    @Override
    public Color getInsideColor() {
        return hexagon.getAreaColor(); // Adapting getInsideColor() to getAreaColor()
    }

    @Override
    public void setInsideColor(Color insideColor) {
        super.setInsideColor(insideColor);
        hexagon.setAreaColor(insideColor); // Adapting setInsideColor() to setAreaColor()
    }

    public Color getOutsideColor() {
        return hexagon.getBorderColor();
    }

    public void setOutsideColor(Color outsideColor) {
        super.setOutsideColor(outsideColor);
        hexagon.setBorderColor(outsideColor);
    }

    // Hexagon-specific methods
    public int getRadius() {
        return hexagon.getR();
    }

    public void setRadius(int radius) {
        hexagon.setR(radius);
    }

    public Point getCenter() {
        return new Point(hexagon.getX(), hexagon.getY());
    }

    public void setCenter(Point center) {
        hexagon.setX((int)center.getX());
        hexagon.setY((int)center.getY());
    }

    public void setHexagon(Point center, int r, Color insideColor, Color outsideColor) {
        this.hexagon = new LegacyHexagon((int)center.getX(), (int)center.getY(), r);
        this.hexagon.setAreaColor(insideColor);
        this.hexagon.setBorderColor(outsideColor);
        hexagon.setSelected(true);
    }

    public LegacyHexagon getHexagon() {
        return hexagon;
    }

    public double area() {
        // Approximate area using circle formula (for simplicity)
        return Math.PI * hexagon.getR() * hexagon.getR();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HexagonAdapter) {
            HexagonAdapter other = (HexagonAdapter) obj;
            return this.getCenter().equals(other.getCenter()) &&
                    this.getRadius() == other.getRadius() &&
                    this.getInsideColor().equals(other.getInsideColor()) &&
                    this.getOutsideColor().equals(other.getOutsideColor());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hexagon: (" + getCenter().getX() + ", " + getCenter().getY() +
                "), Radius=" + getRadius() +
                ", Inside Color: " + getInsideColor() +
                ", Outside Color: " + getOutsideColor();
    }

    @Override
    public HexagonAdapter clone() {
        HexagonAdapter cloned = new HexagonAdapter();
        cloned.hexagon = new LegacyHexagon(
                this.hexagon.getX(),
                this.hexagon.getY(),
                this.hexagon.getR()
        );
        cloned.hexagon.setAreaColor(this.hexagon.getAreaColor());
        cloned.hexagon.setBorderColor(this.hexagon.getBorderColor());
        cloned.hexagon.setSelected(this.hexagon.isSelected());
        return cloned;
    }
}
