package kursovaya.oop;

import java.util.List;

public class MiddleCandidate extends Candidate{

    MiddleCandidate(String firstName, String lastName,int id, String description, int salary, EnglishLVL englishLVL, ProgrammingLVL programmingLVL){
        super(firstName, lastName, id, description, salary, englishLVL, programmingLVL);
    }

    @Override
    String getCategory() {
        return "Middle";
    }

    @Override
    String getManagerHint() {
        if (getEnglishLVL().getScore() < 4){
            return "вам нужно подтянуть знания английского";
        }
        if (getProgrammingLVL().getScore() < 7){
            return "подтяните знания по программированию";
        }
        else {
            return "наставлений нет";
        }
    }

    @Override
    String getRecommendation() {
        return "вас отправят сразу на проект";
    }

    @Override
    List<String> getInterviewStage() {
        return List.of("HR-интервью", "тест", "техническое интервью");
    }

    @Override
    public int getTotalScore(){
        return getEnglishLVL().getScore() + getProgrammingLVL().getScore();
    }
}
