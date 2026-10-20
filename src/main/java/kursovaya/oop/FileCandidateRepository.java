package kursovaya.oop;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Repository
public class FileCandidateRepository{

    private final CandidateChooser candidateChooser;

    public FileCandidateRepository(CandidateChooser candidateChooser) {
        this.candidateChooser = candidateChooser;
    }

    Path path = Path.of("candidates.txt");
    Path path1 = Path.of("accepted.txt");

    public void save(Candidate candidate){
        try {
            Files.write(path, List.of(candidate.getId() + "|"
                            + candidate.getFirstName() + "|"
                            + candidate.getLastName() + "|"
                            + candidate.getDescription() + "|"
                            + candidate.getEnglishLVL() + "|"
                            + candidate.getProgrammingLVL() + "|"
                            + candidate.getSalary()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Candidate> findAll(){

        List<String> lines = new ArrayList<>();
        List<Candidate> candidates = new ArrayList<Candidate>();

        if(!Files.exists(path)){
            return candidates;
        }

        try{
        lines = Files.readAllLines(path);
        String[] parts;
        for (String line : lines) {
            parts = line.split("\\|");
            Candidate candidate;

            candidate = candidateChooser.createCandidate(parts[1], parts[2], Integer.parseInt(parts[0]), Integer.parseInt(parts[6]), EnglishLVL.valueOf(parts[4]), ProgrammingLVL.valueOf(parts[5]), parts[3]);
            candidates.add(candidate);
        }

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return candidates;
    }


    public void accept(int id){
        try {
            Files.write(path1, List.of(String.valueOf(id)), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Integer> findAcceptedIds(){
        List<Integer> acceptedIds = new ArrayList<Integer>();
        List<String> lines = new ArrayList<>();

        if (!Files.exists(path1)){
            return acceptedIds;
        }

        try{
            lines = Files.readAllLines(path1);
            for (String line : lines){
                acceptedIds.add(Integer.parseInt(line));
            }
            return acceptedIds;

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public int getNextId(){
        if (!Files.exists(path)){
            return 1;
        }

        int maxID = 0;
        try {
        List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.isEmpty()) {
                    continue;
                }
                int candidateId = Integer.parseInt(line.split("\\|")[0]);
                if (candidateId > maxID){
                    maxID = candidateId;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return maxID + 1;
    }
}
