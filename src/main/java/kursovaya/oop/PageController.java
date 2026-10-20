package kursovaya.oop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    private final FileCandidateRepository fileCandidateRepository;
    private final CandidateChooser candidateChooser;
    private final CandidateService candidateService;

    public PageController(FileCandidateRepository fileCandidateRepository, CandidateChooser candidateChooser, CandidateService candidateService){
        this.fileCandidateRepository = fileCandidateRepository;
        this.candidateChooser = candidateChooser;
        this.candidateService = candidateService;
    }

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @GetMapping("/candidate")
    public String getCandidatePage(){
        return "candidate";
    }

    @PostMapping("/candidate")
    public String setCandidatesData(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String description, @RequestParam EnglishLVL englishLVL, @RequestParam ProgrammingLVL programmingLVL, @RequestParam int salary){
        Candidate candidate = candidateChooser.createCandidate(firstName, lastName, fileCandidateRepository.getNextId(), salary, englishLVL, programmingLVL, description);
        fileCandidateRepository.save(candidate);
        return "redirect:/";
    }

    @PostMapping("/accept")
    public String setCandidateAccept(@RequestParam int id){
        fileCandidateRepository.accept(id);
        return "redirect:/manager";
    }

    @GetMapping("/manager")
    public String getManagerPage(Model model, @RequestParam(defaultValue = "Все") String category, @RequestParam(defaultValue = "0") int minScore){

        List<Candidate> allCandidates = new ArrayList<>();
        List<Integer> acceptedCandidatesIds = new ArrayList<>();
        List<Candidate> sortedCandidates = new ArrayList<>();

        allCandidates = fileCandidateRepository.findAll();
        acceptedCandidatesIds = fileCandidateRepository.findAcceptedIds();

        List<Candidate> newCandidates = new ArrayList<>();
        List<Candidate> acceptedCandidates = new ArrayList<>();

        for (Candidate candidate : allCandidates){
            if(acceptedCandidatesIds.contains(candidate.getId())){
                acceptedCandidates.add(candidate);
            }
            else {
                newCandidates.add(candidate);
            }
        }

        if (!category.equals("Все") && minScore == 0){
            model.addAttribute("newCandidates", candidateService.filter(newCandidates, category));
        }

        else if (category.equals("Все") && minScore != 0){
            model.addAttribute("newCandidates", candidateService.filter(newCandidates, minScore));
        }

        else if (!category.equals("Все") && minScore !=0){
            model.addAttribute("newCandidates", candidateService.filter(newCandidates, category, minScore));
        }

        else {
            model.addAttribute("newCandidates", newCandidates);
        }

        model.addAttribute("acceptedCandidates",acceptedCandidates);

        return "manager";
    }
}
