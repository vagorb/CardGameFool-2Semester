package com.card.game.fool.logic;

import com.card.game.fool.cards.Card;
import com.card.game.fool.players.Hand;
import com.card.game.fool.players.Player;
import com.card.game.fool.tables.Table;

import java.util.Random;
import java.util.Scanner;

public class GameController {

    // CODE FOR CHECKING IF THE TRUMP CARD ( BOTTOM CARD IN THE DECK ( Which is not actually part of the deck) ) was taken
    // Currently i am not accounting for that card at all.

    private boolean turnEnded;
    private boolean gameIsBeingPlayed;
    private TurnCalculation turnCalculation;
    private Table table;
    private Card cardThatDecidesTrump;
    private int attackDefenseDecider;
    private Player player1;
    private Player player2;
    private Scanner input;


    /**
     * Getter
     * @return table object
     */
    public Table getTable() {
        return table;
    }

    /**
     * Getter
     * @return card that decides trump suit for this table
     */
    public Card getCardThatDecidesTrump() {
        return cardThatDecidesTrump;
    }

    /**
     * Null value
     * @param cardThatIsNull null value that will set our card to null ( It means the trump card was taken from by one of the players )
     */
    public void setCardThatDecidesTrump(Card cardThatIsNull) {
        cardThatDecidesTrump = cardThatIsNull;
    }


    /**
     * Method creates all required classes for the game to run for 2 players
     */
    public void createBasic() {
        turnEnded = false;
        gameIsBeingPlayed = true;
        input = new Scanner(System.in);
        System.out.println("Please enter your name : ");
        String s = input.next();
        Hand hand1 = new Hand();
        Player player1 = new Player(s, 0, hand1);
        this.player1 = player1;

        System.out.println("Please enter your name : ");
        String x = input.next();
        Hand hand2 = new Hand();
        Player player2 = new Player(x, 0, hand2);
        this.player2 = player2;

        Table table = new Table(player1, player2);
        this.table = table;
        this.turnCalculation = new TurnCalculation(table);
    }


    /**
     * Method (randomly) decides what card suit will be a trump card suit
     * Method fills hands of 2 players with cards
     * @param table com.card.game.fool.tables.Table on which this exact MATCH ( Game ) is happening
     */
    public void assignTrumpAndFillHands(Table table) {
        Random generator = new Random();
        cardThatDecidesTrump = table.getGameDeck().get(generator.nextInt(36));
        table.setTrumpSuit(cardThatDecidesTrump);
        table.getGameDeck().remove(cardThatDecidesTrump);
        String trumpSuit = cardThatDecidesTrump.getSuit();
        for (Card card : table.getGameDeck()) {
            if (card.getSuit().equals(trumpSuit)) {
                card.setTrump(true);
            }
        }
        for (int i = 0; i < 6; i++) {
            Card cardForPlayer1 = table.getGameDeck().get(table.getGameDeck().size() - 1);
            table.getPlayers().get(0).getHand().addCard(cardForPlayer1);
            table.getGameDeck().remove(cardForPlayer1);
            Card cardForPlayer2 = table.getGameDeck().get(table.getGameDeck().size() - 1);
            table.getPlayers().get(1).getHand().addCard(cardForPlayer2);
            table.getGameDeck().remove(cardForPlayer2);
        }
        attackDefenseDecider = 0;
    }


    /**
     * This method sets player states to attack or defense state.
     *
     */
    public void setPlayerStates() {
        turnEnded = false;
        System.out.println(table.getGameDeck().size());
        System.out.println(table.getTrumpSuit());
        // I can add this if statement in my main code to prevent this from going weird.
        if (attackDefenseDecider % 2 == 0) {
            player1.setPlayerState(Player.PlayerState.ATTACK);
            player2.setPlayerState(Player.PlayerState.DEFENSE);
        } else if (attackDefenseDecider % 2 == 1) {
            player1.setPlayerState(Player.PlayerState.DEFENSE);
            player2.setPlayerState(Player.PlayerState.ATTACK);
        }
    }


