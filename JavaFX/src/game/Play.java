package game;

import appearance.BackgroundSetter;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Play extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;
    private Button activeCard = null;

    private double windowHeight;
    private double windowWidth;


    void setFullscreenStatus(boolean fullscreenStatus) {
        this.fullscreenStatus = fullscreenStatus;
    }

    void setMenu(Scene menu) {
        this.menuScene = menu;
        windowWidth = menuScene.widthProperty().get();
        windowHeight = menuScene.heightProperty().get();
    }

    @Override
    public void start(Stage window) {
        Buttons buttons = new Buttons();
        StackPane playStackPane = new StackPane();
        Scene playScene = new Scene(playStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox();
        HBox cardBox = new HBox(2);

        ScrollPane scrollPane = new ScrollPane(cardBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardBoxBase.setAlignment(Pos.CENTER);

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9)");

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);
        window.setX(0);

        Button backButton = buttons.back();
        Button exitButton = buttons.exit();

        ///   windowWidth / 2.3 ~ 10 kaardi
        cardBoxBase.setMaxSize(windowWidth / 2.3, windowHeight / 9);
        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);

        double cardHeight = cardBoxBase.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        double playfieldCardUnit = cardWidth * 1.2;

        // /PLACEHOLDER/
        List<Button> buttonList = new ArrayList<>();
        String suite = "Spades";
        for (int i = 2; i <= 14; i++) {
            buttonList.add(new Button());
            Button button = buttonList.get(i - 2);
            button.setMinSize(cardWidth, cardHeight);
            button.setMaxSize(cardWidth, cardHeight);
            if (i == 2) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/2 of " + suite + ".png", button));
            } else if (i == 3) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/3 of " + suite + ".png", button));
            } else if (i == 4) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/4 of " + suite + ".png", button));
            } else if (i == 5) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/5 of " + suite + ".png", button));
            } else if (i == 6) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/6 of " + suite + ".png", button));
            } else if (i == 7) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/7 of " + suite + ".png", button));
            } else if (i == 8) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/8 of " + suite + ".png", button));
            } else if (i == 9) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/9 of " + suite + ".png", button));
            } else if (i == 10) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/10 of " + suite + ".png", button));
            } else if (i == 11) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/Jack of " + suite + ".png", button));
            } else if (i == 12) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/Queen of " + suite + ".png", button));
            } else if (i == 13) {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/King of " + suite + ".png", button));
            } else {
                button.setBackground(new BackgroundSetter().setImage("appearance/cards/" + suite + "/Ace of " + suite + ".png", button));
            }
            ///   valitud kaardi teeb läbipaistvamaks
            button.setOnAction(actionEvent -> {
                if (activeCard != null) {
                    activeCard.setStyle("-fx-opacity: 1");
                }
                activeCard = button;
                activeCard.setStyle("-fx-opacity: 0.5");
            });
            cardBox.getChildren().add(button);
        }

        /// mänguvälja elemendid
        PlayField playFieldClass = new PlayField(playfieldCardUnit);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, Pane> playFieldButtons = playFieldClass.createButtons();

        for (int i = 1; i <= 6; i++) {
            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
            attack.setBackground(new BackgroundSetter().setColor(Color.ORANGERED));

            attack.setTranslateX(playfieldCardUnit);
            attack.setTranslateY(playfieldCardUnit);

            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
            defence.setBackground(new BackgroundSetter().setColor(Color.LAWNGREEN));
            defence.setVisible(false);

            if (i < 4) {
                upperLayer.getChildren().addAll(playFieldButtons.get(i));
            } else {
                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
            }

            attack.setOnAction(actionEvent -> {
                if (activeCard != null && !attack.isDisable()) {
                    System.out.println(attack.getParent());
                    cardBox.getChildren().remove(activeCard);
                    attack.setBackground(activeCard.getBackground());
                    attack.setDisable(true);
                    defence.setVisible(true);
                    activeCard = null;
                }
            });
            defence.setOnMouseClicked(mouseEvent -> {
                if (activeCard != null && !defence.isDisable()) {
                    cardBox.getChildren().remove(activeCard);
                    defence.setBackground(activeCard.getBackground());
                    defence.setDisable(true);
                    activeCard = null;
                }
            });
        }

        /// tegevused nuppude ning hiire ja klaviatuuriga
        scrollPane.setOnScroll(scrollEvent -> scrollPane.setHvalue(scrollPane.getHvalue() - scrollEvent.getDeltaY() / 300));

        cardBoxBase.setOnMouseEntered(actionEvent -> {
            cardBoxBase.setTranslateY(3.7 * cardHeight);
            cardBoxBase.setScaleX(2);
            cardBoxBase.setScaleY(2);
        });

        cardBoxBase.setOnMouseExited(actionEvent -> {
            cardBoxBase.setTranslateY(4.2 * cardHeight);
            cardBoxBase.setScaleX(1);
            cardBoxBase.setScaleY(1);
        });

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

        playStackPane.setBackground(new BackgroundSetter().setColor(Color.WHEAT));
        cardBoxBase.setBackground(new BackgroundSetter().setColor(Color.DARKGOLDENROD));

        cardBoxBase.getChildren().add(scrollPane);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        playStackPane.getChildren().addAll(menu, playField, cardBoxBase);

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
