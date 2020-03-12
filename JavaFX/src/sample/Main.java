package sample;

import appearance.BackgroundSetter;
import appearance.Icons;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
    static boolean fullscreenBoolean = false;
    Buttons buttons = new Buttons();
    BackgroundSetter background = new BackgroundSetter();
    double windowHeight;
    double windowWidth;

    /**
     * Insets(top = y, right = width-?, bottom = height-?, left = x)
     */
    @Override
    public void start(Stage window) {
        /// avamenüü
        StackPane openingStackpane = new StackPane();
        Scene openingScene = new Scene(openingStackpane, 800, 600);

        windowWidth = openingScene.getWidth();
        windowHeight = openingScene.getHeight();

        /// sätete menüü
        StackPane settingsStackpane = new StackPane();
        Scene settingsScene = new Scene(settingsStackpane, windowWidth + 100, windowHeight + 100);
        /// mängu parameetrite paika panemise menüü
        StackPane playStackpane = new StackPane();
        Scene playScene = new Scene(playStackpane, windowWidth, windowHeight);

        /// nupud
        Button exitButton = buttons.exit();
        Button backButton = buttons.back();
        Button settingsButton = buttons.settings();
        Button playButton = buttons.play();
        Button fullscreenButton = buttons.fullscreen(windowWidth, windowHeight);


        // akna sätted
        window.setX(0.0);
        window.setY(0.0);
        window.setTitle("This is testing window...");
        window.setScene(openingScene);
        window.setResizable(false);
        window.show();
        window.hide();

        /// menüü riba
        VBox menu = new VBox(25);
        menu.getChildren().addAll(playButton, settingsButton, exitButton);
        menu.setMaxSize(windowWidth * 3 / 7, windowHeight * 9 / 10);
        menu.setAlignment(Pos.CENTER);

        VBox settingsMenu = new VBox(25);
        settingsMenu.getChildren().addAll(fullscreenButton, backButton);
        settingsMenu.setPadding(new Insets(100, 0, 0, 100));

        settingsStackpane.setAlignment(Pos.BOTTOM_LEFT);
        settingsStackpane.getChildren().addAll(settingsMenu);
        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(menu);

        /// nuppude ja klahvide tegevused
        exitButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });

        backButton.setOnAction(actionEvent -> {
            window.setScene(openingScene);
            window.setFullScreen(fullscreenBoolean);
        });

        openingScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.F) {
                fullscreenBoolean = !fullscreenBoolean;
            } else if (button.getCode() == KeyCode.ESCAPE) {
                window.close();
            }
            window.setFullScreen(fullscreenBoolean);
        });

        settingsScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.F) {
                fullscreenBoolean = !fullscreenBoolean;
            } else if (button.getCode() == KeyCode.ESCAPE) {
                window.setScene(openingScene);
            }
            window.setFullScreen(fullscreenBoolean);
        });

        settingsButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) settingsButton.getScene().getWindow();
            stage.setScene(settingsScene);
            window.setFullScreen(fullscreenBoolean);
        });

        fullscreenButton.setOnAction(actionEvent -> {
            fullscreenBoolean = !fullscreenBoolean;
            window.setFullScreen(fullscreenBoolean);
            if (window.isFullScreen()) {
                fullscreenButton.setGraphic(new Icons("black").fullscreenExit());
                windowWidth = window.getWidth();
                windowHeight = window.getHeight();
            } else {
                fullscreenButton.setGraphic(new Icons("black").fullscreenEnter());
                windowWidth = window.getScene().getWidth();
                windowHeight = window.getScene().getHeight();
            }
            fullscreenButton.setPrefWidth(windowWidth / 5);
            fullscreenButton.setPrefHeight(windowHeight / 20);
        });

        /// tausta lisamine
        openingScene.setFill(Paint.valueOf("red"));
        openingStackpane.setBackground(background.setColor("green"));
        menu.setBackground(background.setColor("yellow"));
        settingsStackpane.setBackground(background.setColor("lightgreen"));
        settingsMenu.setBackground(background.setColor("lightgreen"));

        System.out.println(window.getWidth());
        System.out.println(window.getHeight());
        System.out.println(windowWidth);
        System.out.println(windowHeight);

        settingsButton.prefWidthProperty().bind(exitButton.widthProperty());
        backButton.prefWidthProperty().bind(fullscreenButton.widthProperty());

        playButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 10.0));
        playButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 5.0));

        exitButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 10.0));
        exitButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 15.0));
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
