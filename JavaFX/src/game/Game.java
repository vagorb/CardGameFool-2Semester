package game;

import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.cards.Deck;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.PlayField;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;
    private boolean invertScroll;

    private Deck deck = new Deck();
    private Button forAttackComparison;
    private Card attackCard;
    private List<Integer> listOfCardsOnUITable = new ArrayList<>();
    private Player player;

    private Button forDefenseComparison;
    private Card defenseCard;
    private String trumpInfo;
    private List<Button> buttonsToDelete = new ArrayList<>();
    private int counter = 0;



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

        Player current = players.get(players.size() - 1);
        this.player = current;
        System.out.println(player.getPlayerState());
        // HERE WE GET INFO ON WHICH PLAYERSTATE WE SHOULD HAVE (CURRENTLY IT WILL DEFAULT AS ATTACK )
        this.player.setPlayerState(Player.PlayerState.ATTACK);

        // kaardi generaator
        List<Button> buttonList = new ArrayList<>();
        List<Card> cardsInHand = new LinkedList<>();
        getCardsFromDeckAndCreatePNG(cardWidth, cardHeight, cardBox, buttonList, cardsInHand);

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
//            attack.setOnMousePressed(mouseEvent -> {
//                System.out.println();
//                System.out.println("ACTIVE " + activeCard + ">>>" + activeCard.getId());
//                if (activeCard != null && !attack.isDisable()) {
//                    System.out.println("???");
//                    activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
            if (player.getPlayerState() == Player.PlayerState.ATTACK) {
                attack.setOnMouseClicked(mouseEvent -> {
                    if (activeCard != null && !attack.isDisable()) {
                        forAttackComparison = activeCard;
                        attackCard = Card.javaFXCardToCard(forAttackComparison.getId());
                        if (listOfCardsOnUITable.size() == 0) {
                            listOfCardsOnUITable.add(attackCard.getValue());
                            buttonsToDelete.add(activeCard);
                            cardBox.getChildren().remove(activeCard);
                            attack.setDisable(true);
                            attack.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                            defence.setVisible(true);
                            activeCard = null;
                        } else {
                            if (listOfCardsOnUITable.contains(attackCard.getValue())) {
                                buttonsToDelete.add(activeCard);
                                cardBox.getChildren().remove(activeCard);
                                attack.setDisable(true);
                                attack.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                                defence.setVisible(true);
                                activeCard = null;
                            } else {
                                activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                            }
                        }
                    }
                });
            } else if (player.getPlayerState() == Player.PlayerState.DEFENSE) {
                defence.setOnMouseClicked(mouseEvent -> {
                    if (activeCard != null && !defence.isDisable()) {
                        forDefenseComparison = activeCard;
                        defenseCard = Card.javaFXCardToCard(forDefenseComparison.getId());
                        if (attackCard.getSuit().equals(defenseCard.getSuit())) {
                            if (defenseCard.getValue() > attackCard.getValue()) {
                                listOfCardsOnUITable.add(defenseCard.getValue());
                                buttonsToDelete.add(activeCard);
                                cardBox.getChildren().remove(activeCard);
                                defence.setDisable(true);
                                defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                                activeCard = null;
                                forAttackComparison = null;
                                forDefenseComparison = null;
                                paneVisibility(defence, playFieldButtons);
                            } else {
                                activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                                activeCard = null;
                            }
                        } else if (defenseCard.getSuit().equals(trumpInfo)) {
                            listOfCardsOnUITable.add(defenseCard.getValue());
                            buttonsToDelete.add(activeCard);
                            cardBox.getChildren().remove(activeCard);
                            defence.setDisable(true);
                            defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                            activeCard = null;
                            forAttackComparison = null;
                            forDefenseComparison = null;
                            paneVisibility(defence, playFieldButtons);
                        } else {
                            activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                        }


                    }
                });
            }
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

//        Player current = players.get(players.size() - 1);
        pickCardsUp.setOnAction(actionEvent -> {
                    playFieldButtons.forEach((integer, pane) -> {
                        if (current.getPlayerState() != null) { /// .equals(Player.PlayerState.DEFENSE)) {
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
//                    for (int x = 0; x < 3; x++) {
////                        if (pileBox.getChildren().) {
//                        if (index[0] >= 0) {
//                            pileBox.getChildren().get(index[0]--).setVisible(false);
//                        }
//                    }
                    // after taking cards . the cards from the table are removed so they are not accounted for in future turns.
                    listOfCardsOnUITable.removeAll(listOfCardsOnUITable);
                    buttonList.removeAll(buttonsToDelete);
                    resetPaneVisibility(playFieldButtons);
//                    for (Card card : cardsToDelete) {
//                        deck.removeCard(card);
//                    }
                    getCardsFromDeckAndCreatePNG(cardWidth, cardHeight, cardBox, buttonList, cardsInHand);
                }
        );

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

//        cardBox.getChildren().addListener((ListChangeListener<Node>) change -> {
//            change.next();
//            if (!change.getRemoved().isEmpty()) {
//                cardsInHand.forEach(card -> {
//                    if (card.getId().equals(change.getRemoved().get(0).getId())) {
//                        cardsOnTable.add(card);
//                    }
//                });
//                cardsInHand.removeIf(card -> card.getId().equals(change.getRemoved().get(0).getId()));
//            }
//        });

//        cardBox.getChildren().addListener((ListChangeListener<Node>) change -> {
//            change.next();
//            cardsInHand.removeIf(card -> card.getId().equals(change.getRemoved().get(0).getId()));
//            System.out.println(cardsInHand);
//        });

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

    public void getCardsFromDeckAndCreatePNG(double cardWidth, double cardHeight, HBox cardBox, List<Button> buttonList, List<Card> cardsInHand) {
        while(buttonList.size() < 6) {
            if (counter <= 34) {
                Card card = deck.getDeck().get(counter);
                cardsInHand.add(card);
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
                counter += 1;
            } else
                break;
        }
    }

    public void paneVisibility(Button defence, Map<Integer, Pane> playFieldButtons) {
        for (int nr = 1; nr <= 6; nr++) {
            if(defence==playFieldButtons.get(nr).getChildrenUnmodifiable().get(1)) {
                playFieldButtons.get(nr+1).setVisible(true);break;
            }
        }
    }

    public void resetPaneVisibility(Map<Integer, Pane> playFieldButtons) {
        for (int nr = 2; nr <= 6; nr++) {
                playFieldButtons.get(nr).setVisible(false);
            }
        }


    // ^^ IT fucks up like this due to me coding a comparison system that is capable of comparing only currently put cards
    // 6 H > 6 P > comparison card is only 6 P now so i can potentially defend IT only.
    // TODO account for trump card not being part of the DECK in the current gameplay


    public static void main(String[] args) {
        launch(args);
    }
}
