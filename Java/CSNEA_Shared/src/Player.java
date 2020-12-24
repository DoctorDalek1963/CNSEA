public class Player {

    private final String name;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int number) {
        score = number;
    }

    public void incrementScore(int value) {
        score += value;
    }
}
