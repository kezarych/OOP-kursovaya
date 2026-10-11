package kursovaya.oop;

import java.util.List;

public class SeniorCandidate extends Candidate{

    SeniorCandidate(String firstName, String lastName,int id, String description, int salary, EnglishLVL englishLVL, ProgrammingLVL programmingLVL){
        super(firstName, lastName, id, description, salary, englishLVL, programmingLVL);
    }

    @Override
    int getTotalScore() {
        return getEnglishLVL().getScore() + getProgrammingLVL().getScore()*2;
    }

    @Override
    String getCategory() {
        return "Senior";
    }

    @Override
    String getManagerHint() {
        if(getEnglishLVL().getScore() < 4){
            return "вам следует углубить знание английского";
        }
        if (getProgrammingLVL().getScore() < 10){
            return "вам стоит улучшить ваши навыки в программировании";
        }
        else{
            return "рекомендаций нет";
        }
    }

    @Override
    String getRecommendation() {
        return "рассмотреть на роль тим лида";
    }

    @Override
    List<String> getInterviewStage() {
        return List.of("HR-интервью", "тест","техническое интервью","системный дизайн");
    }

}
