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
import javafx.stage.*;

import java.util.Arrays;

public class Menu extends Application {
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
        window.setScene(menuScene);
        window.setResizable(false);
        window.show();
        window.hide();

        /// peamenüü
        VBox menu = new VBox(50);
        Button startButton = buttons.play();
        Button settingsButton = buttons.settings();
        menu.getChildren().addAll(startButton, settingsButton, exitButton);
        menu.setMaxSize(windowWidth / 3, windowHeight);
        menu.setAlignment(Pos.CENTER);

        /// vahemenüü
        CheckBox checkBoxAI = new CheckBox();
        checkBoxAI.setText("1 AI player");
        checkBoxAI.setId("AIcheck");
        checkBoxAI.getStylesheets().add(getClass().getResource("/css/misc.css").toString());
        Label humanCountLabel = new Label("Human Opponents Count:");
        humanCountLabel.setId("HumanCount");
        humanCountLabel.getStylesheets().add(getClass().getResource("/css/misc.css").toString());
        Slider sliderHuman = new Slider(0, 3, 1);
        sliderHuman.setBlockIncrement(1);
        sliderHuman.setMinorTickCount(0);
        sliderHuman.setMajorTickUnit(1);
        sliderHuman.setShowTickLabels(true);
        sliderHuman.setSnapToTicks(true);
        sliderHuman.getStylesheets().add(getClass().getResource("/css/slider.css").toString());
        VBox playerChoosingMenu = new VBox(25);
        playerChoosingMenu.getChildren().addAll(backButton, checkBoxAI, humanCountLabel, sliderHuman, playButton);
        playerChoosingMenu.setMaxSize(windowWidth / 4, windowHeight / 2);
        playerChoosingMenu.setAlignment(Pos.CENTER);


        /// sätete menu
        VBox settingsMenu = new VBox(25);
        settingsMenu.getChildren().addAll(fullscreenButton, backButton);
        settingsMenu.setPadding(new Insets(100, 0, 0, 100));

        settingsStackpane.setAlignment(Pos.BOTTOM_LEFT);
        settingsStackpane.getChildren().addAll(settingsMenu);
        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(menu);
        playStackpane.getChildren().addAll(playerChoosingMenu);

        /// nuppude ja klahvide tegevused

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
            menu.setMaxSize(windowWidth / 3, windowHeight);
            fullscreenButton.setPrefHeight(windowHeight / 20);
        });

        openingStackpane.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                window.close();
            }
        });

        playStackpane.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                window.getScene().setRoot(openingStackpane);
            }
        });

        exitButton.setOnAction(actionEvent -> window.close());
        backButton.setOnAction(actionEvent -> menuScene.setRoot(openingStackpane));
        settingsButton.setOnAction(actionEvent -> menuScene.setRoot(settingsStackpane));
        startButton.setOnAction(actionEvent -> menuScene.setRoot(playStackpane));

        checkBoxAI.setOnAction(actionEvent -> {
            if (checkBoxAI.isSelected()) {
                playerAIcount = 1;
                sliderHuman.setMax(1);
            } else {
                playerAIcount = 0;
                sliderHuman.setMax(3);
            }
        });

        sliderHuman.setOnMouseExited(mouseEvent -> {
            if (!checkBoxAI.isSelected() && sliderHuman.getValue() == 0) {
                sliderHuman.setValue(1);
            }
            playerHumanCount = (int) sliderHuman.getValue();
        });

        playButton.setOnAction(actionEvent -> {
            Game play = new Game();
            try {
                window.hide();
                play.setFullscreenStatus(fullscreenBoolean);
                play.setMenu(menuScene, openingStackpane);
                play.setPlayerCount(playerHumanCount, playerAIcount);
                play.start(window);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        });

        /// tausta lisamine
        for (Pane pane : Arrays.asList(openingStackpane, settingsStackpane, playStackpane)) {
            pane.setBackground(new BackgroundSetter().setImage(
                    getClass().getResource("/images/backgrounds/menu_bg.jpg"), pane));
        }
        menu.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());


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
