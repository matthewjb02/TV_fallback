package nl.hu.inno.hulp.exam.presentation.controller;

import nl.hu.inno.hulp.commons.request.QuestionRequest;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.exam.application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping
    public QuestionResponse addQuestion(@RequestBody QuestionRequest question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public QuestionResponse deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
}
