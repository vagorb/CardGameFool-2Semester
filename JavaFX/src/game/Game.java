package game;

import Client.Client;
import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.CardPackField;
import game.help.PlayField;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Game extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;
    private boolean invertScroll;
    private boolean fixedResolution;

    private double windowHeight;
    private double windowWidth;

    //    private final Deck deck = new Deck();
    private Player thePlayer;
    private Client client = new Client();

    private Card attackCard;
    private Card defenseCard;
    private Card trumpCard;
    private Set<Integer> listOfCardsOnUITable = new HashSet<>();

    private Button activeCard = null;
    private final int AIcount;
    private final int humanCount;
    private List<Player> players = new ArrayList<>();
    public Ai computer;

    public Game(int human, int AI) {
        this.humanCount = human;
        this.AIcount = AI;
        if (AIcount > 0) {
            computer = new Ai(new Hand());
        }
    }

    void setMenu(Scene menu) {
        this.menuScene = menu;
        windowWidth = menu.getWidth();
        windowHeight = menu.getHeight();
    }

    void setSettings(boolean invertScroll, boolean fullscreenStatus, boolean fixedResolution) {
        this.fullscreenStatus = fullscreenStatus;
        this.invertScroll = invertScroll;
        this.fixedResolution = fixedResolution;
    }

    private Button cardToButton(Card card, HBox cardBox, double cardWidth, double cardHeight) {
        Button button = new Button();
        Buttons.oneSizeOnly(button, cardWidth, cardHeight);
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
        return button;
    }

    public void start(Stage window) {
        window.setResizable(false);
//        deck.shuffleDeck();
//        trumpCard = deck.getDeck().get(0);
        // I NEED TO ACCOUNT FOR THE FACT THAT THIS CARD WILL BE REMOVED FROM THE DECK AS A SEPARATE CARD
//        deck.removeCard(trumpCard);
//        System.out.println(trumpCard.getSuit());

        Buttons buttons = new Buttons();
        StackPane gameStackPane = new StackPane();
//        StackPane playeradding = new StackPane();
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
        Button throwCards = buttons.throwCards();
        Button pickUpCards = buttons.pickCardsUp();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 4, windowHeight / 12);

        for (Button btn : Arrays.asList(throwCards, pickUpCards)) {
            Buttons.oneSizeOnly(btn, cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
            btn.setTranslateX(cardBoxBase.getMaxWidth() / 2.5);
        }
        cardBoxBase.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);
        cardBoxScroll.setTranslateY((windowHeight - cardBoxBase.getMaxHeight()) / 2);

        double cardHeight = cardBoxScroll.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 90 * 59;
        double cardUnitSize = cardWidth * 0.9;

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

        this.thePlayer = players.get(players.size() - 1);
        // HERE WE GET INFO ON WHICH PLAYERSTATE WE SHOULD HAVE (CURRENTLY IT WILL DEFAULT AS ATTACK )
        this.thePlayer.setPlayerState(Player.PlayerState.ATTACK);

        // card generator
