public class Score {

    private final String name;
    private final int number;

    Score(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String displayName() {
        return name + " - " + number;
    }
}
