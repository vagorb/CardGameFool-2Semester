package game;

import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.PlayField;
import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    private boolean invertScroll;

    private double windowHeight;
    private double windowWidth;

    private Deck deck = new Deck();
    //    private Button forAttackComparison;
    private Card attackCard;
    //    private Button forDefenseComparison;
    private Card defenseCard;
    private String trumpInfo;

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

    void setSettings(boolean invertScroll) {
        this.invertScroll = invertScroll;
    }

    void setPlayerCount(int human, int AI) {
        this.humanCount = human;
        this.AIcount = AI;
        if (AIcount > 0) {
            computer = new Ai(new Hand());
        }
    }

    private Button cardToButton(Card card, HBox cardBox, double cardWidth, double cardHeight) {
        Button button = new Button();
        Buttons.oneSizeOnly(button, cardWidth, cardHeight);
        button.setId(card.getId());
        button.setStyle(String.format("-fx-background-size: cover;-fx-background-image: "
                + "url('/images/cards/%s/%s.png')", card.getSuit(), card.getId()));
//      button.getStylesheets().add(getClass().getResource("/css/card.css").toExternalForm());
        cardBox.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            if (activeCard != null) {
                activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
            }
            activeCard = button;
            activeCard.setId(button.getId());
            activeCard.setStyle(button.getStyle() + ";-fx-opacity: 0.6; -fx-border-color: rgb(255,200,0)");
        });
        return button;
    }

    public void start(Stage window) {
        deck.shuffleDeck();
        trumpInfo = deck.getDeck().get(0).getSuit();
        // I NEED TO ACCOUNT FOR THE FACT THAT THIS CARD WILL BE REMOVED FROM THE DECK AS A SEPARATE CARD
        deck.removeCard(deck.getDeck().get(0));
        System.out.println(trumpInfo);

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
        Button clearTableButton = buttons.clearTable();
        Button pickCardsUp = buttons.pickCardsUp();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 4, windowHeight / 12);

        for (Button btn : Arrays.asList(clearTableButton, pickCardsUp)) {
            Buttons.oneSizeOnly(btn, cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
            btn.setTranslateX(cardBoxBase.getMaxWidth() / 2.5);
        }
        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
        cardBoxScroll.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);

        double cardHeight = cardBoxScroll.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 900 * 590;
        double playfieldCardUnit = cardWidth * 0.8;

        // avatars (name's max length 22-36 symbols)
        AvatarBox avatars = new AvatarBox(humanCount + AIcount, windowWidth);
        if (computer != null) {
            avatars.makeAvatar(new Ai(new Hand()));
        }
        for (int p = 0; p < humanCount; p++) {
            players.add(new Player("player", 0, new Hand()));
            avatars.makeAvatar(players.get(players.size() - 1));
        }
        HBox avatarPage = avatars.showAvatars();

        // card generator
        deck.shuffleDeck();
        List<Button> buttonList = new ArrayList<>();
        List<Card> cardsInHand = new LinkedList<>();
        List<Card> cardsOnTable = new LinkedList<>();
        for (Card card : deck.getDeck()) {
            if (buttonList.size() < 8) {
                cardsInHand.add(card);
                buttonList.add(cardToButton(card, cardBox, cardWidth, cardHeight));
            }
        }

        /// playfield elements
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
            attack.setOnMousePressed(mouseEvent -> {
                System.out.println();
                System.out.println("ACTIVE " + activeCard + ">>>" + activeCard.getId());
                if (activeCard != null && !attack.isDisable()) {
                    System.out.println("???");
                    activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");

                    cardsInHand.forEach(card -> {
                        if (activeCard.getId().equals(card.getId())) {
                            attackCard = card;
                        }
                    });
                    System.out.println("VERIFIED ATTACKER");
                    if (cardsOnTable.size() == 0) {
                        System.out.println("0 cards on table");
                        cardBox.getChildren().remove(activeCard);
                        attack.setDisable(true);
                        attack.setStyle(activeCard.getStyle());
                        defence.setVisible(true);
                        activeCard = null;
                    } else {
                        System.out.println("more cards on table");
                        for (Card card : cardsOnTable) {
                            if (card.getValue().equals(attackCard.getValue())) {
                                System.out.println("attack card IS on table");
                                cardBox.getChildren().remove(activeCard);
                                System.out.println(1);
                                attack.setDisable(true);
                                attack.setStyle(activeCard.getStyle());
                                System.out.println(2);
                                defence.setVisible(true);
                                System.out.println(3);
                                activeCard = null;
                                System.out.println(card);
                            }
                        }
                    }

                }
            });

            defence.setOnMousePressed(mouseEvent -> {
                cardsInHand.forEach(card -> {
                    if (activeCard.getId().equals(card.getId())) {
                        defenseCard = card;
                    }
                });
                cardsOnTable.forEach(card -> {
                    if (defence.getParent().getChildrenUnmodifiable().get(0).getId().equals(card.getId())) {
                        attackCard = card;
                    }
                });
                System.out.println("ATTACK: " + attackCard + "/// Defence: " + defenseCard);
                if (activeCard != null && !defence.isDisable()) {
                    activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");

                    if (attackCard.getSuit().equals(defenseCard.getSuit())) {
                        if (defenseCard.getValue() > attackCard.getValue()) {
                            System.out.println("def > att");
                            cardBox.getChildren().remove(activeCard);
                            defence.setDisable(true);
                            defence.setStyle(activeCard.getStyle());
                        } else {
                            System.out.println("def NOT > att");
                            activeCard.setStyle(activeCard.getStyle());
                        }
                        activeCard = null;
                    } else if (defenseCard.getSuit().equals(trumpInfo)) {
                        System.out.println("def is TRUMP");
                        cardBox.getChildren().remove(activeCard);
                        defence.setDisable(true);
                        defence.setStyle(activeCard.getStyle());
                        activeCard = null;
                    } else {
                        System.out.println("this is shit card!");
                    }
                }
            });


