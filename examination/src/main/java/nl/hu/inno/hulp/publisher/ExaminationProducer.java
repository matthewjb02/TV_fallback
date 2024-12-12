package nl.hu.inno.hulp.publisher;

import nl.hu.inno.hulp.commons.request.*;
import nl.hu.inno.hulp.commons.response.ExamSessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExaminationProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationProducer.class);
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ExaminationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentRequest(String studentId) {
        LOGGER.info(String.format("Message sent -> student id: %s", studentId));
        rabbitTemplate.convertAndSend(exchange, routingKey, studentId);
    }

    public void sendExamRequest(String examId) {
        LOGGER.info(String.format("Message sent -> exam id: %s", examId));
        rabbitTemplate.convertAndSend(exchange, routingKey, examId);
    }
    public void sendCourseRequest(String courseId) {
        LOGGER.info(String.format("Message sent -> course id: %s", courseId));
        rabbitTemplate.convertAndSend(exchange, routingKey, courseId);
    }

    public void sendQuestionRequest(String examSessionId, String examId, String questionId) {
        LOGGER.info(String.format("Message sent -> exam-session id: %s exam id: %s, question id: %s", examSessionId, examId, questionId));
        SeeQuestion seeQuestion = new SeeQuestion(examSessionId, examId, questionId);
        rabbitTemplate.convertAndSend(exchange, routingKey, seeQuestion);
    }

    public void sendAnswerRequest(AnswerRequest answerRequest) {
        LOGGER.info(String.format("Message sent -> answer-request %s", answerRequest));
        rabbitTemplate.convertAndSend(exchange, routingKey, answerRequest);
    }

    public void endingSessionRequest(ExamSessionResponse examSessionResponse) {
        LOGGER.info(String.format("Message sent -> exam-session-response: %s", examSessionResponse));
        rabbitTemplate.convertAndSend(exchange, routingKey, examSessionResponse);
    }

    public void sendUpdateQuestionGradingRequest(String examSessionId, int questionNr, UpdateOpenQuestionPointsRequest request) {
        UpdateOpenQuestionPoints updateOpenQuestionPoints = new UpdateOpenQuestionPoints(examSessionId, questionNr, request);
        LOGGER.info(String.format("Message sent -> exam-session id: %s, question number: %s, request: %s", updateOpenQuestionPoints));
        rabbitTemplate.convertAndSend(exchange, routingKey, updateOpenQuestionPoints);

    }

    public void sendAddSubmissionToExamRequest(String examId, String submissionId) {
        AddSubmissionToExam addSubmissionToExam = new AddSubmissionToExam(examId, submissionId);
        LOGGER.info(String.format("Message sent -> exam-session id: %s, submission-response: %s", addSubmissionToExam));
        rabbitTemplate.convertAndSend(exchange, routingKey, addSubmissionToExam);
    }

    public void saveSubmissionRequest(String submissionId) {
        LOGGER.info(String.format("Message sent -> submission id: %s", submissionId));
        rabbitTemplate.convertAndSend(exchange, routingKey, submissionId);
    }
}