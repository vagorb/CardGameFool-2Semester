package game;

import appearance.*;
import javafx.application.*;
import javafx.beans.binding.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Menu extends Application {
    private static boolean fullscreenBoolean = false;
    private Buttons buttons = new Buttons();
    private BackgroundSetter background = new BackgroundSetter();
    private double windowHeight;
    private double windowWidth;
    private Scene menuScene;

    @Override
    public void start(Stage window) {
        /// avamenüü
        StackPane openingStackpane = new StackPane();
        StackPane settingsStackpane = new StackPane();
        StackPane playStackpane = new StackPane();
        menuScene = new Scene(openingStackpane, 1280, 720);
        windowWidth = menuScene.getWidth();
        windowHeight = menuScene.getHeight();

        /// nupud
        Button exitButton = buttons.exit();
        Button backButton = buttons.back();
        Button settingsButton = buttons.settings();
        Button playButton = buttons.play();
        Button fullscreenButton = buttons.fullscreen();

        // akna sätted
        window.setX(0.0);
        window.setY(0.0);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Menu.exe");
        window.setScene(menuScene);
        window.setResizable(false);
        window.show();
        window.hide();

        /// menüü riba
        VBox menu = new VBox(25);
        menu.getChildren().addAll(playButton, settingsButton, exitButton);
        menu.setMaxSize(windowWidth / 2, windowHeight / 1.5);
        menu.setAlignment(Pos.CENTER);

        VBox settingsMenu = new VBox(25);
        settingsMenu.getChildren().addAll(fullscreenButton, backButton);
        settingsMenu.setPadding(new Insets(100, 0, 0, 100));

        settingsStackpane.setAlignment(Pos.BOTTOM_LEFT);
        settingsStackpane.getChildren().addAll(settingsMenu);
        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(menu);

        /// nuppude ja klahvide tegevused
        menuScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.F) {
                fullscreenBoolean = !fullscreenBoolean;
                window.setFullScreen(fullscreenBoolean);

                windowWidth = window.getScene().getWidth();
                windowHeight = window.getScene().getHeight();

                menu.setMaxSize(windowWidth / 2, windowHeight / 1.5);
            }
        });
        fullscreenButton.setOnAction(actionEvent -> {
            fullscreenBoolean = !fullscreenBoolean;
            window.setFullScreen(fullscreenBoolean);
            if (window.isFullScreen()) {
                fullscreenButton.setGraphic(new Icons("black").fullscreenExit());
            } else {
                fullscreenButton.setGraphic(new Icons("black").fullscreenEnter());
            }
            windowWidth = window.getScene().getWidth();
            windowHeight = window.getScene().getHeight();
            fullscreenButton.setPrefHeight(windowHeight / 20);
        });

        openingStackpane.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                window.close();
            }
        });

        exitButton.setOnAction(actionEvent -> window.close());
        backButton.setOnAction(actionEvent -> menuScene.setRoot(openingStackpane));
        settingsButton.setOnAction(actionEvent -> menuScene.setRoot(settingsStackpane));

        playButton.setOnAction(actionEvent -> {
            Play play = new Play();
            try {
                window.hide();
                play.setFullscreenStatus(fullscreenBoolean);
                play.setMenu(menuScene);
                play.start(window);
            } catch (Exception ignore) {
            }
        });

        /// tausta lisamine
        menuScene.setFill(Paint.valueOf("red"));
        openingStackpane.setBackground(background.setColor("green"));
        menu.setBackground(background.setColor(Color.NAVY));
        settingsStackpane.setBackground(background.setColor("lightgreen"));
        settingsMenu.setBackground(background.setColor("lightgreen"));

        /// nuppude suuruste omavaheline kopeerimine
        backButton.prefWidthProperty().bind(fullscreenButton.widthProperty());
        exitButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 10.0));
        exitButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 15.0));
        settingsButton.prefWidthProperty().bind(exitButton.widthProperty());

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
