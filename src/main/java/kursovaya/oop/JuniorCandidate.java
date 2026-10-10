package kursovaya.oop;

import java.util.List;

public class JuniorCandidate extends Candidate{

    public JuniorCandidate(String firstName, String lastName, int id, String description, int salary, EnglishLVL englishLVL, ProgrammingLVL programmingLVL) {
        super(firstName, lastName, id, description, salary, englishLVL, programmingLVL);
    }

    @Override
    int getTotalScore() {
        return getEnglishLVL().getScore() * 2 + getProgrammingLVL().getScore();
    }

    @Override
    String getCategory() {
        return "Junior";
    }

    @Override
    String getManagerHint() {
        if(getTotalScore() < 8){
            return "кандидатуре стоит подтянуть свои навыки";
        }
        else {
            return "кандидатура может быть рассмотрена";
        }
    }

    @Override
    String getRecommendation() {
        return "стажировка с наставником";
    }

    @Override
    List<String> getInterviewStage() {
        return List.of("HR-собеседование", "тест");
    }

}
