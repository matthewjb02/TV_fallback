package nl.hu.inno.hulp.examination.presentation.controller;

import nl.hu.inno.hulp.commons.exception.NoExaminationFoundException;
import nl.hu.inno.hulp.commons.request.Candidate;
import nl.hu.inno.hulp.commons.request.Candidates;
import nl.hu.inno.hulp.commons.request.CreateExamination;
import nl.hu.inno.hulp.commons.response.CandidatesResponse;
import nl.hu.inno.hulp.commons.response.ExaminationResponse;
import nl.hu.inno.hulp.examination.application.service.ExaminationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/examination")
public class ExaminationController {
    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/test")
    public String test() {
        return "Grading service is up and running!";
    }


    @GetMapping("{id}")
    public ExaminationResponse getExamination(@PathVariable Long id) {
        try {
            return examinationService.getExaminationResponse(id);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public ExaminationResponse createExamination(@RequestBody CreateExamination createExamination) {
        try {
            return examinationService.createExamination(createExamination);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/candidates")
    public CandidatesResponse selectCandidates(@RequestBody Candidates candidates) {
        try {
            return examinationService.selectCandidates(candidates);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/candidate")
    public CandidatesResponse selectCandidate(@RequestBody Candidate candidate) {
        try {
            return examinationService.selectCandidate(candidate);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/candidates")
    public CandidatesResponse removeCandidates(@RequestBody Candidates candidates) {
        try {
            return examinationService.removeCandidates(candidates);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/candidate")
    public CandidatesResponse removeCandidate(@RequestBody Candidate candidate) {
        try {
            return examinationService.removeCandidate(candidate);
        } catch (NoExaminationFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
    }
}
