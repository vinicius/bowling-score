package challenge.jobsity.model;

public class Frame {
    private int number;
    private String roll1;
    private String roll2;
    private String rollExtra;
    private int partialScore;

    public Frame(int number, String roll1, int partialScore) {
        this(number, roll1, null, null, partialScore);
    }

    public Frame(int number, String roll1, String roll2, int partialScore) {
        this(number, roll1, roll2, null, partialScore);
    }

    public Frame(int number, String roll1, String roll2, String rollExtra, int partialScore) {
        this.number = number;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.rollExtra = rollExtra;
        this.partialScore = partialScore;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRoll1() {
        return roll1;
    }

    public void setRoll1(String roll1) {
        this.roll1 = roll1;
    }

    public String getRoll2() {
        return roll2;
    }

    public void setRoll2(String roll2) {
        this.roll2 = roll2;
    }

    public String getRollExtra() {
        return rollExtra;
    }

    public void setRollExtra(String rollExtra) {
        this.rollExtra = rollExtra;
    }

    public int getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(int partialScore) {
        this.partialScore = partialScore;
    }
}
