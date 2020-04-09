package game;

import appearance.BackgroundSetter;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.PlayField;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;

    private double windowHeight;
    private double windowWidth;

    private Button activeCard = null;
    private int AIcount;
    private int humanCount;
    private StackPane openingMenu;

    void setFullscreenStatus(boolean fullscreenStatus) {
        this.fullscreenStatus = fullscreenStatus;
    }

    void setMenu(Scene menu, StackPane openingMenu) {
        this.menuScene = menu;
        this.openingMenu = openingMenu;
        windowWidth = menuScene.getWidth();
        windowHeight = menuScene.getHeight();
    }

    void setPlayerCount(int human, int AI) {
        this.humanCount = human;
        this.AIcount = AI;
    }

    public void start(Stage window) {
        Buttons buttons = new Buttons();
//        GridPane playPane = new GridPane();
        StackPane playStackPane = new StackPane();
        Scene playScene = new Scene(playStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox();
        HBox cardBox = new HBox(2);

        ScrollPane cardBoxScroll = new ScrollPane(cardBox);
        cardBoxScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardBoxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardBoxBase.setAlignment(Pos.CENTER);

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9)");

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);

        Button backButton = buttons.back();
        Button exitButton = buttons.exit();

        cardBoxBase.setMaxSize(windowWidth / 3, windowHeight / 12);
        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);

        double cardHeight = cardBoxBase.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        double playfieldCardUnit = cardWidth * 0.8;

        // avatarid
        AvatarBox avatars = new AvatarBox(5, windowWidth);
        avatars.makeAvatar("playername1");
        avatars.makeAvatar("playername2");
        avatars.makeAvatar("playername3");
        avatars.makeAvatar("playername4");
        avatars.makeAvatar("playername5");
        HBox avaterPage = avatars.showAvatars();

        // kaardi generaator
        List<Button> buttonList = new ArrayList<>();
        for (String suite : Arrays.asList("Hearts", "Spades", "Diamonds", "Clubs")) {
            for (int i = 2; i <= 14; i++) {
                buttonList.add(new Button());
                Button button = buttonList.get(buttonList.size() - 1);
                button.setMinSize(cardWidth, cardHeight);
                button.setMaxSize(cardWidth, cardHeight);
                String value = String.valueOf(i);
                if (i == 11) {
                    value = "Jack";
                } else if (i == 12) {
                    value = "Queen";
                } else if (i == 13) {
                    value = "King";
                } else if (i == 14) {
                    value = "Ace";
                }
                button.setId(value + "_of_" + suite);
                button.getStylesheets().add(getClass().getResource("/css/card.css").toExternalForm());
                cardBox.getChildren().add(button);

                ///   valitud kaardi teeb läbipaistvamaks
                button.setOnAction(actionEvent -> {
                    if (activeCard != null) {
                        activeCard.setStyle("-fx-opacity: 1");
                    }
                    activeCard = button;
                    activeCard.setStyle("-fx-opacity: 0.5");
                });
            }
        }


        /// mänguvälja elemendid
        PlayField playFieldClass = new PlayField(playfieldCardUnit);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, Pane> playFieldButtons = playFieldClass.createButtons();

        for (int i = 1; i <= 6; i++) {
            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
            attack.setBackground(new BackgroundSetter().setByColor(Color.ORANGERED));

            attack.setTranslateX(playfieldCardUnit);
            attack.setTranslateY(playfieldCardUnit);

            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
            defence.setBackground(new BackgroundSetter().setByColor(Color.LAWNGREEN));
            defence.setVisible(false);

            if (i < 4) {
                upperLayer.getChildren().addAll(playFieldButtons.get(i));
            } else {
                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
            }

            attack.setOnMouseClicked(mouseEvent -> {
                if (activeCard != null && !attack.isDisable()) {
                    cardBox.getChildren().remove(activeCard);
                    attack.setBackground(new BackgroundSetter().setImage(
                            activeCard.getBackground().getImages().get(0).getImage().getUrl(), attack));
                    attack.setDisable(true);
                    defence.setVisible(true);
                    activeCard = null;
                }
            });
            defence.setOnMouseClicked(mouseEvent -> {
                if (activeCard != null && !defence.isDisable()) {
                    cardBox.getChildren().remove(activeCard);
                    defence.setBackground(new BackgroundSetter().setImage(
                            activeCard.getBackground().getImages().get(0).getImage().getUrl(), defence));
                    defence.setDisable(true);
                    activeCard = null;
                }
            });
        }

        /// tegevused nuppude ning hiire ja klaviatuuriga
        cardBoxScroll.setOnScroll(scrollEvent -> cardBoxScroll.setHvalue(cardBoxScroll.getHvalue() - scrollEvent.getDeltaY() / 500));

        cardBoxBase.setOnMouseEntered(actionEvent -> {
            cardBoxBase.setTranslateY(5.1 * cardHeight);
            cardBoxBase.setScaleX(2.5);
            cardBoxBase.setScaleY(2.5);
        });

        cardBoxBase.setOnMouseExited(actionEvent -> {
            cardBoxBase.setTranslateY(5.8 * cardHeight);
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
            menuScene.setRoot(openingMenu);
            window.setScene(menuScene);
            window.setFullScreen(fullscreenStatus);
            window.show();
        });

        exitButton.setOnAction(actionEvent -> window.close());

        playStackPane.setBackground(new BackgroundSetter().setByColor(Color.WHEAT));
        cardBoxBase.setBackground(new BackgroundSetter().setByColor(Color.DARKGOLDENROD));

        cardBoxBase.getChildren().add(cardBoxScroll);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        playStackPane.getChildren().addAll(menu, avaterPage, playField, cardBoxBase);

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
