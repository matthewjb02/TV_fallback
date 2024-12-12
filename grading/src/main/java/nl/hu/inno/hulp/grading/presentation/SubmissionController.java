package nl.hu.inno.hulp.grading.presentation;

import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.grading.application.SubmissionService;
import nl.hu.inno.hulp.commons.request.GradingRequest;
import nl.hu.inno.hulp.commons.response.SubmissionResponse;
import nl.hu.inno.hulp.grading.domain.Grading;
import nl.hu.inno.hulp.grading.producer.RabbitMQProducer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    private final RabbitMQProducer rabbitMQProducer;

    public SubmissionController(SubmissionService submissionService, RabbitMQProducer rabbitMQProducer) {
        this.submissionService = submissionService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/test")
    public String test() {
        return "Grading service is up and running!";
    }

    @GetMapping("/{testId}")
    public List<SubmissionResponse> getSubmissionsByTestId(@PathVariable String testId) {
        return submissionService.getSubmissionsByExamId(testId);
    }

    @GetMapping("/{id}/sub")
    public SubmissionResponse getSubmissionById(@PathVariable String id) {
        return submissionService.getSubmissionResponseById(id);
    }

    @PutMapping("/{examId}/{studentId}/question/{questionNr}")
    public void updateQuestionGrading(@PathVariable String examId, @PathVariable String studentId, @PathVariable int questionNr, @RequestBody UpdateOpenQuestionPointsRequest request) {

        submissionService.updateOpenQuestionGrading(examId, studentId, questionNr, request);
    }

    @PostMapping("/{testId}/{studentId}/grading")
    public void addGrading(@PathVariable String testId, @PathVariable String studentId, @RequestBody GradingRequest request) {
        submissionService.addGrading(testId, studentId, request);
    }

    // rpc
    @PostMapping("/{examSessionId}/create")
    public SubmissionResponse createSubmission(@PathVariable String examSessionId) {
        return submissionService.createSubmission(examSessionId);
    }

    @PostMapping("{submissionId}")
    public  void saveSubmission(@PathVariable String submissionId) {
        submissionService.saveSubmission(submissionId);
    }
}