package kursovaya.oop;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    public List<Candidate> filter(List<Candidate> candidates, String category){
        List<Candidate> result = new ArrayList<>();
        for (Candidate candidate : candidates){
            if (candidate.getCategory().equals(category)){
                result.add(candidate);
            }
        }

        return result;
    }

    public List<Candidate> filter(List<Candidate> candidates, int minScore){
        List<Candidate> result = new ArrayList<>();

        for (Candidate candidate : candidates){
            if(candidate.getTotalScore() >= minScore){
                result.add(candidate);
            }
        }
        return result;
    }

    public List<Candidate> filter(List<Candidate> candidates, String category, int minScore){
        List<Candidate> result = new ArrayList<>();
        for(Candidate candidate : candidates){
            if (candidate.getTotalScore() >= minScore && candidate.getCategory().equals(category)){
                result.add(candidate);
            }
        }
        return result;
    }
}
