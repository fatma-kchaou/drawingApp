package com.example.demo1; // This Main class is in the 'com.example.demo1' package

import javafx.application.Application;
import com.example.demo1.mvc.DrawingApp; // This imports DrawingApp from the 'com.example.demo1.mvc' package

public class Main {
    public static void main(String[] args) {
        // This launches your DrawingApp, which extends javafx.application.Application
        Application.launch(DrawingApp.class, args);
    }
}