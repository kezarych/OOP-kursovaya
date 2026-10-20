package kursovaya.oop;

import org.springframework.stereotype.Component;

@Component
public class CandidateChooser {

    public Candidate createCandidate(String firstName, String lastName, int id, int salary, EnglishLVL englishLVL, ProgrammingLVL programmingLVL, String description){

        if(programmingLVL.getScore() >= 3 && programmingLVL.getScore() <=5){
            return new JuniorCandidate(firstName, lastName,id, description, salary, englishLVL, programmingLVL);
        }

        else if (programmingLVL.getScore() >= 6 && programmingLVL.getScore() <=8){
            return new MiddleCandidate(firstName, lastName,id, description, salary, englishLVL, programmingLVL);
        }

        else if(programmingLVL.getScore() >= 9 && programmingLVL.getScore() <=11){
            return new SeniorCandidate(firstName, lastName,id, description, salary, englishLVL, programmingLVL);
        }

        else {
            return new JuniorCandidate("", "", 0, "", 0, EnglishLVL.A1, ProgrammingLVL.JuniorMinus);
        }

    }
}
