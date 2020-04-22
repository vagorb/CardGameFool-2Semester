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
    private Resolution resolution = new Resolution();
    private Scene menuScene;

    public void start(Stage window) {
        /// avamenüü
        window.setMinWidth(1280);
        window.setMinHeight(720);
        StackPane openingStackpane = new StackPane();
        menuScene = new Scene(openingStackpane, 1280, 720);
        resolution.change(menuScene.getWidth(), menuScene.getHeight());

        SettingMenu settingMenu = new SettingMenu(window, openingStackpane, resolution);
        StackPane settingsStackpane = settingMenu.settings();
        StackPane playStackpane = settingMenu.game();

        Button exitButton = buttons.exit();

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
        VBox mainMenu = new VBox(50);
        Button startButton = buttons.play();
        Button settingsButton = buttons.settings();
        mainMenu.getChildren().addAll(startButton, settingsButton, exitButton);
        mainMenu.setMaxSize(resolution.width() / 3, resolution.height());
        mainMenu.setAlignment(Pos.CENTER);

        openingStackpane.setAlignment(Pos.BOTTOM_CENTER);
        openingStackpane.getChildren().addAll(mainMenu);

        /// nuppude ja klahvide tegevused
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

        /// tausta lisamine
        for (Pane pane : Arrays.asList(openingStackpane, settingsStackpane, playStackpane)) {
            pane.setId("menuStackPane");
            pane.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
        }
        mainMenu.setId("MenuBar");
        mainMenu.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());


        /// nuppude suuruste kopeerimine
        for (Button btn : Arrays.asList(exitButton, settingsButton, startButton)) {
            btn.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 13d));
        }
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
