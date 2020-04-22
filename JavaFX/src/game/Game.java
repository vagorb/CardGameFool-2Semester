package game;

import appearance.BackgroundSetter;
import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.PlayField;
import game.help.Popup;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;

    private GameController gameController = new GameController();


    private double windowHeight;
    private double windowWidth;

    private Button activeCard = null;
    private int AIcount;
    private int humanCount;
    private List<Player> players = new ArrayList<>();
    public Ai computer;

    void setFullscreenStatus(boolean fullscreenStatus) {
        this.fullscreenStatus = fullscreenStatus;
    }

    void setMenu(Scene menu) {
        this.menuScene = menu;
        windowWidth = menu.getWidth();
        windowHeight = menu.getHeight();
    }

    void setPlayerCount(int human, int AI) {
        this.humanCount = human;
        this.AIcount = AI;
        if (AIcount > 0) {
            computer = new Ai(new Hand());
        }
    }

    public void start(Stage window) {
        gameController.createBasic();
        gameController.assignTrumpAndFillHands();
        gameController.setPlayerStates();
        Buttons buttons = new Buttons();
        StackPane playStackPane = new StackPane();
        StackPane playeradding = new StackPane();
        Scene playScene = new Scene(playStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox();
        HBox cardBox = new HBox(2);

        ScrollPane cardBoxScroll = new ScrollPane(cardBox);
        cardBoxScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardBoxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardBoxBase.setAlignment(Pos.CENTER);

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);

        Button backButton = buttons.back();
        Button exitButton = buttons.exit();
        Button pickCardsUpButton = buttons.pickCardsUp();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 3, windowHeight / 12);
        pickCardsUpButton.setMaxSize(cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());

        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
        cardBoxScroll.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
        pickCardsUpButton.setTranslateX(cardBoxBase.getMaxWidth() / 2 - pickCardsUpButton.getMaxWidth());

        double cardHeight = cardBoxScroll.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        double playfieldCardUnit = cardWidth * 0.8;

        // avatarid (nime max pikkus 22-36 tähemärki)
        AvatarBox avatars = new AvatarBox(humanCount + AIcount, windowWidth);
        if (computer != null) {
            avatars.makeAvatar(new Ai(new Hand()));
        }
        players.add(new Player("player1", 0, new Hand()));
        avatars.makeAvatar(players.get(players.size() - 1));
        HBox avatarPage = avatars.showAvatars();

        // kaardi generaator

        List<Button> buttonList = new ArrayList<>();
        getCardsFromDeckAndCreatePNG(cardWidth, cardHeight, cardBox, buttonList);


        /// mänguvälja elemendid
        PlayField playFieldClass = new PlayField(playfieldCardUnit);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, Pane> playFieldButtons = playFieldClass.createButtons();

        for (int i = 1; i <= 6; i++) {
            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
            attack.setId("Attack");
            attack.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

            attack.setTranslateX(playfieldCardUnit);
            attack.setTranslateY(playfieldCardUnit);

            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
            defence.setId("Defence");
            defence.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
            defence.setVisible(false);

            if (i < 4) {
                upperLayer.getChildren().addAll(playFieldButtons.get(i));
            } else {
                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
            }

            for (Button placableCard : Arrays.asList(attack, defence)) {
                placableCard.setOnMouseClicked(mouseEvent -> {
                    if (activeCard != null && !placableCard.isDisable()) {
                        gameController.attackDefenseTurn();
                        gameController.setPlayerStates();
                        cardBox.getChildren().remove(activeCard);
                        placableCard.setDisable(true);
                        placableCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                        activeCard = null;
                        if (placableCard == attack) {
                            defence.setVisible(true);
                        }
                    }
                });
            }
        }

        /// tegevused nuppude ning hiire ja klaviatuuriga
        cardBoxScroll.setOnScroll(scrollEvent -> cardBoxScroll
                .setHvalue(cardBoxScroll.getHvalue() - scrollEvent.getDeltaY() / 1000));

        cardBoxScroll.setOnMouseEntered(actionEvent -> {
            cardBoxScroll.setTranslateY(5.1 * cardHeight);
            cardBoxScroll.setScaleX(2.5);
            cardBoxScroll.setScaleY(2.5);
        });

        cardBoxScroll.setOnMouseExited(actionEvent -> {
            cardBoxScroll.setTranslateY(5.8 * cardHeight);
            cardBoxScroll.setScaleX(1);
            cardBoxScroll.setScaleY(1);
        });

        /// väike visuaalse pile eksperiment
        VBox pileBox = new VBox();
        for (int i = 0; i < 36; i++) {
            Label newB = new Label();
            newB.setMinSize(playfieldCardUnit * 2, playfieldCardUnit * 3);
            newB.setStyle("-fx-background-image: url('/images/cards/back_of_card.png'); -fx-background-size: cover");
            pileBox.getChildren().add(newB);
            newB.setTranslateX(-i);
            newB.setTranslateY(-i * 87);
        }
        final int[] index = {pileBox.getChildren().size() - 1};
        pileBox.setTranslateX(windowWidth / 1.4);
        pileBox.setTranslateY(windowHeight * 2.1);
        pickCardsUpButton.setOnAction(actionEvent -> pileBox.getChildren().get(index[0]++).setVisible(false));

        Player current = players.get(players.size() - 1);
        pickCardsUpButton.setOnAction(actionEvent -> {
                    playFieldButtons.forEach((integer, pane) -> {
                        if (current.getPlayerState() == null) { /// .equals(Player.PlayerState.DEFENSE)) {
                            for (int i = 0; i < 2; i++) {
                                Button button = (Button) pane.getChildrenUnmodifiable().get(i);
                                button.setDisable(false);
                                button.getStylesheets().clear();
                                button.setStyle("-fx-background-image: null");
                                button.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
                                if (button.getId().equals("Defence")) {
                                    button.setVisible(false);
                                }
                            }
                        }
                    });
                    for (int x = 0; x < 3; x++) {
                        pileBox.getChildren().get(index[0]--).setVisible(false);
                    }
                }
        );

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

        cardBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            cardsInHand.removeIf(card -> card.getId().equals(change.getRemoved().get(0).getId()));
            System.out.println(cardsInHand);
            });

        playStackPane.setBackground(new BackgroundSetter()
                .setImage(getClass().getResource("/images/backgrounds/game_bg.jpg"), playStackPane));
        cardBoxBase.setBackground(new BackgroundSetter().setByColor(Color.DARKGOLDENROD));
        cardBoxBase.getChildren().add(pickCardsUpButton);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        playStackPane.getChildren().addAll(menu, avatarPage, playField, pileBox, cardBoxBase, cardBoxScroll);
        window.show();
    }

    public void getCardsFromDeckAndCreatePNG(double cardWidth, double cardHeight, HBox cardBox, List<Button> buttonList) {
        int i = 0;
        while(buttonList.size() < 6) {
//        for (int i = 0; i < 6; i++) {
            Player player1 = gameController.getTable().getPlayers().get(0);
            Card card = player1.getHand().getCardsInHand().get(i);
            buttonList.add(new Button());
            Button buttonAdding = buttonList.get(buttonList.size() - 1);
            buttonAdding.setMinSize(cardWidth, cardHeight);
            buttonAdding.setMaxSize(cardWidth, cardHeight);
            String value = String.valueOf(card.getValue());
            if (value.equals("11")) {
                value = "Jack";
            } else if (value.equals("12")) {
                value = "Queen";
            } else if (value.equals("13")) {
                value = "King";
            } else if (value.equals("14")) {
                value = "Ace";
            }
            buttonAdding.setId(value + "_of_" + card.getSuit());
            buttonAdding.getStylesheets().add(getClass().getResource("/css/card.css").toExternalForm());
            buttonAdding.setStyle(String.format("-fx-background-size: cover;-fx-background-image: "
                    + "url('/images/cards/%s/%s_of_%s.png')", card.getSuit(), value, card.getSuit()));
            cardBox.getChildren().add(buttonAdding);

            buttonAdding.setOnAction(actionEvent -> {
                if (activeCard != null) {
                    activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                }
                activeCard = buttonAdding;
                activeCard.setStyle(buttonAdding.getStyle() + ";-fx-opacity: 0.6; -fx-border-color: rgb(255,200,0)");
            });
            i += 1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
