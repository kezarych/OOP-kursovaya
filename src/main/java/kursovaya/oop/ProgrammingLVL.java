package kursovaya.oop;

public enum ProgrammingLVL {

    JuniorMinus(3),
    Junior(4),
    JuniorPlus(5),
    MiddleMinus(6),
    Middle(7),
    MiddlePlus(8),
    SeniorMinus(9),
    Senior(10),
    SeniorPlus(11);

    private final int score;


    ProgrammingLVL(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
