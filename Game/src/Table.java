import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table {
//Table(cards on the table) defence attack
//when card get on table, they removed from hand of player.
//comparing cards( can kill or not attack card)
//put card on table from hand for attacking or defence. the card must be removed from hand.


    private List<Card> deckForThisGame;
    private List<Player> listOfPlayers = new ArrayList<>();
    private List<Card> pileOfCardsForThisGame;

    public Table(Player player1, Player player2) {
        deckForThisGame = Deck.createDeck();
        Pile pile = new Pile();
        pileOfCardsForThisGame = pile.createPile();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Player player2, Player player3) {
        deckForThisGame = Deck.createDeck();
        Pile pile = new Pile();
        pileOfCardsForThisGame = pile.createPile();
        listOfPlayers.add(player1);
        listOfPlayers.add(player2);
        listOfPlayers.add(player3);
        System.out.println(deckForThisGame);
    }

    public Table(Player player1, Player player2, Player player3, Player player4) {
        deckForThisGame = Deck.createDeck();
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

    public List<Card> getGameDeck() {
        return deckForThisGame;
    }

    public List<Card> getPile() {
        return pileOfCardsForThisGame;
    }

    public void addToPile(Card card) {
        pileOfCardsForThisGame.add(card);
        deckForThisGame.remove(card);
    }
}






//     not sure about this either right now
//    private Set<Card> defenseAndAttackCards = new HashSet<>();
//     Okey i am retarded
//    private Set<Card> pileOfCards = new HashSet<>();
//     We should probably have a Player class after all
//    private Set<Player> playersAtThisTable = new HashSet<>();
//     Table class description
//     Class that holds information about everything on the table
//     Deck info
//     Cards that are put against the player
//     Cards we defend with
//     Pile ( cards that were discarded (Successful defense against attacking player(es)
//     Info about players sitting(playing) at this table ( NOT SURE IF THIS CLASS SHOULD DO IT)
//
//     We should probably use table as a getter/setter for info
//
//    public List<Card> getPileOfCards() {
//        return Pile.getPileOfCards;
//    }

//
//    public Set<Players> getPlayersAtThisTable() {
//        return playersAtThisTable;
//    }
//
//    public void setPlayersAtThisTable(Player PlayerName) {
//        playersAtThisTable.add(PlayerName);
//    }



//
//    public void addGameDeckToTable() {
//
//        deckForThisGame.add(Deck.createDeck())
//    }
//
//}
