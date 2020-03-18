package sample;

import appearance.BackgroundSetter;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.stage.*;

import java.io.File;

public class Play extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;

    //    private double windowHeight;
//    private double windowWidth;
    double windowWidth = 1280d;
    double windowHeight = 720d;


    void setFullscreenStatus(boolean fullscreenStatus) {
        this.fullscreenStatus = fullscreenStatus;
    }

    void setWindowResolution(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    void setMenu(Scene menu) {
        this.menuScene = menu;
    }

    @Override
    public void start(Stage window) {
        Buttons buttons = new Buttons();
        StackPane playStackPane = new StackPane();
        Scene playScene = new Scene(playStackPane, windowWidth, windowHeight);
        HBox cardBox = new HBox(5);
        Button cards = new Button();
        Button cards2 = new Button();

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);
        window.setX(0);
        window.show();
        window.hide();

        Button backButton = buttons.back();
        Button exitButton = buttons.exit();

        cardBox.setMinWidth(windowWidth);
        cardBox.setMaxHeight(windowHeight / 9);
        cardBox.setTranslateY((windowHeight - cardBox.getMaxHeight()) / 2);
        cardBox.setAlignment(Pos.CENTER);

        double cardHeight = cardBox.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        cards.setMinSize(cardWidth, cardHeight);
        cards.setMaxSize(cardWidth, cardHeight);
        cards2.setMinSize(cardWidth, cardHeight);
        cards2.setMaxSize(cardWidth, cardHeight);

        cardBox.setOnMouseClicked(actionEvent -> {
        });

        playStackPane.setBackground(new BackgroundSetter().setColor("Grey"));
        cardBox.setBackground(new BackgroundSetter().setColor("Yellow"));

        cards.setBackground(new BackgroundSetter().setImage("appearance/cards/Diamonds/Ace of Diamonds.png", cards));
        cards2.setBackground(new BackgroundSetter().setImage("appearance/cards/Spades/Ace of Spades.png", cards2));

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

        playStackPane.getChildren().addAll(menu, cardBox);
        menu.getChildren().addAll(backButton, exitButton);
        cardBox.getChildren().addAll(cards, cards2);

        playScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                menu.setVisible(!menu.isVisible());
                playStackPane.getChildren().forEach(child -> {
                    if (child != menu) {
                        child.setDisable(!child.isDisable());
                    }
                });
            }
        });

        backButton.setOnAction(actionEvent -> {
            window.hide();
            window.setScene(menuScene);
            window.setFullScreen(fullscreenStatus);
            window.show();
        });

        exitButton.setOnAction(actionEvent -> window.close());

        window.show();
    }

}
