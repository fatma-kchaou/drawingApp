package com.example.demo1.gsonadapters;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;

public class GsonFactory {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Color.class, new ColorAdapter()) // <-- important
                .setPrettyPrinting()
                .create();
    }
}