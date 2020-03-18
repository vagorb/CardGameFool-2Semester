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
