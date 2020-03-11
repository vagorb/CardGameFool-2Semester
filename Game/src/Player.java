public class Player {
    private Hand hand;
    private String name;
    private Integer score;
    private PlayerState playerState;

    public Player(String name, Integer score, Hand hand) {
        this.name = name;
        this.score = score;
        this.hand = hand;
    }

    public enum PlayerState {
        ATTACK, DEFENSE
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

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }




}
