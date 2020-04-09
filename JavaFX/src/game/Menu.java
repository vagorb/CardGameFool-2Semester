package game;

import appearance.*;
import game.help.Buttons;
import javafx.application.*;
import javafx.beans.binding.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

import java.util.Arrays;

public class Menu extends Application{
    private static boolean fullscreenBoolean = false;
    private Buttons buttons = new Buttons();
    private BackgroundSetter background = new BackgroundSetter();
    private double windowHeight;
    private double windowWidth;
    private Scene menuScene;
    private int playerHumanCount = 1;
    private int playerAIcount = 0;

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

        /// peamenüü
        VBox menu = new VBox(25);
        Button startButton = buttons.play();
        Button settingsButton = buttons.settings();
        menu.getChildren().addAll(startButton, settingsButton, exitButton);
        menu.setMaxSize(windowWidth / 2, windowHeight / 1.5);
        menu.setAlignment(Pos.CENTER);

        /// vahemenüü
        Slider sliderAI = new Slider(0, 5, 0);
        Slider sliderHuman = new Slider(0, 5, 1);
        for (Slider slider : Arrays.asList(sliderAI, sliderHuman)) {
            slider.setBlockIncrement(1);
            slider.setMinorTickCount(0);
            slider.setMajorTickUnit(1);
            slider.setShowTickLabels(true);
            slider.setSnapToTicks(true);
            slider.getStylesheets().add(getClass().getResource("/css/slider.css").toString());
            slider.setOnMouseDragReleased(mouseEvent -> {
                playerHumanCount = (int) sliderHuman.getValue();
                playerAIcount = (int) sliderAI.getValue();
            });
        }
        VBox sliderMenu = new VBox(25);
        sliderMenu.getChildren().addAll(sliderAI, sliderHuman, playButton);
        sliderMenu.setMaxSize(windowWidth / 1.5, windowHeight / 2);
        sliderMenu.setAlignment(Pos.CENTER);

        /// sätete menu
        VBox settingsMenu = new VBox(25);
        settingsMenu.getChildren().addAll(fullscreenButton, backButton);
        settingsMenu.setPadding(new Insets(100, 0, 0, 100));

        settingsStackpane.setAlignment(Pos.BOTTOM_LEFT);
        settingsStackpane.getChildren().addAll(settingsMenu);
        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(menu);
        playStackpane.getChildren().add(sliderMenu);

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
        startButton.setOnAction(actionEvent -> menuScene.setRoot(playStackpane));

        sliderAI.setOnMouseExited(mouseEvent -> {
            if (sliderAI.getValue() + sliderHuman.getValue() > 5) {
                sliderHuman.setValue(5 - (int) sliderAI.getValue());
            } else if (sliderAI.getValue() + sliderHuman.getValue() == 0) {
                sliderHuman.setValue(1);
            }
        });
        sliderHuman.setOnMouseExited(mouseEvent -> {
            if (sliderAI.getValue() + sliderHuman.getValue() > 5) {
                sliderAI.setValue(5 - (int) sliderHuman.getValue());
            } else if (sliderAI.getValue() + sliderHuman.getValue() == 0) {
                sliderAI.setValue(1);
            }
        });

        playButton.setOnAction(actionEvent -> {
            Game play = new Game();
            try {
                window.hide();
                play.setFullscreenStatus(fullscreenBoolean);
                play.setMenu(menuScene);
                play.start(window);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        });

        playStackpane.setOnKeyPressed(keyEvent -> {
            System.out.println(windowWidth);
            System.out.println("BOT: " + playerAIcount);
            System.out.println("HUMAN: "+playerHumanCount);
        });

        /// tausta lisamine
        menuScene.setFill(Paint.valueOf("red"));
        openingStackpane.setBackground(background.setByColor("green"));
        menu.setBackground(background.setByColor(Color.NAVY));
        settingsStackpane.setBackground(background.setByColor("lightgreen"));
        settingsMenu.setBackground(background.setByColor("lightgreen"));

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
