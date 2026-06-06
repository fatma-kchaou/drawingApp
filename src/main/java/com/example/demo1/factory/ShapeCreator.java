package com.example.demo1.factory;

import com.example.demo1.decorator.FillColorDecorator;
import com.example.demo1.decorator.StrokeColorDecorator;
import com.example.demo1.shapes.Point;
import com.example.demo1.shapes.Shape;
import javafx.scene.paint.Color;

/**
 * ╔══════════════════════════════════════════════════════╗
 *  PATTERN : Factory Method (GoF)
 *
 *  ShapeCreator  = Creator abstrait
 *    └─ déclare factoryMethod() → produit une Shape brute
 *    └─ contient la logique métier commune (décorateurs)
 *       dans assembleShape()
 *
 *  Sous-classes concrètes (PointCreator, LineCreator…)
 *    └─ implémentent factoryMethod() avec leurs propres
 *       paramètres géométriques
 * ╚══════════════════════════════════════════════════════╝
 */
public abstract class ShapeCreator {

    // ─────────────────────────────────────────────────
    // THE FACTORY METHOD  (override in each subclass)
    // ─────────────────────────────────────────────────
    /**
     * Crée et retourne la Shape brute (sans décorateurs).
     * Chaque Creator concret sait comment construire SA shape
     * à partir des deux points fournis par l'utilisateur.
     */
    public abstract Shape factoryMethod(Point start, Point end);

    // ─────────────────────────────────────────────────
    // TEMPLATE METHOD  (logique commune, non surchargée)
    // ─────────────────────────────────────────────────
    /**
     * Appelle factoryMethod() puis applique les décorateurs.
     * C'est LA méthode que le Controller appelle.
     */
    public final Shape assembleShape(Point start, Point end,
                                     Color strokeColor, Color fillColor) {
        // 1. Créer la shape via la Factory Method
        Shape shape = factoryMethod(start, end);

        // 2. Appliquer le décorateur de remplissage
        shape = new FillColorDecorator(shape, fillColor);

        // 3. Appliquer le décorateur de contour
        shape = new StrokeColorDecorator(shape, strokeColor);

        return shape;
    }

    // ─────────────────────────────────────────────────
    // STATIC REGISTRY  (résoudre le Creator par nom)
    // ─────────────────────────────────────────────────
    /**
     * Retourne le Creator adapté à un type de shape donné.
     * Evite un switch dans le Controller.
     */
    public static ShapeCreator forType(String type) {
        switch (type) {
            case "Point":     return new PointCreator();
            case "Line":      return new LineCreator();
            case "Rectangle": return new RectangleCreator();
            case "Circle":    return new CircleCreator();
            case "Donut":     return new DonutCreator();
            case "Hexagon":   return new HexagonCreator();
            default:
                throw new IllegalArgumentException("No creator registered for type: " + type);
        }
    }
}
