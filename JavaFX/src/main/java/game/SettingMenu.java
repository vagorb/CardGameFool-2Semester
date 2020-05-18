package game;


import game.help.Buttons;
import game.help.Resolution;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.regex.Pattern;

public class SettingMenu {
    private final Stage window;
    private final StackPane mainStackpane;
    private final Resolution resolution;
    private final Buttons buttons = new Buttons();
    private int playerAI = 0;
    private int playerHumanCount = 1;
    private boolean invertScroll = false;

    public SettingMenu(Stage window, StackPane mainStackpane, Resolution resolution) {
        this.window = window;
        this.mainStackpane = mainStackpane;
        this.resolution = resolution;
    }

    protected StackPane settings() {
        StackPane settingsStackpane = new StackPane();
        VBox settingsMenu = new VBox(25);
        Button backButton = buttons.back();
        Button fullscreenButton = buttons.fullscreen();

        ComboBox<String> resolutionChoices = resolution.getResolutionChoices();

        CheckBox scrollInversion = new CheckBox();
        scrollInversion.setText("Invert scroll");

        CheckBox customResolution = resolution.getCustomResolution();
        customResolution.setText("Custom Resolution");

        CheckBox alwaysOnTop = new CheckBox();
        alwaysOnTop.setText("Always on top");

        for (CheckBox checkBox : List.of(scrollInversion, alwaysOnTop, customResolution)) {
            checkBox.getStylesheets().add(getClass().getResource("/css/misc.css").toString());
        }

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
        alwaysOnTop.setOnAction(actionEvent -> window.setAlwaysOnTop(alwaysOnTop.isSelected()));
        scrollInversion.setOnAction(actionEvent -> invertScroll = scrollInversion.isSelected());
        backButton.setOnAction(actionEvent -> window.getScene().setRoot(mainStackpane));

        fullscreenButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        settingsMenu.setAlignment(Pos.CENTER);

        settingsMenu.getChildren().addAll(fullscreenButton, resolutionChoices, customResolution, alwaysOnTop,
                scrollInversion, backButton);
        settingsStackpane.getChildren().addAll(settingsMenu);

        return settingsStackpane;
    }

    protected StackPane game() {
        StackPane gameStackPane = new StackPane();
        Button playButton = buttons.play();
        playButton.setText("START");
        playButton.setDisable(true);

        Label enterYourName = new Label("Enter your name:");
        enterYourName.setId("EnterYourName");
        enterYourName.getStylesheets().add(getClass().getResource("/css/misc.css").toString());
        TextField nameField = new TextField();
        VBox nameEnteringVbox = new VBox(5, enterYourName, nameField);
        nameField.setAlignment(Pos.CENTER);
        nameEnteringVbox.setAlignment(Pos.CENTER);
        nameField.textProperty().addListener((observableValue) ->
                playButton.setDisable(!Pattern.matches("\\D\\w{3,21}", nameField.getText())));

        Label humanCountLabel = new Label("Human Opponents Count:");
        humanCountLabel.setId("HumanCount");
        humanCountLabel.getStylesheets().add(getClass().getResource("/css/misc.css").toString());

        /// checkbox for AI (if included in game or not)
        CheckBox checkBoxAI = new CheckBox();
        checkBoxAI.setText("1 AI player");
        checkBoxAI.setId("AIcheck");
        checkBoxAI.getStylesheets().add(getClass().getResource("/css/misc.css").toString());
        /// slider for number of players
        Slider sliderHuman = new Slider(0, 1, 1);
        sliderHuman.setBlockIncrement(1);
        sliderHuman.setMinorTickCount(0);
        sliderHuman.setMajorTickUnit(1);
        sliderHuman.setShowTickLabels(true);
        sliderHuman.setSnapToTicks(true);
        sliderHuman.getStylesheets().add(getClass().getResource("/css/slider.css").toString());

        sliderHuman.setOnMouseReleased(mouseEvent -> {
            if (!checkBoxAI.isSelected() && sliderHuman.getValue() == 0) {
                sliderHuman.setValue(1);
            }
        });

        checkBoxAI.selectedProperty().addListener((observable) -> {
            sliderHuman.setDisable(checkBoxAI.isSelected());
            if (!checkBoxAI.isSelected()) {
                sliderHuman.setValue(1);
                playerAI = 0;
            } else {
                sliderHuman.setValue(0);
                playerAI = 1;
            }
        });

        sliderHuman.valueProperty().addListener((observable -> playerHumanCount = (int) sliderHuman.getValue()));

        VBox playerChoosingMenu = new VBox(25);
        playerChoosingMenu.getChildren().addAll(nameEnteringVbox, checkBoxAI, humanCountLabel, sliderHuman, playButton);
        playerChoosingMenu.setMaxSize(resolution.width() / 4, resolution.height() / 2);
        playerChoosingMenu.setAlignment(Pos.CENTER);
        gameStackPane.getChildren().addAll(playerChoosingMenu);

        playButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        playButton.setOnAction(actionEvent -> {
            Game play = new Game(playerHumanCount, playerAI);
            try {
                window.hide();
                window.getScene().setRoot(mainStackpane);
                //play.setPlayerName(nameField.getText());
                play.setMenu(window.getScene());
                play.setSettings(invertScroll, window.isFullScreen(), resolution);
                resolution.change(window.getScene().getWidth(), window.getScene().getHeight());
                play.start(window);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return gameStackPane;
    }
}