    /**
     * This method makes the attack/defense turn happen
     * The method is stopped when attacker decides to skip on attacking
     * The method is stopped when defender decides to take cards that he is being attacked with
     */
    // The method SHOULD be stopped when attacker puts 6 cards for attack (because maximum of attacking cards in 1 game turn is 6 cards) Should work now
    public void attackDefenseTurn() {
        for (int i = 0; i < 12; i++) {
            if (!turnCalculation.getDefenseSuccess()) {
                for (Player defender : table.getPlayers()) {
                    if (defender.getPlayerState() == Player.PlayerState.DEFENSE) {
                        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(defender);
                        System.out.println(defender.getHand().getCardsInHand());
                        turnCalculation.setDefenseSuccess(true);
                    }
                }
                break;
            } else if (turnEnded) {
                turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player1);
                System.out.println(player1.getHand().getCardsInHand());
                System.out.println(turnCalculation.getPlayingTable().getPile().getPile());
                break;
            }
            for (Player playerAttackerOrDefender : table.getPlayers()) {
                if (playerAttackerOrDefender.getHand().getCardsInHand().size() == 0) {
                    break;
                }
                if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.ATTACK) {
                    System.out.println("Choose card for attack from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
                    System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
                    String attackCard = input.next();
                    if (attackCard.equals("Skip")) {
                        attackDefenseDecider += 1;
                        turnEnded = true;
                        break;
                    }
                    Integer integer = Integer.valueOf(attackCard);
                    System.out.println("We will attack with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
                    turnCalculation.putAttackCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
                }
                if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.DEFENSE && table.getTable().size() % 2 == 1) {
                    System.out.println("Choose card for defense from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
                    System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
                    String defenseCard = input.next();
                    if (defenseCard.equals("Take")) {
                        turnEnded = true;
                        turnCalculation.setDefenseSuccess(false);
                        break;
                    }
                    Integer integer = Integer.valueOf(defenseCard);
                    System.out.println("We will defend with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
                    turnCalculation.putDefenseCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
                    while (!turnCalculation.getDefenseSuccess()) {
                        System.out.println("You can't defend with this card. Please put another one, or write ( press ) Take");
                        String newDefenseCard = input.next();
                        if (newDefenseCard.equals("Take")) {
                            turnEnded = true;
                            turnCalculation.setDefenseSuccess(false);
                            break;
                        }
                        else {
                            Integer integer1 = Integer.valueOf(newDefenseCard);
                            System.out.println("This time we will try to defend with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer1));
                            turnCalculation.putDefenseCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer1));
                        }
                    }
                }
            }
        }
    }


    /**
     * This methods refills player hands with cards up to 6 cards (if player has more then 6, then he does not get any) from the deck
     */
    public void refillCards() {
        for (int i = 0; i < 6; i++) {
            if (table.getGameDeck().size() == 0) {
                if (cardThatDecidesTrump != null) {
                    Card cardToAdd = cardThatDecidesTrump;
                    if (player1.getHand().getCardsInHand().size() < 6) {
                        player1.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    } else if (player2.getHand().getCardsInHand().size() < 6) {
                        player2.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    }
                }
                System.out.println("com.card.game.fool.cards.Deck is empty, you will not get 6 cards refilled");
                break;
            }
            if (player1.getHand().getCardsInHand().size() < 6) {
                Card cardToAdd = table.getGameDeck().get(table.getGameDeck().size() - 1);
                player1.getHand().addCard(cardToAdd);
                table.getGameDeck().remove(cardToAdd);
                System.out.println("Getting our number of cards back to 6 for player 1");
            }
            if (table.getGameDeck().size() == 0) {
                if (cardThatDecidesTrump != null) {
                    Card cardToAdd = cardThatDecidesTrump;
                    if (player1.getHand().getCardsInHand().size() < 6) {
                        player1.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    } else if (player2.getHand().getCardsInHand().size() < 6) {
                        player2.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    }
                }
                System.out.println("com.card.game.fool.cards.Deck is empty, you will not get 6 cards refilled");
                break;
            }
            if (player2.getHand().getCardsInHand().size() < 6) {
                Card cardToAdd = table.getGameDeck().get(table.getGameDeck().size() - 1);
                player2.getHand().addCard(cardToAdd);
                table.getGameDeck().remove(cardToAdd);
                System.out.println("Getting our number of cards back to 6 for player 2");
            }
            if (table.getGameDeck().size() == 0) {
                if (cardThatDecidesTrump != null) {
                    Card cardToAdd = cardThatDecidesTrump;
                    if (player1.getHand().getCardsInHand().size() < 6) {
                        player1.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    } else if (player2.getHand().getCardsInHand().size() < 6) {
                        player2.getHand().addCard(cardToAdd);
                        setCardThatDecidesTrump(null);
                    }
                }
                System.out.println("com.card.game.fool.cards.Deck is empty, you will not get 6 cards refilled");
                break;
            }
            if (player1.getHand().getCardsInHand().size() >= 6 && player2.getHand().getCardsInHand().size() >= 6) {
                System.out.println("Required amount of cards was reached");
                break;
            }
        }
    }

    /**
     * After deck has no cards in it (0 cards) . Instead of refilling player hands, we check for them being of size 0
     * If ( in 2 player game ) player hand is 0 , then return false and finish the while loop ( end the game ).
     * @return returns boolean false for ending the cycle, or true for continuing it.
     */
    public boolean endGame() {
        if (player1.getHand().getCardsInHand().size() == 0 || player2.getHand().getCardsInHand().size() == 0) {
            // Stops the cycle
            return false;
        }
        return true;
    }


    /**
     * Some text print outs to show that the game reached it's end.
     * Announces the match winner
     */
    public void ending() {
        if (player1.getHand().getCardsInHand().size() == 0) {
            System.out.println("Congratulations player1 " + player1.getName() + " you won against player2 who had " + player2.getHand().getCardsInHand().size() + "cards in hand left");
        } else {
            System.out.println("Congratulations player2 " + player2.getName() + " you won against player1 who had " + player1.getHand().getCardsInHand().size() + "cards in hand left");
        }
        System.out.println("Wish to return back to main menu?");
    }
}
