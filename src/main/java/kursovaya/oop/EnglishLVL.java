package kursovaya.oop;

public enum EnglishLVL {
    A1(1),
    A2(2),
    B1(3),
    B2(4),
    C1(5),
    C2(6);

    private final int score;

    EnglishLVL(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}
