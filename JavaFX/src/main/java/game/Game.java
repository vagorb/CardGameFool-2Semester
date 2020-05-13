package game;

import Client.Client;
import Server.GameInfo;
import Server.model.NewPlayer;
import Server.model.NewPlayerAI;
import com.card.game.fool.AI.Ai;
import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Player;
import com.card.game.fool.players.PlayerState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import game.help.AvatarBox;
import game.help.Buttons;
import game.help.GameField;
import game.help.PlayField;
import game.help.Resolution;
import game.service.WaitForGameStartService;
import game.service.WaitForMyTurnService;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static javafx.concurrent.Worker.State.SUCCEEDED;

public class Game extends Application {
    private final Gson gson = new Gson();
    private Scene menuScene;
    private boolean fullscreenStatus;
    private boolean invertScroll;
    private Resolution resolution;
    private final String playerId = UUID.randomUUID().toString();
    private final Label stateLabel = new Label();
    private boolean goHere = false;

    private boolean uiIsLocked = true;
    private Map<Integer, HBox> playFieldButtons;
    private GameInfo currentGameState;
    private GameField gameFieldClass;
    private List<Card> cardsOnTable = new ArrayList<>();
    private final List<Card> cardsInHand = new ArrayList<>();
    private HBox cardBox;
    private ScrollPane cardBoxScroll;
    private VBox endGameScreen;

    private double windowHeight;
    private double windowWidth;

    private WaitForMyTurnService waitForMyTurnService;

    private Card attackCard;
    private Card defenseCard;

    private Button activeCard = null;
    private final int AIcount;
    private final int humanCount;
    public Ai computer;

    public Game(int human, int AI) {
        this.humanCount = human;
        this.AIcount = AI;
        if (AIcount > 0) {
            computer = new Ai();
        }
    }

    void setMenu(Scene menuScene) {
        this.menuScene = menuScene;
        windowWidth = menuScene.getWidth();
        windowHeight = menuScene.getHeight();
    }

    void setSettings(boolean invertScroll, boolean fullscreenStatus, Resolution resolution) {
        this.fullscreenStatus = fullscreenStatus;
        this.invertScroll = invertScroll;
        this.resolution = resolution;
    }

    public void setPlayerName(String name) {
    }

