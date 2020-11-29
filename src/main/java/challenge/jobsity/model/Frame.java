package challenge.jobsity.model;

public class Frame {
    private int number;
    private String chance1;
    private String chance2;
    private String chanceExtra;
    private int partialScore;

    public Frame(int number, String chance1, int partialScore) {
        this(number, chance1, null, null, partialScore);
    }

    public Frame(int number, String chance1, String chance2, int partialScore) {
        this(number, chance1, chance2, null, partialScore);
    }

    public Frame(int number, String chance1, String chance2, String chanceExtra, int partialScore) {
        this.number = number;
        this.chance1 = chance1;
        this.chance2 = chance2;
        this.chanceExtra = chanceExtra;
        this.partialScore = partialScore;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getChance1() {
        return chance1;
    }

    public void setChance1(String chance1) {
        this.chance1 = chance1;
    }

    public String getChance2() {
        return chance2;
    }

    public void setChance2(String chance2) {
        this.chance2 = chance2;
    }

    public String getChanceExtra() {
        return chanceExtra;
    }

    public void setChanceExtra(String chanceExtra) {
        this.chanceExtra = chanceExtra;
    }

    public int getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(int partialScore) {
        this.partialScore = partialScore;
    }
}
