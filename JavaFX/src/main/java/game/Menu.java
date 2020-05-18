package game;

import game.help.Buttons;
import game.help.Resolution;
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
    protected Buttons buttons = new Buttons();
    private Scene menuScene;
    private final StackPane openingStackpane = new StackPane();

    public void start(Stage window) {
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(false);
        window.setMinWidth(1280);
        window.setMinHeight(720);
        menuScene = new Scene(openingStackpane, 1280, 720);
        window.setScene(menuScene);

        Resolution resolution = new Resolution(window);
        resolution.change(menuScene.getWidth(), menuScene.getHeight());

        SettingMenu settings = new SettingMenu(window, openingStackpane, resolution);
        StackPane settingsStackpane = settings.settings();
        StackPane playStackpane = settings.game();

        /// main menu
        VBox mainMenu = new VBox(50);
        Button startButton = buttons.play();
        Button settingsButton = buttons.settings();
        Button exitButton = buttons.exit();
        mainMenu.getChildren().addAll(startButton, settingsButton, exitButton);
        mainMenu.setMaxSize(resolution.width() / 3, resolution.height());
        mainMenu.setAlignment(Pos.CENTER);

        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(mainMenu);
        /// actions for buttons and key
        openingStackpane.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                window.close();
            }
        });

        window.getScene().setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                window.getScene().setRoot(openingStackpane);
            }
        });

        exitButton.setOnAction(actionEvent -> window.close());
        settingsButton.setOnAction(actionEvent -> menuScene.setRoot(settingsStackpane));
        startButton.setOnAction(actionEvent -> menuScene.setRoot(playStackpane));

        /// adding backgrounds
        for (Pane pane : Arrays.asList(openingStackpane, settingsStackpane, playStackpane)) {
            pane.setId("menuStackPane");
            pane.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
        }
        mainMenu.setId("MenuBar");
        mainMenu.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

        /// buttons binding
        for (Button btn : Arrays.asList(exitButton, settingsButton, startButton)) {
            btn.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        }
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