    private Button cardToButton(Card card, HBox destination, double cardWidth, double cardHeight) {
        Button button = new Button();
        Buttons.oneSizeOnly(button, cardWidth, cardHeight);
        button.setId(card.getId());
        button.setStyle(card.getStyleForButton());
        destination.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            if (activeCard != null) {
                activeCard.setStyle(activeCard.getStyle() + "-fx-opacity: 100%; -fx-border-color: null;");
            }
            activeCard = button;
            activeCard.setId(button.getId());
            activeCard.setStyle(button.getStyle() + "-fx-opacity: 60%; -fx-border-color: rgb(255,200,0);");
        });
        return button;
    }

    private GameInfo sendNewPlayerMsg() throws IOException {
        NewPlayer newPlayerMsg = new NewPlayer();
        newPlayerMsg.playerId = playerId;
        newPlayerMsg.type = "newPlayer";
        String response = Client.sendMessage(newPlayerMsg);
        return gson.fromJson(response, GameInfo.class);
    }

    private GameInfo sendNewAIPlayerMsg() throws IOException {
        NewPlayerAI newPlayerMsg = new NewPlayerAI();
        newPlayerMsg.playerId = playerId;
        newPlayerMsg.type = "AIGame";
        newPlayerMsg.State = "Update";
        String response = Client.sendMessage(newPlayerMsg);
        return gson.fromJson(response, GameInfo.class);
    }


    public void start(Stage window) throws IOException {
        GameInfo gameInfo;
        if (humanCount > 0) {
            gameInfo = sendNewPlayerMsg();
        } else {
            gameInfo = sendNewAIPlayerMsg();
        }
        window.setResizable(false);

        Buttons buttons = new Buttons();
        StackPane gameStackPane = new StackPane();
        Scene playScene = new Scene(gameStackPane, windowWidth, windowHeight);
        HBox cardBoxBase = new HBox(20);
        cardBox = new HBox(2);

        cardBoxScroll = new ScrollPane(cardBox);
        cardBoxScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardBoxScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cardBoxBase.setAlignment(Pos.CENTER);

        HBox menu = new HBox(25);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(16,16,16,0.9);");

        endGameScreen = new VBox();
        endGameScreen.setVisible(false);

        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle(playerId + "_Play.exe");
        window.setFullScreen(fullscreenStatus);
        window.setScene(playScene);

        Button backButton = buttons.back();
        Button backButton2 = buttons.back();
        Button exitButton = buttons.exit();
        Button throwCards = buttons.throwCards();
        Button pickUpCards = buttons.pickCardsUp();
        Button skipTurn = buttons.skipTurn();

        cardBoxBase.setMaxSize(windowWidth, windowHeight / 12);
        cardBoxScroll.setMaxSize(windowWidth / 4, windowHeight / 12);

        for (Button btn : Arrays.asList(throwCards, pickUpCards, skipTurn)) {
            Buttons.oneSizeOnly(btn, cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
            btn.setTranslateX(cardBoxBase.getMaxWidth() / 2.3);
        }
        stateLabel.setMinSize(cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
        stateLabel.setMaxSize(cardBoxBase.getMaxHeight(), cardBoxBase.getMaxHeight());
        stateLabel.setTranslateX(-cardBoxBase.getMaxWidth() / 2);

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
            avatars.makeAvatar(new Player());
        }
        HBox avatarPage = avatars.showAvatars();

        replenishHand();

        /// playfield elements
        PlayField playFieldClass = new PlayField(cardUnitSize);
        VBox playField = (VBox) playFieldClass.createPlayfield().get(0);
        HBox upperLayer = (HBox) playFieldClass.createPlayfield().get(1);
        HBox lowerLayer = (HBox) playFieldClass.createPlayfield().get(2);
        Map<Integer, HBox> playFieldButtons = playFieldClass.createButtons();
        this.playFieldButtons = playFieldButtons;

        gameFieldClass = new GameField(cardUnitSize, windowWidth);
        HBox gameFields = gameFieldClass.addFields(playField, gameInfo.getTrump());
        gameFields.setTranslateX(windowWidth * 9 / 40);
        gameFields.setTranslateY((windowHeight / 2 - 4 * cardUnitSize));

        /// actions for buttons
        resolution.dragWindow();

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
            if (cardsInHand.size() == 0) {
            JsonObject endGame = new JsonObject();
            endGame.addProperty("type", "endGame");
            endGame.addProperty("Size", cardsInHand.size());
            endGame.addProperty("playerId", playerId);
            String response = null;
            try {
                response = Client.sendMessage(endGame);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.contains("The game has finished")) {
                if (cardsInHand.size() == 0) {
                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9); -fx-background-size: cover;" +
                            "-fx-background-image: url('/images/backgrounds/winnerEndGame.png')");
                    endGameScreen.setVisible(true);
                    //gameEnd();
                    //state = PlayerState.SKIP;
                    uiIsLocked = true;
                } else {
                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9);-fx-background-size: cover;" +
                            "-fx-background-image: url('/images/backgrounds/foolEndGame.png')");
                    endGameScreen.setVisible(true);

                    //gameEnd();
                    //state = PlayerState.SKIP;
                    uiIsLocked = true;
                }
            }
            }
