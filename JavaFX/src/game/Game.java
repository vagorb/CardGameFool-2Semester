package game;

import Client.Client;
import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.GameField;
import game.help.PlayField;
import game.help.Resolution;
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
import java.util.UUID;
import java.util.stream.Collectors;

public class Game extends Application {
    private Scene menuScene;
    private boolean fullscreenStatus;
    private boolean invertScroll;
    private Resolution resolution;
    private String uuid = UUID.randomUUID().toString();
    private int playersInTable = 2;
    private boolean gameIsGoing = true;
    private List<Card> sizeOfCards= new ArrayList<>();

    private double windowHeight;
    private double windowWidth;

    //    private final Deck deck = new Deck();
    private Player.PlayerState playerState;
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
            computer = new Ai();
        }
    }

    void setMenu(Scene menu) {
        this.menuScene = menu;
        windowWidth = menu.getWidth();
        windowHeight = menu.getHeight();
    }

    void setSettings(boolean invertScroll, boolean fullscreenStatus, Resolution resolution) {
        this.fullscreenStatus = fullscreenStatus;
        this.invertScroll = invertScroll;
        this.resolution = resolution;
        resolution.dragWindow();
    }

    private Button cardToButton(Card card, HBox destination, double cardWidth, double cardHeight) {
        Button button = new Button();
        Buttons.oneSizeOnly(button, cardWidth, cardHeight);
        button.setId(card.getId());
        button.setStyle(String.format("-fx-background-size: cover;-fx-background-image: "
                + "url('/images/cards/%s/%s.png')", card.getSuit(), card.getId()));
        destination.getChildren().add(button);
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
        JsonObject start = new JsonObject();
        start.addProperty("MessageType", "gameStart");
        start.addProperty("UUID", uuid);
        start.addProperty("GameSize", playersInTable);
        client.setMessage(start);
        try {
            Client.sendMessage(start);
            String response = Client.getResponse();
            while (response.equals("WAIT")) {
                Client.sendMessage(start);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = Client.getResponse();
            }
            JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
            String state = obj.get("State").toString();
            state = state.replace("\"", "");
            if (state.equalsIgnoreCase("ATTACK")) {
                playerState = Player.PlayerState.ATTACK;
            } else if (state.equalsIgnoreCase("DEFENCE")) {
                playerState = Player.PlayerState.DEFENSE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO ASK for trump CARD
        try {
            JsonObject trump = new JsonObject();
            trump.addProperty("UUID", uuid);
            trump.addProperty("MessageType", "getTrump");
            Client.sendMessage(trump);
            String response = Client.getResponse();
            trumpCard = cardFromResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buttons buttons = new Buttons();
        StackPane gameStackPane = new StackPane();
        Scene playScene = new Scene(gameStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox(20);
        HBox cardBox = new HBox(2);


        ScrollPane cardBoxScroll = new ScrollPane(cardBox);
        cardBoxScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardBoxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardBoxBase.setAlignment(Pos.CENTER);

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(16,16,16,0.9)");

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);

        Button backButton = buttons.back();
        Button exitButton = buttons.exit();
        Button throwCards = buttons.throwCards();
        Button pickUpCards = buttons.pickCardsUp();
        Button skipTurn = buttons.skipTurn();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 4, windowHeight / 12);

        for (Button btn : Arrays.asList(throwCards, pickUpCards, skipTurn)) {
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
            avatars.makeAvatar(new Ai());
        }
        for (int p = 0; p < humanCount; p++) {
            players.add(new Player("player", 0));
            avatars.makeAvatar(players.get(players.size() - 1));
        }
        HBox avatarPage = avatars.showAvatars();

        this.thePlayer = players.get(players.size() - 1);

        // card generator
//        deck.shuffleDeck();
        List<Card> cardsInHand = new LinkedList<>();
        List<Card> cardsOnTable = new LinkedList<>();  // need to get that info from Server
        replenishHand(cardBox, cardWidth, cardHeight, cardsInHand);

        /// playfield elements
        PlayField playFieldClass = new PlayField(cardUnitSize);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, HBox> playFieldButtons = playFieldClass.createButtons();
//        for (int i = 1; i <= 6; i++) {
//            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
//            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
//
//            if (i < 4) {
//                upperLayer.getChildren().addAll(playFieldButtons.get(i));
//            } else {
//                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
//            }
//
//            if (playerState == Player.PlayerState.ATTACK) {
//                attack.setOnMouseClicked(mouseEvent -> {
//                    if (activeCard != null && !attack.isDisable()) {
//                        attackCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
//                                .collect(Collectors.toList()).get(0);
//                        if (listOfCardsOnUITable.size() == 0) {
//                            JsonObject sendToServer = cardToJson(attackCard);
//                            client.setMessage(sendToServer);
//                            try {
//                                Client.sendMessage(sendToServer);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        String resp = Client.getResponse();
//                            System.out.println(resp);
////                        Card card = cardFromResponse(resp);
////                        System.out.println(card);
//                            listOfCardsOnUITable.add(attackCard.getValue());
//                            cardBox.getChildren().remove(activeCard);
//                            attack.setDisable(true);
//                            attack.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
//                            defence.setVisible(true);
//                            activeCard = null;
//                        } else {
//                            if (listOfCardsOnUITable.contains(attackCard.getValue())) {
//                                JsonObject sendToServer = cardToJson(attackCard);
//                                client.setMessage(sendToServer);
//                                try {
//                                    Client.sendMessage(sendToServer);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
////                                String resp = Client.getResponse();
////                                Card card = cardFromResponse(resp);
////                                System.out.println(card);
//                                cardBox.getChildren().remove(activeCard);
//                                attack.setDisable(true);
//                                attack.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
//                                defence.setVisible(true);
//                                activeCard = null;
//                            } else {
//                                activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
//                            }
//                        }
//                    }
//                });
//            } else if (playerState == Player.PlayerState.DEFENSE) {
//                defence.setOnMouseClicked(mouseEvent -> {
//                    if (activeCard != null && !defence.isDisable()) {
//                        defenseCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
//                                .collect(Collectors.toList()).get(0);
//                        if (attackCard.getSuit().equals(defenseCard.getSuit())) {
//                            if (defenseCard.getValue() > attackCard.getValue()) {
//                                JsonObject sendToServer = cardToJson(defenseCard);
//                                client.setMessage(sendToServer);
//                                try {
//                                    Client.sendMessage(sendToServer);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                String resp = Client.getResponse();
//                                listOfCardsOnUITable.add(defenseCard.getValue());
//                                cardBox.getChildren().remove(activeCard);
//                                defence.setDisable(true);
//                                defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
//                                playFieldClass.nextAttackVisible(defence);
//                            } else {
//                                activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
//                            }
//                            activeCard = null;
//                        } else if (defenseCard.getSuit().equals(trumpCard.getSuit())) {
//                            JsonObject sendToServer = cardToJson(defenseCard);
//                            client.setMessage(sendToServer);
//                            try {
//                                Client.sendMessage(sendToServer);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            String resp = Client.getResponse();
//                            Card card = cardFromResponse(resp);
//                            System.out.println(card);
//                            listOfCardsOnUITable.add(defenseCard.getValue());
//                            cardBox.getChildren().remove(activeCard);
//                            defence.setDisable(true);
//                            defence.setStyle(activeCard.getStyle() + ";-fx-opacity: 1");
//                            activeCard = null;
//                            playFieldClass.nextAttackVisible(defence);
//                        } else {
//                            activeCard.setStyle(activeCard.getStyle() + ";-fx-opacity: 1; -fx-border-color: null");
//                        }
//                    }
//                });
//            }
//        }

//        if (bool = true) {
//            if (playerState == Player.PlayerState.ATTACK && listOfCardsOnUITable.size() % 2 == 1) {
//                JsonObject opponent = new JsonObject();
//                opponent.addProperty("MessageType", "getOpponentCard");
//                opponent.addProperty("UUID", uuid);
//                client.setMessage(opponent);
//                try {
//                    Client.sendMessage(opponent);
//                    String response = Client.getResponse();
//                    while (response.equals("WAIT")) {
//                        Client.sendMessage(opponent);
//                        System.out.println(response = Client.getResponse());
//                    }
//                    Card card = cardFromResponse(response);
//                    listOfCardsOnUITable.add(card.getValue());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (playerState == Player.PlayerState.DEFENSE && listOfCardsOnUITable.size() % 2 == 0) {
//                JsonObject opponent = new JsonObject();
//                opponent.addProperty("MessageType", "getOpponentCard");
//                opponent.addProperty("UUID", uuid);
//                client.setMessage(opponent);
//                try {
//                    Client.sendMessage(opponent);
////                    String response = Client.getResponse();
//                    JsonObject response = JsonParser.parseString(Client.getResponse()).getAsJsonObject();
//                    String strResponse = response.get("MessageType").toString();
//                    strResponse = strResponse.replace("\"", "");
//
//                    if (strResponse.equals("WAIT")) {
//                        Client.sendMessage(opponent);
////                        System.out.println(response = Client.getResponse());
//                    } else {
//                        Card card = cardFromResponse(response.toString());
//                        listOfCardsOnUITable.add(card.getValue());
//                        for (int i = 0; i < 6; i++) {
//                            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
//                            if (defence.getStyle().contains("-fx-background-image: null")) {
//                                // cardBox ??
//                                Button buttonCard = cardToButton(card, playFieldButtons.get(i), cardWidth, cardHeight);
//                                break;
//                            }
//                        }
////                        Button buttonCard = cardToButton(card, cardBox, cardWidth, cardHeight);
//
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        this.bool = true;

        GameField gameFieldClass = new GameField(cardUnitSize, windowWidth);
        HBox gameFields = gameFieldClass.addFields(playField, trumpCard);
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

        skipTurn.setOnAction(actionEvent -> {
//            if (thePlayer.getPlayerState() == Player.PlayerState.ATTACK) {
            thePlayer.setPlayerState(Player.PlayerState.SKIP);   // need to send that info to Server as well
//            }
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
                deckcards.getChildren().remove(deckcards.getChildren().size() - 1);
            }
        });

        throwCards.setOnAction(actionEvent -> {
//            if (thePlayer.getPlayerState().equals(Player.PlayerState.DEFENSE &&
            if (playFieldClass.validToThrowCards() && cardsOnTable.size() != 0) {
                listOfCardsOnUITable.clear();
                for (Card card : cardsOnTable) {
                    gameFieldClass.throwCardToPile();
//                    getDeck().removeCard(card);   // need to get deck from Server
                }
                cardsOnTable.clear();
                playFieldClass.setDefault(playFieldButtons);
                replenishHand(cardBox, cardWidth, cardHeight, cardsInHand);
            }
        });

        playScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                menu.setVisible(!menu.isVisible());
            }
        });

        backButton.setOnAction(actionEvent -> window.setScene(menuScene));

        exitButton.setOnAction(actionEvent -> window.close());

        cardBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();
            if (change.wasRemoved()) {
                for (Card card : cardsInHand) {
                    if (card.getId().equals(change.getRemoved().get(0).getId())) {
                        cardsOnTable.add(card);
                        cardsInHand.remove(card);
                        break;
                    }
                }
            }
        });

        gameStackPane.setId("gameStackPane");
        gameStackPane.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
        cardBoxBase.setId("cardBoxBase");
        cardBoxBase.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());

        cardBoxBase.getChildren().addAll(pickUpCards, throwCards);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        gameStackPane.getChildren().addAll(avatarPage, gameFields, cardBoxBase, cardBoxScroll, menu);

        backButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 50));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 50));
        exitButton.prefWidthProperty().bind(backButton.prefHeightProperty());
        exitButton.prefHeightProperty().bind(backButton.prefHeightProperty());

        window.show();
