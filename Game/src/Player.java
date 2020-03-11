public class Player {
    Hand hand;
    String name;
    Integer score;

    public Player(String name, Integer score, Hand hand) {
        this.name = name;
        this.score = score;
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public void addScore(int score) {
        if (this.score + score > 0) {
            this.score += score;
        } else {
            this.score = 0;
        }
    }




}
