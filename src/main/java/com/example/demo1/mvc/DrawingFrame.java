package com.example.demo1.mvc;

import com.example.demo1.observer.TimeDisplay;
import com.example.demo1.model.TimeData;
import com.example.demo1.rendering.RenderContext;
import com.example.demo1.rendering.WireframeRenderStrategy;
import com.example.demo1.rendering.FlatRenderStrategy;
import com.example.demo1.rendering.ShadowRenderStrategy;
import com.example.demo1.strategy.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalTime;

public class DrawingFrame extends BorderPane {

    private DrawingController controller;
    private DrawingView view = new DrawingView();

    // Shape selector
    private ToggleGroup shapeGroup   = new ToggleGroup();

    // Dimension mode selector (1D/2D/3D)
    private ToggleGroup dimGroup     = new ToggleGroup();

    // Color pickers
    private ColorPicker strokePicker = new ColorPicker(Color.web("#2c3e50"));
    private ColorPicker fillPicker   = new ColorPicker(Color.web("#74b9ff"));

    // Toolbar buttons
    private Button btnUndo = new Button();
    private Button btnRedo = new Button();
    private Button btnClear = new Button();

    // Counter
    private Label counterLabel = new Label("Shapes: 0");

    // Clock
    private Label clockLabel   = new Label("00:00:00");
    private TimeData timeData  = new TimeData();
    private TimeDisplay timeDisplay;
    private ComboBox<String> strategyCombo = new ComboBox<>();

    private MenuBar menuBar    = new MenuBar();

    public DrawingFrame() {
        buildTopBar();
        buildLeftPanel();
        buildRightPanel();
        buildClock();
        setCenter(view);
        getStyleClass().add("root-pane");
    }

    // ===================================================
    // TOP BAR: MenuBar + ToolBar
    // ===================================================
    @SuppressWarnings("unused")
	private void buildTopBar() {
        // --- Menu ---
        Menu menuDb = new Menu("  💾  Fichier");
        MenuItem save = new MenuItem("💾  Sauvegarder le dessin");
        MenuItem load = new MenuItem("📂  Charger un dessin");
        save.setOnAction(e -> {
            TextInputDialog dlg = new TextInputDialog("drawing");
            dlg.setTitle("Sauvegarder");
            dlg.setHeaderText("Nom du dessin :");
            dlg.showAndWait().ifPresent(name -> {
                if (!name.trim().isEmpty()) controller.saveToDatabase(name.trim());
            });
        });
        load.setOnAction(e -> controller.loadFromDatabase());
        menuDb.getItems().addAll(save, new SeparatorMenuItem(), load);
        menuBar.getMenus().add(menuDb);
        menuBar.getStyleClass().add("menu-bar");

        // --- Buttons ---
        btnUndo.setGraphic(new FontIcon("fas-undo"));
        btnUndo.getStyleClass().addAll("tool-btn", "undo-button");
        btnUndo.setTooltip(new Tooltip("Undo (Ctrl+Z)"));
        btnUndo.setOnAction(e -> controller.Undo());

        btnRedo.setGraphic(new FontIcon("fas-redo"));
        btnRedo.getStyleClass().addAll("tool-btn", "redo-button");
        btnRedo.setTooltip(new Tooltip("Redo (Ctrl+Y)"));
        btnRedo.setOnAction(e -> controller.Redo());

        btnClear.setGraphic(new FontIcon("fas-trash-alt"));
        btnClear.getStyleClass().addAll("tool-btn", "clear-button");
        btnClear.setTooltip(new Tooltip("Clear all shapes"));
        btnClear.setOnAction(e -> {
            controller.getModel().clearShapes();
            view.paint();
            updateCounter();
        });

        // Counter badge
        counterLabel.getStyleClass().add("counter-label");

        // --- 1D / 2D / 3D radio group ---
        HBox dimBox = new HBox(4);
        dimBox.setAlignment(Pos.CENTER_LEFT);
        Label dimLabel = new Label("Mode:");
        dimLabel.getStyleClass().add("dim-label");
        dimBox.getChildren().add(dimLabel);

        for (String d : new String[]{"1D", "2D", "3D"}) {
            ToggleButton tb = new ToggleButton(d);
            tb.setToggleGroup(dimGroup);
            tb.setUserData(d);
            tb.getStyleClass().add("dim-button");
            if (d.equals("2D")) tb.setSelected(true);
            tb.setOnAction(e -> applyDimMode(d));
            dimBox.getChildren().add(tb);
        }

        Separator sep1 = new Separator(); sep1.setOrientation(javafx.geometry.Orientation.VERTICAL);
        Separator sep2 = new Separator(); sep2.setOrientation(javafx.geometry.Orientation.VERTICAL);

        ToolBar toolbar = new ToolBar(
                btnUndo, btnRedo, sep1, btnClear, sep2,
                dimBox,
                new Separator(),
                counterLabel
        );
        toolbar.getStyleClass().add("tool-bar");

        setTop(new VBox(menuBar, toolbar));
    }

    private void applyDimMode(String mode) {
        switch (mode) {
            case "1D": RenderContext.set(new WireframeRenderStrategy()); break;
            case "3D": RenderContext.set(new ShadowRenderStrategy());    break;
            default:   RenderContext.set(new FlatRenderStrategy());
        }
        view.paint();
    }