//        boolean goIn = true;
//        while (gameIsGoing) {
//            if (goIn) {
        for (int i = 1; i <= 6; i++) {
//                    goIn = false;
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

            if (playerState == Player.PlayerState.ATTACK) {
                attack.setOnMouseClicked(mouseEvent -> {
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
                            System.out.println(resp);
//                        Card card = cardFromResponse(resp);
//                        System.out.println(card);
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
//                                String resp = Client.getResponse();
//                                Card card = cardFromResponse(resp);
//                                System.out.println(card);
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
            } else if (playerState == Player.PlayerState.DEFENSE) {
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
            }
        }
    }
//        }

//                    if (playerState == Player.PlayerState.ATTACK && listOfCardsOnUITable.size() % 2 == 1) {
//                JsonObject opponent = new JsonObject();
//                opponent.addProperty("MessageType", "getOpponentCard");
//                opponent.addProperty("UUID", uuid);
//                client.setMessage(opponent);
//                try {
//                    Client.sendMessage(opponent);
//                    String response = Client.getResponse();
//                    while (response.equals("WAIT")) {
//                        Client.sendMessage(opponent);
//                        System.out.println(response = Client.getResponse());
//                    }
//                    Card card = cardFromResponse(response);
//                    listOfCardsOnUITable.add(card.getValue());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (playerState == Player.PlayerState.DEFENSE && listOfCardsOnUITable.size() % 2 == 0) {
//                JsonObject opponent = new JsonObject();
//                opponent.addProperty("MessageType", "getOpponentCard");
//                opponent.addProperty("UUID", uuid);
//                client.setMessage(opponent);
//                try {
//                    Client.sendMessage(opponent);
////                    String response = Client.getResponse();
//                    JsonObject response = JsonParser.parseString(Client.getResponse()).getAsJsonObject();
//                    String strResponse = response.get("MessageType").toString();
//                    strResponse = strResponse.replace("\"", "");
//
//                    while (strResponse.equals("WAIT")) {
//                        Client.sendMessage(opponent);
////                        System.out.println(response = Client.getResponse());
//                    }
//                    Card card = cardFromResponse(response.toString());
//                    listOfCardsOnUITable.add(card.getValue());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

    // actions loop
//        for


    public void attackerLogic(Map<Integer, Pane> playFieldButtons, double cardWidth, double cardHeight) {
        JsonObject opponent = new JsonObject();
        opponent.addProperty("MessageType", "getOpponentCard");
        opponent.addProperty("UUID", uuid);
        opponent.addProperty("SIZE", sizeOfCards.size());
        client.setMessage(opponent);
        try {
            Client.sendMessage(opponent);
            String response = Client.getResponse();
            if (response.equals("WAIT")) {
                Client.sendMessage(opponent);
                System.out.println(Client.getResponse());
            } else {
                Card card = cardFromResponse(response);
                sizeOfCards.add(card);
                listOfCardsOnUITable.add(card.getValue());
                for (int i = 0; i < 6; i++) {
                    Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
                    if (defence.getStyle().contains("-fx-background-image: null")) {
                        // cardBox ??
//                        Button buttonCard = cardToButton(card, playFieldButtons.get(i), cardWidth, cardHeight);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void defenderLogic(Map<Integer, HBox> playFieldButtons, double cardWidth, double cardHeight) {
        JsonObject opponent = new JsonObject();
        opponent.addProperty("MessageType", "getOpponentCard");
        opponent.addProperty("UUID", uuid);
        opponent.addProperty("SIZE", sizeOfCards.size());
        client.setMessage(opponent);
        try {
            Client.sendMessage(opponent);
//                    String response = Client.getResponse();
            JsonObject response = JsonParser.parseString(Client.getResponse()).getAsJsonObject();
            String strResponse = response.get("MessageType").toString();
            strResponse = strResponse.replace("\"", "");

            if (strResponse.equals("WAIT")) {
                Client.sendMessage(opponent);
//                        System.out.println(response = Client.getResponse());
            } else {
                Card card = cardFromResponse(response.toString());
                sizeOfCards.add(card);
                listOfCardsOnUITable.add(card.getValue());
                for (int i = 0; i < 6; i++) {
                    Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);
                    if (defence.getStyle().contains("-fx-background-image: null")) {
                        // cardBox ??
                        cardToButton(card, playFieldButtons.get(i), cardWidth, cardHeight);
                        break;
                    }
                }
//                        Button buttonCard = cardToButton(card, cardBox, cardWidth, cardHeight);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject gameStart() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "GameStart");
        return obj;
    }

    public JsonObject gameCardsReplenish() {
        JsonObject obj = new JsonObject();
        obj.addProperty("MessageType", "replenish");
        obj.addProperty("UUID", uuid);
        return obj;
    }

    public void replenishHand(HBox cardBox, double cardWidth, double cardHeight, List<Card> cardsInHand) {
        JsonObject obj = gameCardsReplenish();
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

    public JsonObject cardToJson(Card card) {
        JsonObject obj = new JsonObject();
        obj.addProperty("UUID", uuid);
        obj.addProperty("MessageType", "GameMove");
//        obj.addProperty("PlayerName", "Sanja"); //player.getName()
        obj.addProperty("MoveType", card.toString()); //player.getPlayerState().toString()
        obj.addProperty("Value", card.getValue()); //table.getLastCardOnTable().getId());
        obj.addProperty("Suit", card.getSuit());
        obj.addProperty("Trump", card.getTrump());
        return obj;
    }

    public Card cardFromResponse(String resp) {
        JsonObject jsonObject = JsonParser.parseString(resp).getAsJsonObject();
        String suit = jsonObject.get("Suit").toString();
        suit = suit.replace("\"", "");
        String value = jsonObject.get("Value").toString();
        value = value.replace("\"", "");
        Integer val = Integer.parseInt(value);
        String trump = jsonObject.get("Trump").toString();
        trump = trump.replace("\"", "");
        Boolean tru = Boolean.parseBoolean(trump);
        return new Card(suit, val, tru);
    }



    // TODO account for trump card not being part of the DECK in the current gameplay


    public static void main(String[] args) {
        launch(args);
    }
}
