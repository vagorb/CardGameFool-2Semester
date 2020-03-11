import java.util.List;

public class GameStart {
    public static void main(String[] args) {
        Hand hand = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        Hand hand4 = new Hand();
        Player player = new Player("Tvorog16", 0, hand);
        Player player2 = new Player("Tvorog17", 0, hand2);
        Player player3 = new Player("Tvorog18", 0, hand3);
        Player player4 = new Player("Tvorog19", 0, hand4);
        Table table2 = new Table(player, player2);
        List<Card> variables = table2.getGameDeck();
        List<Player>  variable = table2.getPlayers();
//        System.out.println(variable);
//        System.out.println(variables);
//        List<Card> pile = table2.getPile();
        Card card = new Card("Hearts", 5);
        table2.addToPile(card);
        List<Card> pile = table2.getPile();
        System.out.println(pile);
        System.out.println(table2.getGameDeck());
//        Table table3 = new Table(player, player2, player3);
//        Table table4 = new Table(player, player2, player3, player4);
//        Pile pile = new Pile();


    }

    }
