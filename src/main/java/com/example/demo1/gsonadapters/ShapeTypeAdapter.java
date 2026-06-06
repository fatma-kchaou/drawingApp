package com.example.demo1.gsonadapters;

import com.google.gson.*;
import com.example.demo1.shapes.*;
import com.example.demo1.adapter.HexagonAdapter;
import com.example.demo1.decorator.FillColorDecorator;
import com.example.demo1.decorator.ShapeDecorator;
import com.example.demo1.decorator.StrokeColorDecorator;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

/**
 * Gson adapter for polymorphic Shape serialization.
 *
 * Strategy:
 *  - On SERIALIZE: unwrap all decorators to find the raw shape,
 *    save its type + geometry + the two decoration colors separately.
 *  - On DESERIALIZE: rebuild the raw shape from its type + geometry,
 *    then rewrap with FillColorDecorator + StrokeColorDecorator.
 *
 * JSON format per shape:
 * {
 *   "type"   : "Circle",
 *   "stroke" : { "red":0,"green":0,"blue":0,"opacity":1 },
 *   "fill"   : { "red":0.45,"green":0.72,"blue":1,"opacity":1 },
 *   "data"   : { ...geometry fields... }
 * }
 */
public class ShapeTypeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {

    // ── Serialize ──────────────────────────────────────────────────────────────
    @Override
    public JsonElement serialize(Shape src, Type typeOfSrc, JsonSerializationContext ctx) {
        JsonObject out = new JsonObject();

        // 1. Extract decoration colors by unwrapping the decorator chain
        Color strokeColor = Color.BLACK;
        Color fillColor   = Color.LIGHTGRAY;

        Shape current = src;
        while (current instanceof ShapeDecorator) {
            if (current instanceof StrokeColorDecorator) {
                strokeColor = ((StrokeColorDecorator) current).getColor();
            } else if (current instanceof FillColorDecorator) {
                fillColor = ((FillColorDecorator) current).getColor();
            }
            current = ((ShapeDecorator) current).getDecoratedShape();
        }
        // current is now the raw (undecorated) shape

        // 2. Determine type name
        String typeName = current.getClass().getSimpleName();
        if (current instanceof HexagonAdapter) typeName = "Hexagon";
        out.addProperty("type", typeName);

        // 3. Serialize colors
        out.add("stroke", serializeColor(strokeColor));
        out.add("fill",   serializeColor(fillColor));

        // 4. Serialize geometry of the raw shape
        out.add("data", serializeGeometry(current, ctx));

        return out;
    }

    // ── Deserialize ────────────────────────────────────────────────────────────
    @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String typeName = obj.get("type").getAsString();
        Color stroke    = deserializeColor(obj.getAsJsonObject("stroke"));
        Color fill      = deserializeColor(obj.getAsJsonObject("fill"));
        JsonObject data = obj.getAsJsonObject("data");

        // 1. Rebuild raw shape
        Shape raw = buildRawShape(typeName, data, ctx);

        // 2. Rewrap with decorators (same order as in ShapeCreator.assembleShape)
        raw = new FillColorDecorator(raw, fill);
        raw = new StrokeColorDecorator(raw, stroke);

        return raw;
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private Shape buildRawShape(String type, JsonObject data, JsonDeserializationContext ctx) {
        switch (type) {
            case "Point": {
                double x = data.get("x").getAsDouble();
                double y = data.get("y").getAsDouble();
                return new Point(x, y);
            }
            case "Line": {
                JsonObject s = data.getAsJsonObject("start");
                JsonObject e = data.getAsJsonObject("end");
                return new Line(
                    new Point(s.get("x").getAsDouble(), s.get("y").getAsDouble()),
                    new Point(e.get("x").getAsDouble(), e.get("y").getAsDouble())
                );
            }
            case "Rectangle": {
                return new Rectangle(
                    data.get("x").getAsDouble(),
                    data.get("y").getAsDouble(),
                    data.get("width").getAsDouble(),
                    data.get("height").getAsDouble()
                );
            }
            case "Circle": {
                JsonObject c = data.getAsJsonObject("center");
                double r     = data.get("radius").getAsDouble();
                return new Circle(
                    new Point(c.get("x").getAsDouble(), c.get("y").getAsDouble()), r
                );
            }
            case "Donut": {
                JsonObject c  = data.getAsJsonObject("center");
                double outer  = data.get("radius").getAsDouble();
                double inner  = data.get("innerRadius").getAsDouble();
                return new Donut(
                    new Point(c.get("x").getAsDouble(), c.get("y").getAsDouble()),
                    inner, outer
                );
            }
            case "Hexagon": {
                double cx = data.get("cx").getAsDouble();
                double cy = data.get("cy").getAsDouble();
                int    r  = data.get("r").getAsInt();
                return new HexagonAdapter(new Point(cx, cy), r, Color.LIGHTPINK, Color.BLACK);
            }
            default:
                throw new JsonParseException("Unknown shape type for deserialization: " + type);
        }
    }

    private JsonElement serializeGeometry(Shape shape, JsonSerializationContext ctx) {
        JsonObject d = new JsonObject();
        if (shape instanceof Donut) {
            Donut donut = (Donut) shape;
            d.add("center", pointJson(donut.getCenter()));
            d.addProperty("radius",      donut.getRadius());
            d.addProperty("innerRadius", donut.getInnerRadius());
        } else if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            d.add("center", pointJson(c.getCenter()));
            d.addProperty("radius", c.getRadius());
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            d.addProperty("x",      r.getX());
            d.addProperty("y",      r.getY());
            d.addProperty("width",  r.getWidth());
            d.addProperty("height", r.getHeight());
        } else if (shape instanceof Line) {
            Line l = (Line) shape;
            d.add("start", pointJson(l.getStart()));
            d.add("end",   pointJson(l.getEnd()));
        } else if (shape instanceof Point) {
            Point p = (Point) shape;
            d.addProperty("x", p.getX());
            d.addProperty("y", p.getY());
        } else if (shape instanceof HexagonAdapter) {
            HexagonAdapter h = (HexagonAdapter) shape;
            d.addProperty("cx", h.getCenter().getX());
            d.addProperty("cy", h.getCenter().getY());
            d.addProperty("r",  h.getRadius());
        }
        return d;
    }

    private JsonObject pointJson(Point p) {
        JsonObject o = new JsonObject();
        o.addProperty("x", p.getX());
        o.addProperty("y", p.getY());
        return o;
    }

    private JsonObject serializeColor(Color c) {
        JsonObject o = new JsonObject();
        o.addProperty("red",     c.getRed());
        o.addProperty("green",   c.getGreen());
        o.addProperty("blue",    c.getBlue());
        o.addProperty("opacity", c.getOpacity());
        return o;
    }

    private Color deserializeColor(JsonObject o) {
        if (o == null) return Color.BLACK;
        return new Color(
            o.get("red").getAsDouble(),
            o.get("green").getAsDouble(),
            o.get("blue").getAsDouble(),
            o.get("opacity").getAsDouble()
        );
    }
}
