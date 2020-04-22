package game;

import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.PlayField;
import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
        Buttons buttons = new Buttons();
        StackPane gameStackPane = new StackPane();
        StackPane playeradding = new StackPane();
        Scene playScene = new Scene(gameStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox(20);
        HBox cardBox = new HBox(2);


//        //// Mängija nime valik / vaja mingit sorti pausi nime kinnitamise (Submiti vajutamine) ajaks
//        for (int i = 0; i < humanCount; i++) {
//            System.out.println(i);
//            Popup popup = new Popup(playeradding);
//            if (popup.playerName() == null || popup.playerName().equals(""))
//                playeradding.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
//                    Player player = new Player(popup.playerName(), 0, new Hand());
//                    players.add(player);
//                    System.out.println("PLAYER " + player.getName());
//                });
//        }
//        System.out.println(players);
        //        playScene.setRoot(gameStackPane);

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
        Button skipButton = buttons.skipTurn();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 4, windowHeight / 12);

        for (Button btn : Arrays.asList(pickCardsUpButton, skipButton)) {
            btn.setMinSize(cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
            btn.setMaxSize(cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
            btn.setTranslateX(cardBoxBase.getMaxWidth() / 2 - btn.getMaxWidth() * 1.5);
        }
        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
        cardBoxScroll.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
//        pickCardsUpButton.setTranslateX(cardBoxBase.getMaxWidth() / 2 - pickCardsUpButton.getMaxWidth());
//        skipButton.setTranslateX(cardBoxBase.getMaxWidth() / 2 - pickCardsUpButton.getMaxWidth() * 3);

        double cardHeight = cardBoxScroll.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        double playfieldCardUnit = cardWidth * 0.8;

        // avatarid (nime max pikkus 22-36 tähemärki)
        AvatarBox avatars = new AvatarBox(humanCount + AIcount, windowWidth);
        if (computer != null) {
            avatars.makeAvatar(new Ai(new Hand()));
        }
        players.add(new Player("player1", 0, new Hand()));
        players.add(new Player("player1", 0, new Hand()));
        avatars.makeAvatar(players.get(0));
        avatars.makeAvatar(players.get(1));
        HBox avatarPage = avatars.showAvatars();

        // kaardi generaator
        Deck deck = new Deck();
        deck.shuffleDeck();
        List<Button> buttonList = new ArrayList<>();
        List<Card> cardsInHand = new LinkedList<>();
        List<Card> cardsOnTable = new LinkedList<>();
        for (Card card : deck.getDeck()) {
//            if (buttonList.size() < 8) {
            cardsInHand.add(card);
            buttonList.add(new Button());
            Button button = buttonList.get(buttonList.size() - 1);
            button.setMinSize(cardWidth, cardHeight);
            button.setMaxSize(cardWidth, cardHeight);
            button.setId(card.getId());
            button.setStyle(String.format("-fx-background-size: cover;-fx-background-image: "
                    + "url('/images/cards/%s/%s.png')", card.getSuit(), card.getId()));
            cardBox.getChildren().add(button);
            button.setOnAction(actionEvent -> {
                if (activeCard != null) {
                    activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                }
                activeCard = button;
                activeCard.setId(button.getId());
                activeCard.setStyle(button.getStyle() + ";-fx-opacity: 0.6; -fx-border-color: rgb(255,200,0)");
            });
//            }
        }
        ///// ei saanud button.getStyle() abil background image kätte
//                button.setId(value + "_of_" + suite);
//                button.getStylesheets().add(getClass().getResource("/css/card.css").toExternalForm());

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
                .setHvalue(cardBoxScroll.getHvalue() + scrollEvent.getDeltaY() / 1000));

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
            if (index[0] >= 0) {
                pileBox.getChildren().get(index[0]--).setVisible(false);
            }
        });

        playScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                menu.setVisible(!menu.isVisible());
                gameStackPane.getChildren().forEach(child -> {
                    if (child != menu) {
                        child.setDisable(!child.isDisable());
                    }
                });
            }
        });

        backButton.setOnAction(actionEvent -> {
            window.hide();
            window.setScene(menuScene);
            window.show();
        });

        exitButton.setOnAction(actionEvent -> window.close());

        cardBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            cardsInHand.forEach(card -> {
                if (card.getId().equals(change.getRemoved().get(0).getId())) {
                    cardsOnTable.add(card);
                    System.out.println("LAUALE PANDI === " + card);
                }
            });
            cardsInHand.removeIf(card -> card.getId().equals(change.getRemoved().get(0).getId()));
            System.out.println("KÄES === " + cardsInHand);
            System.out.println("LAUAL === " + cardsOnTable);
            System.out.println();
        });

        cardBoxBase.setId("cardBoxBase");
        gameStackPane.setId("gameStackPane");
        gameStackPane.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
        cardBoxBase.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

        cardBoxBase.getChildren().addAll(skipButton, pickCardsUpButton);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        gameStackPane.getChildren().addAll(menu, avatarPage, playField, pileBox, cardBoxBase, cardBoxScroll);

        backButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 50));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 50));
        exitButton.prefWidthProperty().bind(backButton.prefHeightProperty());
        exitButton.prefHeightProperty().bind(backButton.prefHeightProperty());
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
