package nl.hu.inno.hulp.exam.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.commons.request.MultipleChoiceQuestionRequest;
import nl.hu.inno.hulp.commons.request.OpenQuestionRequest;
import nl.hu.inno.hulp.commons.request.QuestionRequest;
import nl.hu.inno.hulp.commons.response.MultipleChoiceQuestionResponse;
import nl.hu.inno.hulp.commons.response.OpenQuestionResponse;
import nl.hu.inno.hulp.commons.response.QuestionResponse;
import nl.hu.inno.hulp.exam.data.QuestionRepository;
import nl.hu.inno.hulp.exam.domain.question.MultipleChoiceQuestion;
import nl.hu.inno.hulp.exam.domain.question.OpenQuestion;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionResponse> getAllQuestions() {
        List<QuestionEntity> allQuestions = questionRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (QuestionEntity question : allQuestions) {
            questionResponses.add(getQuestionResponse(question));
        }

        return questionResponses;
    }

    public QuestionResponse getQuestionById(Long id) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No question with id: " + id + " found!"));

        return getQuestionResponse(question);
    }

    public QuestionResponse addQuestion(QuestionRequest question) {

        QuestionEntity entity;
        if (question instanceof MultipleChoiceQuestionRequest){
            entity = new MultipleChoiceQuestion(question.getPoints(), question.getQuestion(),
                    ((MultipleChoiceQuestionRequest) question).getCorrectAnswerIndexes(),
                    ((MultipleChoiceQuestionRequest) question).getAnswers());

        } else {
            entity = new OpenQuestion(question.getPoints(), question.getQuestion(), ((OpenQuestionRequest)question).getCorrectAnswer());
        }

        QuestionEntity savedEntity = questionRepository.save(entity);

        return getQuestionById(savedEntity.getId());
    }

    public QuestionResponse deleteQuestion(Long id) {
        QuestionResponse oldResponse = getQuestionById(id);
        questionRepository.deleteById(id);
        return oldResponse;
    }

    public QuestionResponse getQuestionResponse(QuestionEntity question){
        if (question instanceof MultipleChoiceQuestion){
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion)question;
            return new MultipleChoiceQuestionResponse(mcQuestion.getPoints(), mcQuestion.getQuestion(),
                    mcQuestion.getAnswers(),
                    mcQuestion.getCorrectAnswerIndexes(), mcQuestion.getGivenAnswers());
        } else {
            OpenQuestion openQuestion = (OpenQuestion) question;
            return new OpenQuestionResponse(openQuestion.getPoints(), openQuestion.getQuestion(), openQuestion.getCorrectAnswer(),
                    openQuestion.getAnswer(), openQuestion.getTeacherFeedback());
        }
    }
}
