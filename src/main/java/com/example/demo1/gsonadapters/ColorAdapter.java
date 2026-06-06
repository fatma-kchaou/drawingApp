package com.example.demo1.gsonadapters;


import com.google.gson.*;
import javafx.scene.paint.Color;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("red", src.getRed());
        obj.addProperty("green", src.getGreen());
        obj.addProperty("blue", src.getBlue());
        obj.addProperty("opacity", src.getOpacity());
        return obj;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        double r = obj.get("red").getAsDouble();
        double g = obj.get("green").getAsDouble();
        double b = obj.get("blue").getAsDouble();
        double a = obj.get("opacity").getAsDouble();
        return new Color(r, g, b, a);
    }
}