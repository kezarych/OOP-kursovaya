package kursovaya.oop;

import java.util.List;

public abstract class Candidate extends Person{

    private int id;
    private String description;
    private int salary;
    private EnglishLVL englishLVL;
    private ProgrammingLVL programmingLVL;

    public Candidate(String firstName, String lastName, int id,String description, int salary,EnglishLVL englishLVL,ProgrammingLVL programmingLVL){
        super(firstName, lastName);
        this.id = id;
        this.description =description;
        this.englishLVL = englishLVL;
        this.programmingLVL = programmingLVL;
        this.salary = salary;
    }

    abstract int getTotalScore();
    abstract String getCategory();
    abstract String getManagerHint();
    abstract String getRecommendation();
    abstract List<String> getInterviewStage();

    public EnglishLVL getEnglishLVL(){
        return englishLVL;
    }

    public ProgrammingLVL getProgrammingLVL(){
        return programmingLVL;
    }

    public String getDescription(){
        return description;
    }

    public int getSalary(){
        return salary;
    }
}
