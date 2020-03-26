import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameStart {
    private Table playingTable;
//    private Deck playingDeck;
//    private List<Player> playingPlayers = new ArrayList<>();
    private List<Card> trumpCard = new ArrayList<>();
//    private boolean gameIsBeingPlayed = true;

    public static void main(String[] args) {
        // This is a rough idea that could make sense.
        boolean firstOffense = true;
        Card cardThatDecidesTrump = null;
        boolean trumpChosen = false;
        boolean gameIsBeingPlayed = true;
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your name : ");
        String s = input.next();
        Hand hand1 = new Hand();
        Player player1 = new Player(s, 0, hand1);

        System.out.println("Please enter your name : ");
        String x = input.next();
        Hand hand2 = new Hand();
        Player player2 = new Player(x, 0, hand2);

        Table table = new Table(player1, player2);
        TurnCalculation turnCalculation = new TurnCalculation(table);

        while (gameIsBeingPlayed)  {
            if (!trumpChosen) {
                Random generator = new Random();

                // The code here represents the idea that this card from deck is not a part of the deck ( I GUESS ? )
                // because it is being shown face up as the bottom card of the deck.
                cardThatDecidesTrump = table.getGameDeck().get(generator.nextInt(36));
                table.getGameDeck().remove(cardThatDecidesTrump);


//                String suit = table.getGameDeck().get(generator.nextInt(37)).getSuit();
                String trumpSuit = cardThatDecidesTrump.getSuit();
                for (Card card : table.getGameDeck()) {
                    if (card.getSuit().equals(trumpSuit)) {
                        card.setTrump(true);
                    }
                }
                trumpChosen = true;
            }
            while (player1.getHand().getCardsInHand().size() < 6 && player2.getHand().getCardsInHand().size() < 6) {
                Card cardForPlayer1 = table.getGameDeck().get(table.getGameDeck().size() - 1);
                table.getPlayers().get(0).getHand().addCard(cardForPlayer1);
                table.getGameDeck().remove(cardForPlayer1);
                Card cardForPlayer2 = table.getGameDeck().get(table.getGameDeck().size() - 1);
                table.getPlayers().get(1).getHand().addCard(cardForPlayer2);
                table.getGameDeck().remove(cardForPlayer2);
            }
            // Because of what i added a bit earlier we need to check for the existence of cardThatDecidesTrump because it goes towards the deck size
            while (table.getGameDeck().size() > 0
                    && (player1.getHand().getCardsInHand().size() > 0 && player2.getHand().getCardsInHand().size() > 0)) {
                while (firstOffense) {
                    player1.setPlayerState(Player.PlayerState.ATTACK);
                    player2.setPlayerState(Player.PlayerState.DEFENSE);
                    for (Player attacker : table.getPlayers()) {
                        if (attacker.getPlayerState() == Player.PlayerState.ATTACK) {
                            // for testing purpose i will try to add just basic random i guess?? no clue how to test otherwise right now
                            Random generator = new Random();
                            turnCalculation.putAttackCard(attacker, attacker.getHand().getCardsInHand().get(generator.nextInt(6)));

                        }
                    }
                }
            }
        }
    }
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
