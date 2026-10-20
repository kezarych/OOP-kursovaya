package kursovaya.oop;

import java.util.List;

public class SeniorCandidate extends Candidate{

    SeniorCandidate(String firstName, String lastName,int id, String description, int salary, EnglishLVL englishLVL, ProgrammingLVL programmingLVL){
        super(firstName, lastName, id, description, salary, englishLVL, programmingLVL);
    }

    @Override
    public int getTotalScore() {
        return getEnglishLVL().getScore() + getProgrammingLVL().getScore()*2;
    }

    @Override
    public String getCategory() {
        return "Senior";
    }

    @Override
    public String getManagerHint() {
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
    public String getRecommendation() {
        return "рассмотреть на роль тим лида";
    }

    @Override
    public List<String> getInterviewStage() {
        return List.of("HR-интервью", "тест","техническое интервью","системный дизайн");
    }

}
