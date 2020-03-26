import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameStart {
    private Table playingTable;
//    private Deck playingDeck;
//    private List<Player> playingPlayers = new ArrayList<>();
    private List<Card> trumpCard = new ArrayList<>();

    public static void main(String[] args) {
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
        // All this happens continuously untill deck size becomes 0
        // When it becomes zero any player who has 0 cards in Hand finishes the game as a (partial) winner
        // Last player in the game with cards is declared a loser ( FOOL )
        // When loser is declared someStatement == false
        // Program ( game ) ends
//    }



//    public void putAttackOrDefenseCards(Player player , Card card) {
//        table.add(player.getHand().putCardOnTable(card));
//        player.getHand().removeCard(card);
//    }
//
//
//    public void addAttackAndDefenseCardsToPileOrPlayer(Player player) {
//        if (currentDefenseState) {
//            pileOfCardsForThisGame.addAll(table);
//        } else {
//            for (Card card : table) {
//                player.getHand().addCard(card);
//            }
//        }
//        table.removeAll(table);
//    }
//
//    public void compareCards() {
//        int tableSize = table.size() - 1;
//        if (table.get(tableSize).value > table.get(tableSize - 1).value) {
//            currentDefenseState = true;
//        } else {
//            currentDefenseState = false;
//        }
//    }

}
