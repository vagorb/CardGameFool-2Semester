import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameStart {
//    private Table playingTable;
//    private List<Card> trumpCard = new ArrayList<>();


    /**
     * Our main method that runs all the required classes and methods to play a game of player vs player
     * @param args main method arguments
     */
    public static void main(String[] args) {
//        boolean turnEnded = false;
//        // This is a rough idea that could make sense.
//        Card cardThatDecidesTrump;
//        boolean gameIsBeingPlayed = true;
//        Scanner input = new Scanner(System.in);
//        System.out.println("Please enter your name : ");
//        String s = input.next();
//        Hand hand1 = new Hand();
//        Player player1 = new Player(s, 0, hand1);
//
//        System.out.println("Please enter your name : ");
//        String x = input.next();
//        Hand hand2 = new Hand();
//        Player player2 = new Player(x, 0, hand2);
//
//        Table table = new Table(player1, player2);
//        TurnCalculation turnCalculation = new TurnCalculation(table);
//        Random generator = new Random();
//
//        // The code here represents the idea that this card from deck is not a part of the deck ( I GUESS ? )
//        // because it is being shown face up as the bottom card of the deck.
//        cardThatDecidesTrump = table.getGameDeck().get(generator.nextInt(36));
//        table.getGameDeck().remove(cardThatDecidesTrump);
//
//
////       String suit = table.getGameDeck().get(generator.nextInt(37)).getSuit();
//        String trumpSuit = cardThatDecidesTrump.getSuit();
//        for (Card card : table.getGameDeck()) {
//            if (card.getSuit().equals(trumpSuit)) {
//                card.setTrump(true);
//            }
//        }
//
//        for (int i = 0; i < 6; i++) {
//            Card cardForPlayer1 = table.getGameDeck().get(table.getGameDeck().size() - 1);
//            table.getPlayers().get(0).getHand().addCard(cardForPlayer1);
//            table.getGameDeck().remove(cardForPlayer1);
//            Card cardForPlayer2 = table.getGameDeck().get(table.getGameDeck().size() - 1);
//            table.getPlayers().get(1).getHand().addCard(cardForPlayer2);
//            table.getGameDeck().remove(cardForPlayer2);
//            }
//        int attackDefenseDecider = 0;
//        while (gameIsBeingPlayed) {
//            turnEnded = false;
//            System.out.println(table.getGameDeck().size());
//            System.out.println(cardThatDecidesTrump.getSuit());
//            if (table.getGameDeck().size() > 0) {
//                if (attackDefenseDecider % 2 == 0) {
//                    player1.setPlayerState(Player.PlayerState.ATTACK);
//                    player2.setPlayerState(Player.PlayerState.DEFENSE);
//                } else if (attackDefenseDecider % 2 == 1) {
//                    player1.setPlayerState(Player.PlayerState.DEFENSE);
//                    player2.setPlayerState(Player.PlayerState.ATTACK);
//                }
//                for (int i = 0; i < 12; i++) {
//                    if (!turnCalculation.getDefenseSuccess()) {
//                        for (Player defender : table.getPlayers()) {
//                            if (defender.getPlayerState() == Player.PlayerState.DEFENSE) {
//                                turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(defender);
//                                System.out.println(defender.getHand().getCardsInHand());
//                            }
//                        }
//                        break;
//                    } else if (turnEnded) {
//                        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player1);
//                        System.out.println(player1.getHand().getCardsInHand());
//                        System.out.println(turnCalculation.getPlayingTable().getPile());
//                        break;
//                    }
//                    for (Player playerAttackerOrDefender : table.getPlayers()) {
//                        if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.ATTACK) {
//                            System.out.println("Choose card for attack from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
//                            System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
//                            String attackCard = input.next();
//                            if (attackCard.equals("Skip")) {
//                                attackDefenseDecider += 1;
//                                turnEnded = true;
//                                break;
//                            }
//                            Integer integer = Integer.valueOf(attackCard);
//                            System.out.println("We will attack with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
//                            turnCalculation.putAttackCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
////                        System.out.println("We are attacking with " + playerAttacker.getHand().getCardsInHand().get(integer));
//                        }
//                        if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.DEFENSE && table.getTable().size() % 2 == 1) {
//                            System.out.println("Choose card for defense from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
//                            System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
//                            String defenseCard = input.next();
//                            if (defenseCard.equals("Take")) {
//                                turnEnded = true;
//                                break;
//                            }
//                            Integer integer = Integer.valueOf(defenseCard);
//                            System.out.println("We will defend with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
//                            turnCalculation.putDefenseCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
//                        }
////                        if (!turnCalculation.getDefenseSuccess()) {
////
////                        }
//                    }
//                }
//                for (int i = 0; i < 6; i++) {
//                    if (player1.getHand().getCardsInHand().size() < 6) {
//                        Card cardToAdd = table.getGameDeck().get(table.getGameDeck().size() - 1);
//                        player1.getHand().addCard(cardToAdd);
//                        table.getGameDeck().remove(cardToAdd);
//                        System.out.println("Getting our number of cards back to 6 for player 1");
//                    }
//                    if (player2.getHand().getCardsInHand().size() < 6) {
//                        Card cardToAdd = table.getGameDeck().get(table.getGameDeck().size() - 1);
//                        player2.getHand().addCard(cardToAdd);
//                        table.getGameDeck().remove(cardToAdd);
//                        System.out.println("Getting our number of cards back to 6 for player 2");
//                    }
//                    if (player1.getHand().getCardsInHand().size() >= 6 && player2.getHand().getCardsInHand().size() >= 6) {
//                        System.out.println("Required amount of cards was reached");
//                        break;
//                    }
//                }
//
//            } else {
//                // empty deck
//                if (attackDefenseDecider % 2 == 0) {
//                    player1.setPlayerState(Player.PlayerState.ATTACK);
//                    player2.setPlayerState(Player.PlayerState.DEFENSE);
//                } else if (attackDefenseDecider % 2 == 1) {
//                    player1.setPlayerState(Player.PlayerState.DEFENSE);
//                    player2.setPlayerState(Player.PlayerState.ATTACK);
//                }
//                for (int i = 0; i < 12; i++) {
//                    if (!turnCalculation.getDefenseSuccess()) {
//                        for (Player defender : table.getPlayers()) {
//                            if (defender.getPlayerState() == Player.PlayerState.DEFENSE) {
//                                turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(defender);
//                                System.out.println(defender.getHand().getCardsInHand());
//                            }
//                        }
//                        break;
//                    } else if (turnEnded) {
//                        turnCalculation.addAttackAndDefenseCardsToPileOrPlayer(player1);
//                        System.out.println(player1.getHand().getCardsInHand());
//                        System.out.println(turnCalculation.getPlayingTable().getPile());
//                        break;
//                    }
//                    for (Player playerAttackerOrDefender : table.getPlayers()) {
//                        if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.ATTACK) {
//                            System.out.println("Choose card for attack from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
//                            System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
//                            String attackCard = input.next();
//                            if (attackCard.equals("Skip")) {
//                                attackDefenseDecider += 1;
//                                turnEnded = true;
//                                break;
//                            }
//                            Integer integer = Integer.valueOf(attackCard);
//                            System.out.println("We will attack with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
//                            turnCalculation.putAttackCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
////                        System.out.println("We are attacking with " + playerAttacker.getHand().getCardsInHand().get(integer));
//                        }
//                        if (playerAttackerOrDefender.getPlayerState() == Player.PlayerState.DEFENSE && table.getTable().size() % 2 == 1) {
//                            System.out.println("Choose card for defense from your hand of size " + (playerAttackerOrDefender.getHand().getCardsInHand().size() - 1));
//                            System.out.println(playerAttackerOrDefender.getHand().getCardsInHand());
//                            String defenseCard = input.next();
//                            if (defenseCard.equals("Take")) {
//                                turnEnded = true;
//                                break;
//                            }
//                            Integer integer = Integer.valueOf(defenseCard);
//                            System.out.println("We will defend with " + playerAttackerOrDefender.getHand().getCardsInHand().get(integer));
//                            turnCalculation.putDefenseCard(playerAttackerOrDefender, playerAttackerOrDefender.chooseCard(integer));
//                        }
//                    }
//                }
//                if (player1.getHand().getCardsInHand().size() == 0 || player2.getHand().getCardsInHand().size() == 0) {
//                    gameIsBeingPlayed = false;
//                }
//            }
//
//
//            }
//        if (player1.getHand().getCardsInHand().size() == 0) {
//            System.out.println("Congratulations player1 " + player1.getName() + " you won against player2 who had " + player2.getHand().getCardsInHand().size() + "cards in hand left");
//        } else {
//            System.out.println("Congratulations player2 " + player2.getName() + " you won against player1 who had " + player1.getHand().getCardsInHand().size() + "cards in hand left");
//        }
//        // Random question text lol
//        System.out.println("Wish to return back to main menu?");
        boolean gameIsBeingPlayed = true;
        GameController codeIsHere = new GameController();
        codeIsHere.createBasic();
        codeIsHere.assignTrumpAndFillHands(codeIsHere.getTable());
        while (gameIsBeingPlayed) {
            if (codeIsHere.getTable().getGameDeck().size() >= 0 && codeIsHere.getCardThatDecidesTrump() != null) {
                codeIsHere.setPlayerStates();
                codeIsHere.attackDefenseTurn();
                codeIsHere.refillCards();
            } else {
                codeIsHere.setPlayerStates();
                codeIsHere.attackDefenseTurn();
                gameIsBeingPlayed = codeIsHere.endGame();
            }
        }
        codeIsHere.ending();
    }


//        while (gameIsBeingPlayed)  {
//            while (player1.getHand().getCardsInHand().size() < 6 && player2.getHand().getCardsInHand().size() < 6) {
//                Card cardForPlayer1 = table.getGameDeck().get(table.getGameDeck().size() - 1);
//                table.getPlayers().get(0).getHand().addCard(cardForPlayer1);
//                table.getGameDeck().remove(cardForPlayer1);
//                Card cardForPlayer2 = table.getGameDeck().get(table.getGameDeck().size() - 1);
//                table.getPlayers().get(1).getHand().addCard(cardForPlayer2);
//                table.getGameDeck().remove(cardForPlayer2);
//            }
//            // Because of what i added a bit earlier we need to check for the existence of cardThatDecidesTrump because it goes towards the deck size
//            while (table.getGameDeck().size() > 0
//                    && (player1.getHand().getCardsInHand().size() > 0 && player2.getHand().getCardsInHand().size() > 0)) {
//                while (firstOffense) {
//                    player1.setPlayerState(Player.PlayerState.ATTACK);
//                    player2.setPlayerState(Player.PlayerState.DEFENSE);
//                    for (Player attacker : table.getPlayers()) {
//                        if (attacker.getPlayerState() == Player.PlayerState.ATTACK) {
//                            // for testing purpose i will try to add just basic random i guess?? no clue how to test otherwise right now
////                            Random generator = new Random();
////                            turnCalculation.putAttackCard(attacker, attacker.getHand().getCardsInHand().get(generator.nextInt(6)));
//                            System.out.println();
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//    while (someStatement == true) {
    // Decide a trump card, give every player in the game (in this exact table) 6 cards from Deck to they're Hand
    // Decide by some logic who starts first ( makes the first attack )
    // Put attack card / defense card. Repeat if possible
    // Ask other players ( if table size > 2 ) if they want to add extra attack cards to defend against
    // Decide if cards go to defending player Hand(Lost defense) or to pile ( Successful defense)
    // Replenish player Hand ( up to 6 cards) from deck
    // If defense is successful next player (Defending player becomes the attacker) and the next player clock wise becomes the defender
    // If defense is unsuccessful skip the player that was defending and make another player Attacker ( and the next one a defender )
    //
    //
    // All this happens continuously until deck size becomes 0
    // When it becomes zero any player who has 0 cards in Hand finishes the game as a (partial) winner
    // Last player in the game with cards is declared a loser ( FOOL )
    // When loser is declared someStatement == false
    // Program ( game ) ends
//    }

}
