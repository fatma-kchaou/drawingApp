module com.example.demo1 {
    // Required JavaFX modules (from your pom.xml)
	requires transitive javafx.controls;
	requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.web;    // Ajouté
    requires javafx.swing;   // Ajouté
    requires javafx.media;   // Ajouté
    // Required standard Java modules
    requires java.desktop; // Pour AWT.Color si utilisé dans les formes par exemple
    requires java.sql;     // Pour SQLite JDBC

    // Required external libraries
    requires transitive com.google.gson;
    requires org.xerial.sqlitejdbc; // Le nom du module du connecteur SQLite

    // Optional: other libraries from your pom.xml, if used in your code
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    
    // Si vous utilisez fxgl, assurez-vous que ce module est correctement défini
    // requires com.almasb.fxgl.all;

    // --- Exports (rend les packages disponibles pour d'autres modules) ---
    exports com.example.demo1;             // Pour la classe Main/HelloApplication
    exports com.example.demo1.mvc;         // Votre architecture MVC principale
    exports com.example.demo1.shapes;      // Vos interfaces/classes de base pour les formes
    exports com.example.demo1.adapter;     // Si HexagonAdapter est dans ce package
    exports com.example.demo1.command;
    exports com.example.demo1.dao;
    exports com.example.demo1.decorator;
    exports com.example.demo1.factory;
    exports com.example.demo1.model;       // Si d'autres classes importantes sont ici
    exports com.example.demo1.observer;
    exports com.example.demo1.strategy;
    exports com.example.demo1.gsonadapters; // Votre adaptateur Gson

    // --- Opens (permet à la réflexion d'accéder aux membres des packages) ---
    // C'est CRUCIAL pour GSON afin d'accéder aux champs et constructeurs
    // de vos classes lors de la sérialisation/désérialisation.
    opens com.example.demo1 to javafx.fxml, javafx.graphics, com.google.gson;
    opens com.example.demo1.mvc to javafx.fxml, javafx.graphics, com.google.gson;

    // OUVREZ TOUS LES PACKAGES QUI CONTIENNENT DES CLASSES DE FORMES CONCRÈTES À GSON !
    opens com.example.demo1.shapes to com.google.gson;     // Point, Line, Rectangle, Circle, Donut, etc.
    opens com.example.demo1.adapter to com.google.gson;    // Pour HexagonAdapter
    opens com.example.demo1.model to com.google.gson;      // Si certaines formes sont dans 'model'
    
    // Autres opens (selon vos besoins spécifiques ou pour d'autres frameworks)
    opens com.example.demo1.dao to com.google.gson;
    opens com.example.demo1.strategy to com.google.gson;
    opens com.example.demo1.gsonadapters to com.google.gson; // Utile pour être complet
}