//            for (Button placableCard : Arrays.asList(attack, defence)) {
//                placableCard.setOnMouseClicked(mouseEvent -> {
//                    if (activeCard != null && !placableCard.isDisable()) {
//                        cardBox.getChildren().remove(activeCard);
//                        placableCard.setDisable(true);
//                        placableCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
//                        activeCard = null;
//                        if (placableCard == attack) {
//                            defence.setVisible(true);
//                        }
//                    }
//                });
//            }
        }

        /// actions for buttons
        cardBoxScroll.setOnScroll(scrollEvent -> {
            if (invertScroll) {
                cardBoxScroll.setHvalue(cardBoxScroll.getHvalue() - scrollEvent.getDeltaY() / 1000);
            } else {
                cardBoxScroll.setHvalue(cardBoxScroll.getHvalue() + scrollEvent.getDeltaY() / 1000);
            }
        });

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

        /// pile EXPERIMENT!
        VBox pileBox = new VBox();
        for (int i = 0; i < 36; i++) {
            Label newB = new Label();
            newB.setMinSize(playfieldCardUnit * 2, playfieldCardUnit * 3);
            newB.setStyle("-fx-background-image: url('/images/cards/back_of_card.png'); -fx-background-size: cover");
            pileBox.getChildren().add(newB);
            newB.setTranslateX(-i);
            newB.setTranslateY(-i * 87);
        }
        pileBox.setTranslateX(windowWidth / 1.4);
        pileBox.setTranslateY(windowHeight * 2.1);

        /// playfield reset w/ cards from table to hand

        pickCardsUp.setOnAction(actionEvent -> {
//            if (PlayerWhoPressedButton.getPlayerState().equals(Player.PlayerState.DEFENSE)) {
            playFieldClass.setDefault(playFieldButtons);
            for (Card card : cardsOnTable) {
                buttonList.add(cardToButton(card, cardBox, cardWidth, cardHeight));
            }
            cardsInHand.addAll(cardsOnTable);
            cardsOnTable.clear();
        });

        clearTableButton.setOnAction(actionEvent -> {
//            if (PlayerWhoPressedButton.getPlayerState().equals(Player.PlayerState.DEFENSE)) {
            boolean clearable = true;
            for (Pane value : playFieldButtons.values()) {
                ObservableList<Node> x = value.getChildren();
                if ((!x.get(0).getStyle().contains("-fx-background-image: null")
                        && x.get(1).getStyle().contains("-fx-background-image: null"))
                        || (x.get(0).getStyle().contains("-fx-background-image: null")
                        && !x.get(1).getStyle().contains("-fx-background-image: null"))) {
                    clearable = false;
                    break;
                }
            }
            if (clearable) {
                cardsOnTable.clear();
                playFieldClass.setDefault(playFieldButtons);
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
            if (!change.getRemoved().isEmpty()) {
                cardsInHand.forEach(card -> {
                    if (card.getId().equals(change.getRemoved().get(0).getId())) {
                        cardsOnTable.add(card);
                    }
                });
                cardsInHand.removeIf(card -> card.getId().equals(change.getRemoved().get(0).getId()));
            }
        });

        gameStackPane.setId("gameStackPane");
        gameStackPane.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
        cardBoxBase.setId("cardBoxBase");
        cardBoxBase.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

        cardBoxBase.getChildren().addAll(pickCardsUp, clearTableButton);
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