    // ===================================================
    // LEFT PANEL: Shape selector
    // ===================================================
    private void buildLeftPanel() {
        VBox panel = new VBox(4);
        panel.setPadding(new Insets(16, 12, 16, 12));
        panel.getStyleClass().add("left-panel");
        panel.setPrefWidth(140);

        Label title = new Label("FORMES");
        title.getStyleClass().add("panel-title");

        panel.getChildren().add(title);
        panel.getChildren().add(new Separator());
        panel.getChildren().add(new javafx.scene.layout.Region() {{ setMinHeight(4); }});

        String[] shapes = {"Point", "Line", "Circle", "Rectangle", "Donut", "Hexagon"};

        for (String s : shapes) {
            RadioButton rb = new RadioButton(s);
            rb.setToggleGroup(shapeGroup);
            rb.setUserData(s);
            rb.getStyleClass().add("shape-button");
            panel.getChildren().add(rb);
        }

        setLeft(panel);
    }

    // ===================================================
    // RIGHT PANEL: Colors
    // ===================================================
    private void buildRightPanel() {
        VBox panel = new VBox(8);
        panel.setPadding(new Insets(16, 14, 16, 14));
        panel.getStyleClass().add("right-panel");
        panel.setPrefWidth(165);

        Label colorsTitle = new Label("COULEURS");
        colorsTitle.getStyleClass().add("panel-title");

        Label strokeLbl = new Label("CONTOUR");
        strokeLbl.getStyleClass().add("sub-label");
        strokePicker.getStyleClass().add("color-picker-custom");
        strokePicker.setMaxWidth(Double.MAX_VALUE);

        Label fillLbl = new Label("REMPLISSAGE");
        fillLbl.getStyleClass().add("sub-label");
        fillPicker.getStyleClass().add("color-picker-custom");
        fillPicker.setMaxWidth(Double.MAX_VALUE);

        panel.getChildren().addAll(
                colorsTitle,
                new Separator(),
                new javafx.scene.layout.Region() {{ setMinHeight(2); }},
                strokeLbl, strokePicker,
                new javafx.scene.layout.Region() {{ setMinHeight(4); }},
                fillLbl,   fillPicker
        );

        setRight(panel);
    }

    // ===================================================
    // CLOCK (bottom bar)
    // ===================================================
    @SuppressWarnings("unused")
	private void buildClock() {
        // Strategy combo
        strategyCombo.getItems().addAll(
                "⏱  Standard (HH:MM:SS)",
                "🕐  Emoji",
                "🌅  AM/PM",
                "🎖  Military",
                "📟  Legacy (HH-MM-SS)"
        );
        strategyCombo.getSelectionModel().selectFirst();
        strategyCombo.getStyleClass().add("strategy-combo");
        strategyCombo.setPrefWidth(200);

        // TimeDisplay observer
        timeDisplay = new TimeDisplay(clockLabel, new StandardTimeFormatStrategy());
        timeData.addObserver(timeDisplay);

        strategyCombo.setOnAction(e -> {
            int idx = strategyCombo.getSelectionModel().getSelectedIndex();
            TimeFormatStrategy strat;
            switch (idx) {
                case 1: strat = new EmojiTimeFormatStrategy();    break;
                case 2: strat = new AmPmTimeFormatStrategy();     break;
                case 3: strat = new MilitaryTimeFormatStrategy(); break;
                case 4: strat = new LegacyTimeFormatStrategy();   break;
                default: strat = new StandardTimeFormatStrategy();
            }
            timeDisplay.setStrategy(strat);
            // Force refresh
            LocalTime now = LocalTime.now();
            timeData.setTime(now.getHour(), now.getMinute(), now.getSecond());
        });

        clockLabel.getStyleClass().add("clock-label");

        // Live ticker — updates every second
        Timeline ticker = new Timeline(
                new KeyFrame(Duration.seconds(1), ev -> {
                    LocalTime t = LocalTime.now();
                    timeData.setTime(t.getHour(), t.getMinute(), t.getSecond());
                })
        );
        ticker.setCycleCount(Animation.INDEFINITE);
        ticker.play();

        HBox bottomBar = new HBox(14);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(8, 18, 8, 18));
        bottomBar.getStyleClass().add("bottom-bar");

        Label clockIcon = new Label("◉");
        clockIcon.setStyle("-fx-font-size: 14px; -fx-text-fill: #22d3ee;");

        Label formatLabel = new Label("FORMAT");
        formatLabel.getStyleClass().add("sub-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottomBar.getChildren().addAll(clockIcon, clockLabel, spacer, formatLabel, strategyCombo);
        setBottom(bottomBar);
    }

    // ===================================================
    // PUBLIC API
    // ===================================================
    public void updateCounter() {
        int count = controller != null ? controller.getModel().getShapeCount() : 0;
        counterLabel.setText("Shapes: " + count);
    }

    public String getSelectedShapeType() {
        return shapeGroup.getSelectedToggle() != null
                ? shapeGroup.getSelectedToggle().getUserData().toString()
                : null;
    }

    public Color getStrokeColor() { return strokePicker.getValue(); }
    public Color getFillColor()   { return fillPicker.getValue(); }
    public DrawingView getView()  { return view; }

    public void setController(DrawingController controller) {
        this.controller = controller;
        view.setController(controller);
        updateCounter();
    }
}
