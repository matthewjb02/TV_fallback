package nl.hu.inno.hulp.exam.presentation.controller;

import nl.hu.inno.hulp.commons.dto.GradingCriteriaDTO;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.exam.application.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ExamResponse getExamById(@PathVariable String id) {
        return examService.getExamById(id);
    }

    @PostMapping("/examMaker/{makerId}/examValidator/{examValidatorId}")
    public ExamResponse addExam(@PathVariable String makerId, @PathVariable String examValidatorId) {
        return examService.addExam(makerId, examValidatorId);
    }

    @DeleteMapping("/{id}")
    public ExamResponse deleteExam(@PathVariable String id) {
        return examService.deleteExam(id);
    }

    @PostMapping("/{examId}/questions")
    public ExamResponse addQuestionsByIdsToExam(@PathVariable String examId, @RequestBody List<String> questionIds) {
        return examService.addQuestionsByIdsToExam(examId, questionIds);
    }

    @PostMapping("/{examId}/grading-criteria")
    public ExamResponse addGradingCriteriaToExam(@PathVariable String examId, @RequestBody GradingCriteriaDTO gradingCriteriaDTO) {
        return examService.addGradingCriteriaToExam(examId, gradingCriteriaDTO);
    }

    // used by other modules via rpc
    @GetMapping("{examId}/students/{studentId}/submission")
    public SubmissionResponse getSubmissionByExamAndStudentId(@PathVariable String examId, @PathVariable String studentId) {
        return examService.getSubmissionByExamAndStudentId(examId, studentId);
    }

    @GetMapping("{examId}/submissions")
    public List<SubmissionResponse> getSubmissionsByExamId(@PathVariable String examId) {
        return examService.getSubmissionsByExamId(examId);
    }


    @PostMapping("/{examId}/gradeCalculation")
    public double calculateGrade(@PathVariable String examId) {
        return examService.calculateGrade(examId);

    }

}