//            if (thePlayer.getPlayerState() == Player.PlayerState.ATTACK) {
//            thePlayer.setPlayerState(PlayerState.SKIP);   // need to send that info to Server as well
//            cardBoxScroll.setDisable(true);
//            }
//            if (currentGameState.getEndTheGame()) {
//                if (cardsInHand.size() == 0) {
//                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9); -fx-background-size: cover;" +
//                            "-fx-background-image: url('images/backgrounds/winnerEndGame.png')");
//                    endGameScreen.setVisible(true);
//                    //gameEnd();
//                    //state = PlayerState.SKIP;
//                    uiIsLocked = true;
//                } else {
//                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9);-fx-background-size: cover;" +
//                            "-fx-background-image: url('images/backgrounds/foolEndGame.png')");
//                    endGameScreen.setVisible(true);
//
//                    //gameEnd();
//                    //state = PlayerState.SKIP;
//                    uiIsLocked = true;
//                }
//            }

        });

        /// When unable to beat, pick all cards
        pickUpCards.setOnAction(actionEvent -> {
            if (currentGameState.getPlayerState(playerId) == PlayerState.DEFENSE && cardsOnTable.size() % 2 != 0
                    && !cardsOnTable.isEmpty()) {
                JsonObject msg = new JsonObject();
                msg.addProperty("type", "pickCardsFromTable");
                msg.addProperty("playerId", playerId);
                try {
                    String response = Client.sendMessage(msg);
                    List<Card> cards = gson.fromJson(response, new TypeToken<List<Card>>() {
                    }.getType());
                    for (Card card : cards) {
                        cardToButton(card, cardBox, cardWidth, cardHeight);
                    }
                    cardsInHand.addAll(cards);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (gameInfo.getDeckIsEmpty() && gameInfo.getEndTheGame()) {
                    gameEnd();
                } else {
                    resetAttackDefButtons();
                    waitForMyTurn();
                }
            }
        });

        /// throw cards to pile
        throwCards.setOnAction(actionEvent -> {
            if (currentGameState.getPlayerState(playerId) == PlayerState.ATTACK && cardsOnTable.size() % 2 == 0 && !cardsOnTable.isEmpty()) {
                JsonObject msg = new JsonObject();
                msg.addProperty("type", "throwCardsToPile");
                msg.addProperty("playerId", playerId);
                try {
                    Client.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resetAttackDefButtons();

                cardsOnTable.clear();
                replenishHand();
                if (cardsInHand.size() == 0 || !currentGameState.getFool().equals("")) {
                    gameEnd();
                } else {
                    waitForMyTurn();
                }
//                if (cardsInHand.size() == 0) {
//
//                }
                // TODO this has to end the game
//                if (cardsInHand.size() == 0) {
//                    System.out.println("LOCK THIS ");
////                    goHere = true;
////                uiIsLocked = true;
//                    // Make the game end
////                stop();
//                    Label label = new Label();
//                    label.setText("Congratulations, you are the winner!");
//                    cardBox.getChildren().add(label);
//                    try {
//                        Client.sendMessage(gameEnd());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    uiIsLocked = true;
//                }
            }
        });

        playScene.setOnKeyPressed(button -> {
            if (button.getCode() == KeyCode.ESCAPE) {
                menu.setVisible(!menu.isVisible());
            }
        });

        for (Button btn : List.of(backButton, backButton2)) {
            btn.setOnAction(actionEvent -> {
                window.setScene(menuScene);
                window.setFullScreen(fullscreenStatus);
            });
        }


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

        cardBoxBase.getChildren().addAll(pickUpCards, throwCards, skipTurn, stateLabel);
        playField.getChildren().addAll(upperLayer, lowerLayer);
        menu.getChildren().addAll(backButton, exitButton);
        endGameScreen.getChildren().add(backButton2);
        gameStackPane.getChildren().addAll(avatarPage, gameFields, cardBoxBase, cardBoxScroll, menu, endGameScreen);

        backButton2.setTranslateX(windowWidth / 2);
        backButton2.setTranslateY(windowHeight / 2);
        backButton.prefWidthProperty().bind(Bindings.divide(window.widthProperty(), 50));
        backButton.prefHeightProperty().bind(Bindings.divide(window.heightProperty(), 50));
        exitButton.prefWidthProperty().bind(backButton.prefHeightProperty());
        exitButton.prefHeightProperty().bind(backButton.prefHeightProperty());

        window.show();

        // Service for waiting for opponent move
        waitForMyTurnService = new WaitForMyTurnService();
        waitForMyTurnService.setPlayerId(playerId);

        waitForMyTurnService.setOnSucceeded(workerStateEvent -> {
            GameInfo game = (GameInfo) workerStateEvent.getSource().getValue();
            updateAttackDefButtons(game);
            doGameAction(game);
        });

        // Service for waiting for game start
        WaitForGameStartService gameStartService = new WaitForGameStartService();
        gameStartService.setPlayerId(playerId);

        gameStartService.setOnSucceeded(workerStateEvent -> {
            System.out.println(playerId + "Starting game");
            GameInfo game = (GameInfo) workerStateEvent.getSource().getValue();

            if (game.getPlayerState(playerId) == PlayerState.ATTACK) {
                showFirstAttackBtn();
            }

            doGameAction(game);
        });


        for (int i = 1; i <= 6; i++) {
            Button attack = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(0);
            Button defence = (Button) playFieldButtons.get(i).getChildrenUnmodifiable().get(1);

            if (i < 4) {
                upperLayer.getChildren().addAll(playFieldButtons.get(i));
            } else {
                lowerLayer.getChildren().addAll(playFieldButtons.get(i));
            }

            attack.setOnMouseClicked(mouseEvent -> {
                if (uiIsLocked) {
                    return;
                }
                if (activeCard != null) {
                    attackCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
                            .collect(Collectors.toList()).get(0);
                    if (cardsOnTable.isEmpty()) {
                        JsonObject sendToServer = cardToJson(attackCard);
                        try {
                            Client.sendMessage(sendToServer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cardBox.getChildren().remove(activeCard);
                        attack.setDisable(true);
                        attack.setStyle(activeCard.getStyle() + " -fx-opacity: 100%;");
                        waitForMyTurn();
                    } else {
                        // Check that I have cards with same value that are on table
                        Optional<Card> matchingCard = cardsOnTable.stream().filter(card -> card.getValue().equals(attackCard.getValue())).findAny();
                        if (matchingCard.isPresent()) {
                            JsonObject sendToServer = cardToJson(attackCard);
                            try {
                                Client.sendMessage(sendToServer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            cardBox.getChildren().remove(activeCard);
                            attack.setDisable(true);
                            attack.setStyle(activeCard.getStyle() + " -fx-opacity: 100%;");
                            waitForMyTurn();
                        }

                    }
                }
            });
            defence.setOnMouseClicked(mouseEvent -> {
                if (uiIsLocked) {
                    return;
                }
                if (activeCard != null) {
                    defenseCard = cardsInHand.stream().filter(card -> card.getId().equals(activeCard.getId()))
                            .collect(Collectors.toList()).get(0);
                    if (attackCard.getSuit().equals(defenseCard.getSuit())) {
                        if (defenseCard.getValue() > attackCard.getValue()) {
                            JsonObject sendToServer = cardToJson(defenseCard);
                            try {
                                Client.sendMessage(sendToServer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            cardBox.getChildren().remove(activeCard);
                            defence.setDisable(true);
                            defence.setStyle(activeCard.getStyle() + " -fx-opacity: 100%;");
                            waitForMyTurn();
                        } else {
                            activeCard.setStyle(activeCard.getStyle() + " -fx-opacity: 100%; -fx-border-color: null;");
                        }
                        activeCard = null;
                    } else if (defenseCard.getSuit().equals(gameInfo.getTrump().getSuit())) {
                        JsonObject sendToServer = cardToJson(defenseCard);
                        try {
                            Client.sendMessage(sendToServer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cardBox.getChildren().remove(activeCard);
                        defence.setDisable(true);
                        defence.setStyle(activeCard.getStyle() + " -fx-opacity: 100%;");
                        activeCard = null;
                        waitForMyTurn();
                    }
                }
            });
        }
        gameStartService.start();
    }

    private void resetAttackDefButtons() {
        for (HBox attackDefPairs : playFieldButtons.values()) {
            for (int i = 0; i < 2; i++) {
                Button button = (Button) attackDefPairs.getChildrenUnmodifiable().get(i);
                button.setDisable(false);
                button.setStyle("-fx-background-image: null;");
                button.getStylesheets().add(getClass().getResource("/css/misc.css").toExternalForm());
                button.setVisible(false);
            }
        }
    }

    private void showFirstAttackBtn() {
        HBox pair = playFieldButtons.get(1);
        pair.setVisible(true);
        Node attackBtn = pair.getChildrenUnmodifiable().get(0);
        attackBtn.setVisible(true);
    }

    private void waitForMyTurn() {
        if (currentGameState.getFool().equals(playerId) || (currentGameState.getEndTheGame() && cardsInHand.size() == 0)) {
            gameEnd();
        } else {
            uiIsLocked = true;
            if (waitForMyTurnService.getState() == SUCCEEDED) {
                waitForMyTurnService.reset();
            }
            waitForMyTurnService.start();
        }
    }

    private void updateAttackDefButtons(GameInfo game) {
        System.out.println(playerId + " Updating UI with changes");

        List<Card> cardsOnTable = game.getCardsOnTable();
        if (cardsOnTable.isEmpty()) {
            resetAttackDefButtons();
            if (game.getPlayerState(playerId) == PlayerState.ATTACK) {
                showFirstAttackBtn();
            }
        } else {
            int cardCounter = 0;
            int pairCounter = 1;
            for (Card card : cardsOnTable) {
                HBox attackDefPair = playFieldButtons.get(pairCounter);
                attackDefPair.setVisible(true);

                Node node;
                if (cardCounter % 2 == 0) {
                    node = attackDefPair.getChildrenUnmodifiable().get(0);
                } else {
                    node = attackDefPair.getChildrenUnmodifiable().get(1);
                }
                node.setDisable(true);
                node.setVisible(true);
                node.setStyle(card.getStyleForButton() + " -fx-opacity: 100%;");

                cardCounter++;
                if (cardCounter % 2 == 0) {
                    pairCounter++;
                }
            }

            attackCard = cardsOnTable.get(cardsOnTable.size() - 1);

            if (game.getPlayerState(playerId) == PlayerState.ATTACK) {
                HBox attackDefPair = playFieldButtons.get(pairCounter);
                // TODO These if and else if do nothing, but placing 6 cards only works with them
                if (pairCounter < 6) {
                    attackDefPair.setVisible(true);
                    Node attackBtn = attackDefPair.getChildrenUnmodifiable().get(0);
                    attackBtn.setVisible(true);
                } else if (pairCounter == 6) {
                    attackDefPair.setVisible(true);
                    Node attackBtn = attackDefPair.getChildrenUnmodifiable().get(0);
                    attackBtn.setVisible(true);
                }
            } else if (game.getPlayerState(playerId) == PlayerState.DEFENSE) {
                HBox attackDefPair = playFieldButtons.get(pairCounter);
                Node attackBtn = attackDefPair.getChildrenUnmodifiable().get(0);
                Node defButton = attackDefPair.getChildrenUnmodifiable().get(1);
                attackBtn.setVisible(true);
                attackBtn.setDisable(true);

                defButton.setVisible(true);
                defButton.setDisable(false);
            }

        }

    }

    private void doGameAction(GameInfo gameInfo) {
        PlayerState state = gameInfo.getPlayerState(playerId);
//        boolean gameIsGoing = true;

        // Set first gameInfo when game starts
        if (currentGameState == null) {
            currentGameState = gameInfo;
        }
        if (gameInfo.getEndTheGame()) {
            if (gameInfo.getFool().equals(playerId)) {
                endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9);-fx-background-size: cover;" +
                        "-fx-background-image: url('/images/backgrounds/foolEndGame.png')");

//                System.out.println("I AM THE FOOL!");
                //                gameIsGoing = false;
            } else {
                endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9); -fx-background-size: cover;" +
                        "-fx-background-image: url('/images/backgrounds/winnerEndGame.png')");

//                System.out.println("I AM THE WINNER");
//                gameIsGoing = false;
            }
            endGameScreen.setVisible(true);
            gameEnd();
            state = PlayerState.SKIP;
            uiIsLocked = true;
        }

        if (currentGameState.getTurnCounter() != gameInfo.getTurnCounter()) {
            replenishHand();
            if (cardsInHand.size() == 0) {
                Label label = new Label();
                label.setText("I AM THE WINNER");
                label.setTranslateX(100);
                label.setTranslateY(100);
                gameEnd();
                uiIsLocked = true;
            }
//            if (cardsInHand.size() == 0) {
            // TODO this has to end the game

//                endTheGame();
//                gameInfo.endTheGame();
//                uiIsLocked = true;
//
//                System.out.println("LOCK THIS ");
////                goHere = true;
//
////                uiIsLocked = true;
//                // Make the game end
////                stop();
//                Label label = new Label();
//                label.setText("Congratulations, you are the winner!");
//                cardBox.getChildren().add(label);
////                uiIsLocked = true;
//                try {
//                    Client.sendMessage(gameEnd());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                uiIsLocked = true;
//                labe
//                uiIsLocked = true;
//            }
        }


        if (state == PlayerState.ATTACK) {
            stateLabel.setStyle("-fx-background-image: url(/icons/black/swords.png); -fx-background-size: cover;" +
                    "-fx-background-color: rgba(139,69,19,0.5);");
            if (gameInfo.isPlayersTurn(playerId)) {
                // unlock UI to do attack move
                uiIsLocked = false;
                System.out.println(playerId + " I am attacking");
            } else {
                uiIsLocked = true;
                System.out.println(playerId + " I am attacking and waiting for other player turn");
                waitForMyTurn();
                // wait for other player move
            }
        } else if (state == PlayerState.DEFENSE) {
            stateLabel.setStyle("-fx-background-image: url(/icons/black/shield.png); -fx-background-size: cover;" +
                    "-fx-background-color: rgba(139,69,19,0.5);");
            if (gameInfo.isPlayersTurn(playerId)) {
                // unlock UI to do defense move
                uiIsLocked = false;
                System.out.println(playerId + " I am defending");
            } else {
                System.out.println(playerId + "  I am defending and waiting for other player turn");
                uiIsLocked = true;
                waitForMyTurn();
                // wait for other player move
            }
        } else if (state == PlayerState.WAITING) {
            stateLabel.setStyle("-fx-background-image: url(/icons/black/waiting.png); -fx-background-size: cover;" +
                    "-fx-background-color: rgba(139,69,19,0.5);");
            // ui is locked
            System.out.println(playerId + " I am waiting for my turn");
            waitForMyTurn();
            uiIsLocked = true;
        }

        this.currentGameState = gameInfo;
        this.cardsOnTable = gameInfo.getCardsOnTable();
        gameFieldClass.updateVisualPile(currentGameState.getPile().getPile().size());
        gameFieldClass.updateVisualDeck(currentGameState.getDeck().getDeck().size());
        for (String player : currentGameState.getPlayers()) {
            if (!player.equals(playerId)) {
                System.out.println(player);
            }
        }
    }


    public JsonObject gameCardsReplenish(int cardsInHand) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "replenish");
        obj.addProperty("playerId", playerId);
        obj.addProperty("cardsInHand", cardsInHand);
        return obj;
    }

    public void gameEnd() {
        JsonObject endGame = new JsonObject();
        endGame.addProperty("type", "endGame");
        endGame.addProperty("Size", cardsInHand.size());
        endGame.addProperty("playerId", playerId);
        String response;
        try {
            response = Client.sendMessage(endGame);
//            String resp = response.toString();
            if (response.contains("The game has finished")) {
                if (cardsInHand.size() == 0 ) {
                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9); -fx-background-size: cover;" +
                            "-fx-background-image: url('/images/backgrounds/winnerEndGame.png')");
                    endGameScreen.setVisible(true);
                    //gameEnd();
                    //state = PlayerState.SKIP;
                    uiIsLocked = true;
                } else {
                    endGameScreen.setStyle("-fx-background-color: rgba(16,16,16,0.9);-fx-background-size: cover;" +
                            "-fx-background-image: url('/images/backgrounds/foolEndGame.png')");
                    endGameScreen.setVisible(true);

                    //gameEnd();
                    //state = PlayerState.SKIP;
                    uiIsLocked = true;
                }

           }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return endGame;
//            if (cardsInHand.size() == 0 ) {
//                System.out.println("winner");
//            } else {
//                System.out.println("loser");
//            }
//        }
    }

//    public void endTheGame() {
//        Label label = new Label();
//        if (cardsInHand.size() == 0) {
////            Label label = new Label();
//            label.setText("Congratulations, you are the winner!");
//        } else {
//            label.setText("Sadly, you have lost, good luck next time! ");
//        }
//        cardBox.getChildren().add(label);
//        Server.playersToGames.remove(playerId);
//    }

    public void replenishHand() {
        double cardHeight = cardBoxScroll.getMaxHeight() * 0.95;
        double cardWidth = cardHeight / 90 * 59;
        JsonObject obj = gameCardsReplenish(cardBox.getChildren().size());
        String response;
        try {
            response = Client.sendMessage(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Card> cards = gson.fromJson(response, new TypeToken<List<Card>>() {
        }.getType());

        for (Card card : cards) {
            cardsInHand.add(card);
            cardToButton(card, cardBox, cardWidth, cardHeight);
        }
    }

    public JsonObject cardToJson(Card card) {
        JsonObject obj = new JsonObject();
        obj.addProperty("playerId", playerId);
        obj.addProperty("type", "gameMove");
        obj.add("card", gson.toJsonTree(card));
        return obj;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
