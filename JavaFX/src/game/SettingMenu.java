package game;


import game.help.Resolution;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class SettingMenu extends Menu {
    private Stage window;
    private StackPane mainStackpane;
    private Resolution resolution;
    private int playerAI = 0;
    private int playerHumanCount = 1;

    public SettingMenu(Stage window, StackPane mainStackpane, Resolution resolution) {
        this.window = window;
        this.mainStackpane = mainStackpane;
        this.resolution = resolution;
    }

    protected StackPane settings() {
        StackPane settingsStackpane = new StackPane();
        Button backButton = buttons.back();
        Button fullscreenButton = buttons.fullscreen();


        VBox settingsMenu = new VBox(25);
        settingsMenu.getChildren().addAll(fullscreenButton, backButton);
        settingsMenu.setPadding(new Insets(100, 0, 0, 100));


        settingsStackpane.setAlignment(Pos.BOTTOM_LEFT);
        settingsStackpane.getChildren().addAll(settingsMenu);

        fullscreenButton.setOnAction(actionEvent -> {
            window.setFullScreen(!window.isFullScreen());
            if (window.isFullScreen()) {
                fullscreenButton.setId("fullscreenExit");
            } else {
                fullscreenButton.setId("fullscreenEnter");
            }
            fullscreenButton.getStylesheets().add(getClass().getResource("/css/buttons.css").toExternalForm());
            resolution.change(window.getScene().getWidth(), window.getScene().getHeight());
            ((VBox) mainStackpane.getChildren().get(0)).setMaxSize(resolution.width() / 3, resolution.height());
        });
        backButton.setOnAction(actionEvent -> window.getScene().setRoot(mainStackpane));

        fullscreenButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        return settingsStackpane;
    }

    protected StackPane game() {
        StackPane gameStackPane = new StackPane();
        Button playButton = buttons.play();

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
        playerChoosingMenu.getChildren().addAll(checkBoxAI, humanCountLabel, sliderHuman, playButton);
        playerChoosingMenu.setMaxSize(resolution.width() / 4, resolution.height() / 2);
        playerChoosingMenu.setAlignment(Pos.CENTER);

        playButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));

        checkBoxAI.setOnAction(actionEvent -> {
            if (checkBoxAI.isSelected()) {
                playerAI = 1;
                sliderHuman.setMax(1);
            } else {
                playerAI = 0;
                sliderHuman.setMax(3);
            }
        });

        sliderHuman.setOnMouseExited(mouseEvent -> {
            if (!checkBoxAI.isSelected() && sliderHuman.getValue() == 0) {
                sliderHuman.setValue(1);
            }
            playerHumanCount = (int) sliderHuman.getValue();
        });
        gameStackPane.getChildren().addAll(playerChoosingMenu);

        playButton.setOnAction(actionEvent -> {
            Game play = new Game();
            try {
                window.hide();
                window.getScene().setRoot(mainStackpane);
                play.setFullscreenStatus(window.isFullScreen());
                play.setMenu(window.getScene());
                play.setPlayerCount(playerHumanCount, playerAI);
                resolution.change(window.getScene().getWidth(), window.getScene().getHeight());
                play.start(window);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        });

        return gameStackPane;
    }
}