//        deck.shuffleDeck();
        List<Card> cardsInHand = new LinkedList<>();
        List<Card> cardsOnTable = new LinkedList<>();
        // THIS WILL TEMPORARILY FIX HANDS HAVING COPIES OF CARDS (WILL WORK LIKE THIS UNTIL SERVER DECK IS IMPLEMENTED)
        replenishHand(cardBox, cardWidth, cardHeight, cardsInHand);

        /// playfield elements
        PlayField playFieldClass = new PlayField(cardUnitSize);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, Pane> playFieldButtons = playFieldClass.createButtons();
        for (int i = 1; i <= 6; i++) {
            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
            attack.setId("Attack");
            attack.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

            attack.setTranslateX(cardUnitSize);
            attack.setTranslateY(cardUnitSize);

            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
            defence.setId("Defence");
            defence.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
            defence.setVisible(false);

            if (i < 4) {
                upperLayer.getChildren().addAll(playFieldButtons.get(i));
            } else {
                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
            }
//            if (thePlayer.getPlayerState() == Player.PlayerState.ATTACK) {
            attack.setOnMouseClicked(mouseEvent -> {
                // PlayerState
                // Card that i put on the table
                // PlayerName
                // MessageType =
//                    Text
//                            TEXT
                if (activeCard != null && !attack.isDisable()) {
                    attackCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
                            .collect(Collectors.toList()).get(0);
                    if (listOfCardsOnUITable.size() == 0) {
                        JsonObject sendToServer = cardToJson(attackCard);
                        client.setMessage(sendToServer);
                        try {
                            Client.sendMessage(sendToServer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String resp = Client.getResponse();
                        Card card = cardFromResponse(resp);
                        System.out.println(card);
                        listOfCardsOnUITable.add(attackCard.getValue());
                        cardBox.getChildren().remove(activeCard);
                        attack.setDisable(true);
                        attack.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                        defence.setVisible(true);
                        activeCard = null;
                    } else {
                        if (listOfCardsOnUITable.contains(attackCard.getValue())) {
                            JsonObject sendToServer = cardToJson(attackCard);
                            client.setMessage(sendToServer);
                            try {
                                Client.sendMessage(sendToServer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String resp = Client.getResponse();
                            Card card = cardFromResponse(resp);
                            System.out.println(card);
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
//            } else if (thePlayer.getPlayerState() == Player.PlayerState.DEFENSE) {

//            } else if (thePlayer.getPlayerState() == Player.PlayerState.DEFENSE) {
            defence.setOnMouseClicked(mouseEvent -> {
                if (activeCard != null && !defence.isDisable()) {
                    defenseCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
                            .collect(Collectors.toList()).get(0);
                    if (attackCard.getSuit().equals(defenseCard.getSuit())) {
                        if (defenseCard.getValue() > attackCard.getValue()) {
                            JsonObject sendToServer = cardToJson(defenseCard);
                            client.setMessage(sendToServer);
                            try {
                                Client.sendMessage(sendToServer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String resp = Client.getResponse();
                            Card card = cardFromResponse(resp);
                            System.out.println(card);
                            listOfCardsOnUITable.add(defenseCard.getValue());
                            cardBox.getChildren().remove(activeCard);
                            defence.setDisable(true);
                            defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                            playFieldClass.nextAttackVisible(defence);
                        } else {
                            activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                        }
                        activeCard = null;
                    } else if (defenseCard.getSuit().equals(trumpCard.getSuit())) {
                        JsonObject sendToServer = cardToJson(defenseCard);
                        client.setMessage(sendToServer);
                        try {
                            Client.sendMessage(sendToServer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String resp = Client.getResponse();
                        Card card = cardFromResponse(resp);
                        System.out.println(card);
                        listOfCardsOnUITable.add(defenseCard.getValue());
                        cardBox.getChildren().remove(activeCard);
                        defence.setDisable(true);
                        defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
                        activeCard = null;
                        playFieldClass.nextAttackVisible(defence);
                    } else {
                        activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
                    }
                }
            });
//            }
//            }
        }

        HBox gameFields = new CardPackField(cardUnitSize, windowWidth, throwCards).addFields(playField, trumpCard);
        gameFields.setTranslateX(windowWidth * 9 / 40);
        gameFields.setTranslateY((windowHeight / 2 - 4 * cardUnitSize));

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

        /// playfield reset w/ cards from table to hand
        pickUpCards.setOnAction(actionEvent -> {
//            if (thePlayer.getPlayerState().equals(Player.PlayerState.DEFENSE)) {
            playFieldClass.setDefault(playFieldButtons);
            for (Card card : cardsOnTable) {
                cardToButton(card, cardBox, cardWidth, cardHeight);
            }
            cardsInHand.addAll(cardsOnTable);
            listOfCardsOnUITable.clear();
            cardsOnTable.clear();
//            }
        });

        /// throw cards to pile
        throwCards.setOnAction(actionEvent -> {
//            if (thePlayer.getPlayerState().equals(Player.PlayerState.DEFENSE)) {
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

                listOfCardsOnUITable.clear();
                cardsOnTable.clear();
                playFieldClass.setDefault(playFieldButtons);
                replenishHand(cardBox, cardWidth, cardHeight, cardsInHand);
                HBox deckcards = (HBox) ((HBox) gameFields.getChildren().get(0)).getChildren().get(0);
//                for (int i = 0; i <= CardsTakenFromDeck; i++) {
                deckcards.getChildren().remove(deckcards.getChildren().size() - 1);
//                }
//                }
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
            window.setResizable(fixedResolution);
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

//        gameFields.getChildren().addAll(deckField, playField, pileField);
        cardBoxBase.getChildren().addAll(pickUpCards, throwCards);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        gameStackPane.getChildren().addAll(menu, avatarPage, gameFields, cardBoxBase, cardBoxScroll);

        backButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 50));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 50));
        exitButton.prefWidthProperty().bind(backButton.prefHeightProperty());
        exitButton.prefHeightProperty().bind(backButton.prefHeightProperty());

        window.show();
    }


    public JsonObject gameStart() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "gameMove");
//        obj.addProperty("PlayerName", "Sanja"); //player.getName()
//        obj.addProperty("MoveType", card.toString()); //player.getPlayerState().toString()
//        //if (!player.getPlayerState().equals(Player.PlayerState.SKIP)) {
//        obj.addProperty("Card", card.getValueName()); //table.getLastCardOnTable().getId());
        return obj;
    }

    public void replenishHand(HBox cardBox, double cardWidth, double cardHeight, List<Card> cardsInHand) {
//        List<Card> cardsToDelete =  new ArrayList<>();
//        for (Card card : deck.getDeck()) {
//            if (cardBox.getChildren().size() < 6) {
//                cardsInHand.add(card);
//                cardsToDelete.add(card);
//                cardToButton(card, cardBox, cardWidth, cardHeight);
//            }
//        }
//        for (Card card : cardsToDelete) {
//            deck.removeCard(card);
//        }
//        cardsToDelete.removeAll(cardsToDelete);
        JsonObject obj = gameStart();
//        trumpCard
        while (cardBox.getChildren().size() < 6) {
            try {
                Client.sendMessage(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String resp = Client.getResponse();
            Card card = cardFromResponse(resp);
            cardsInHand.add(card);
            cardToButton(card, cardBox, cardWidth, cardHeight);
        }
    }

    public void paneVisibility(Button defence, Map<Integer, Pane> playFieldButtons) {
        for (int nr = 1; nr <= 6; nr++) {
            if (defence == playFieldButtons.get(nr).getChildrenUnmodifiable().get(1)) {
                playFieldButtons.get(nr + 1).setVisible(true);
                break;
            }
        }
    }

    public void resetPaneVisibility(Map<Integer, Pane> playFieldButtons) {
        for (int nr = 2; nr <= 6; nr++) {
            playFieldButtons.get(nr).setVisible(false);
        }
    }

    public JsonObject cardToJson(Card card) {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "gameMove");
        obj.addProperty("PlayerName", "Sanja"); //player.getName()
        obj.addProperty("MoveType", card.toString()); //player.getPlayerState().toString()
        //if (!player.getPlayerState().equals(Player.PlayerState.SKIP)) {
        obj.addProperty("Card", card.getValueName()); //table.getLastCardOnTable().getId());
//        obj.get("Card").getAsJsonObject().addProperty("Name", card.getValueName());
//        obj.get("Card").getAsJsonObject().addProperty("Suit", card.getSuit());
//        obj.get("Card").getAsJsonObject().addProperty("Value", card.getValue());
//        obj.get("Card").getAsJsonObject().addProperty("Trump", card.getTrump());
        //}
        return obj;
    }

    public Card cardFromResponse(String resp) {
        JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
        String suit = jsonObject.get("Suit").toString();
        suit = suit.replace("\"", "");
        String value = jsonObject.get("Value").toString();
        value = value.replace("\"", "");
        Integer val = Integer.parseInt(value);
        String trump = jsonObject.get("Trump").toString();
        trump = trump.replace("\"", "");
        Boolean tru = Boolean.parseBoolean(trump);
//        String id = jsonObject.get("Id").toString();
//        id = id.replace("\"", "");
        return new Card(suit, val, tru);
    }

//    public void jsonInfoToCards()


    // 6 H > 6 P > comparison card is only 6 P now so i can potentially defend IT only.
    // TODO account for trump card not being part of the DECK in the current gameplay


    public static void main(String[] args) {
        launch(args);
    }
}
