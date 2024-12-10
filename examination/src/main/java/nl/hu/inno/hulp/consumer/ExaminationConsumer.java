package nl.hu.inno.hulp.consumer;

import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import nl.hu.inno.hulp.commons.response.*;
import nl.hu.inno.hulp.examination.application.service.ExamSessionService;
import nl.hu.inno.hulp.examination.domain.ExamSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ExaminationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExaminationConsumer.class);
    private final ExamSessionService examSessionService;

    public ExaminationConsumer(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    //@RabbitListener(queues = {"${rabbit.grading.demo.queue}"})
    public void consumeDemoMessage(String string) {
        //LOGGER.info("Message received: {}", string);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeStudentResponse(@Payload StudentResponse studentResponse) {
        LOGGER.info(String.format("Received JSON message -> student-response: %s", studentResponse));
    }
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeCourseResponse(@Payload CourseResponse courseResponse) {
        LOGGER.info(String.format("Received JSON message -> course-response: %s", courseResponse));
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeExamResponse(@Payload ExamResponse examResponse) {
        LOGGER.info(String.format("Received JSON message -> exam-response: %s", examResponse));
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeQuestionResponse(@Payload QuestionResponse questionResponse) {
        LOGGER.info(String.format("Received JSON message -> question-response: %s", questionResponse));
    }

    //@RabbitListener(queues = {"${rabbitmq.grading.examsession.queue}"})
    public void  updateOpenQuestionGrading(Long examSessionIdFromSubmission, int questionNr, UpdateOpenQuestionPointsRequest request) {
        LOGGER.info(String.format("Received JSON message -> update-question-grading: %s", request));
        //examSessionService.updatePointsOpenQuestion(examSessionIdFromSubmission, questionNr, request);
    }
}