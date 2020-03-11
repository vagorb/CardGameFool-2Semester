import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table {
//Table(cards on the table) defence and attack . - Implemented
//when card get on table, they removed from hand of player. - Implemented
//comparing cards( can kill or not attack card) - Nope
//put card on table from hand for attacking or defence. the card must be removed from hand. - Implemented


    public Deck deckForThisGame;
    private List<Player> listOfPlayers = new ArrayList<>();
    private List<Card> pileOfCardsForThisGame;
    private List<Card> table = new ArrayList<>();
    private boolean currentDefenseState;
    // This is a default value i guess(atleast for coding purposes)
//    private List<Card> attackAndDefenseCards = new ArrayList<>();

    public Table(Player player1, Player player2) {
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        Pile pile = new Pile();
        pileOfCardsForThisGame = pile.createPile();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Player player2, Player player3) {
        //deckForThisGame = Deck.createDeck();
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        Pile pile = new Pile();
        pileOfCardsForThisGame = pile.createPile();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Player player2, Player player3, Player player4) {
        //deckForThisGame = Deck.createDeck();
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        Pile pile = new Pile();
        pileOfCardsForThisGame = pile.createPile();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        listOfPlayers.add(player4);
        System.out.println(deckForThisGame);
    }

    public List<Player> getPlayers() {
        return listOfPlayers;
    }

    public ArrayList<Card> getGameDeck() {
        return deckForThisGame.getDeck();
    }

    public List<Card> getPile() {
        return pileOfCardsForThisGame;
    }

    public void addToPile(Card card) {
        deckForThisGame.removeCard(card);
        pileOfCardsForThisGame.add(card);
    }


    public void putAttackOrDefenseCards(Player player , Card card) {
        table.add(player.getHand().putCardOnTable(card));
        player.getHand().removeCard(card);
    }


    public void addAttackAndDefenseCardsToPileOrPlayer(Player player) {
        if (currentDefenseState) {
            pileOfCardsForThisGame.addAll(table);
        } else {
            for (Card card : table) {
                player.getHand().addCard(card);
            }
        }
        table.removeAll(table);
    }

    public void compareCards() {
        int tableSize = table.size() - 1;
        if (table.get(tableSize).value > table.get(tableSize - 1).value) {
            currentDefenseState = true;
        } else {
            currentDefenseState = false;
        }
    }



}
