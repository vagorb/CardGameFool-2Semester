import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {
//Table(cards on the table) defence and attack . - Implemented
//when card get on table, they removed from hand of player. - Implemented
//comparing cards( can kill or not attack card) - Nope
//put card on table from hand for attacking or defence. the card must be removed from hand. - Implemented


    public Deck deckForThisGame;
    private List<Player> listOfPlayers = new ArrayList<>();
    private List<Card> pileOfCardsForThisGame = new ArrayList<>();
    Pile pile;
    AI ai;
    List<Card> table = new ArrayList<>();
    private boolean currentDefenseState;
    private String trumpSuit;
    // This is a default value i guess(atleast for coding purposes)
//    private List<Card> attackAndDefenseCards = new ArrayList<>();

    public Table(Player player1, Player player2) {
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        Pile pile = new Pile(pileOfCardsForThisGame);
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Player player2, Player player3) {
        //deckForThisGame = Deck.createDeck();
        this.table = new ArrayList<>();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();
        Pile pile = new Pile(pileOfCardsForThisGame);
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
        Pile pile = new Pile(pileOfCardsForThisGame);

        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        listOfPlayers.add(player4);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, AI ai) {
        this.table = new ArrayList<>();
        this.ai = ai;
        this.pile = new Pile(pileOfCardsForThisGame);
        //pile.createPile();
        this.deckForThisGame = new Deck();
        this.deckForThisGame.shuffleDeck();

        listOfPlayers.add(player1);
    }

    public List<Player> getPlayers() {
        return listOfPlayers;
    }

    public ArrayList<Card> getGameDeck() {
        return deckForThisGame.getDeck();
    }

    public Pile getPile() {
        return pile;
    }

    public void addToPile(Card card) {
        //deckForThisGame.removeCard(card);
        pile.addDiscardedCards(card);
    }

    public void setTrumpSuit(String trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public void addCardOnTable(Card card) {
        table.add(card);
    }

    public Card getLastCardPutOnTable() {
        int lastCardOnTable = table.size() - 1;
        return table.get(lastCardOnTable);
    }

    public List<Card> getTable() {
        return table;
    }

    public void assignTrumpCard() {
        // This method should be changed, currently serves a purpose of assigning a trump card ( not the way it should be in final game)
        trumpSuit = deckForThisGame.getDeck().get(0).getSuit();
    }


    public String getTrumpSuit() {
        return trumpSuit;
    }


    public Card getLastCardOnTable() {
        return table.get(table.size() - 1);
    }

    public Map<Integer, List<Card>> mapOfCardsInTable() {
        return getTable().stream().collect(Collectors.groupingBy(Card::getValue));
    }


//
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
