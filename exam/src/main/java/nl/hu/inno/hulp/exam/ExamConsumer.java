package nl.hu.inno.hulp.exam;


import nl.hu.inno.hulp.commons.request.AddSubmissionToExam;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPoints;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.exam.application.service.CourseService;
import nl.hu.inno.hulp.exam.application.service.ExamService;
import nl.hu.inno.hulp.exam.domain.Exam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ExamConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamConsumer.class);
    private final ExamService examService;
    private final CourseService courseService;

    public ExamConsumer(ExamService examService, CourseService courseService) {
        this.examService = examService;
        this.courseService = courseService;
    }


    @RabbitListener(queues = {"examination"})
    public void consumeExamId(Long examId){
        LOGGER.info(String.format("Received message -> %s", examId));
        examService.sendAndProcessExam(examId);
    }
    @RabbitListener(queues = {"examination"})
    public void consumeCourse(Long courseId){
        LOGGER.info(String.format("Received message -> %s", courseId));
        courseService.sendAndProcessCourse(courseId);
    }
    @RabbitListener(queues={"${rabbitmq.queue.name}"})
    public void consumeTeacherResponse(@Payload TeacherResponse teacherResponse){
        LOGGER.info(String.format("Received JSON message teacherResponse -> %s", teacherResponse));
    }

    @RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveUpdateOpenQuestionGradingMessage(UpdateOpenQuestionPoints message) {
        examService.updatePointsForOpenQuestion(message.getExamId(), message.getQuestionNr(), message.getGrading());
    }

    @RabbitListener(queues = "${rabbit.grading.exam.queue}")
    public void receiveUpdateExamStatisticsMessage(Long examId) {
        examService.updateStatistics(examId);
    }

    @RabbitListener(queues = "${rabbitmq.examsession.exam.queue}")
    public void receiveAddSubmissionToExamMessage(AddSubmissionToExam message) {
        examService.addSubmission(message.getExamId(), message.getSubmissionId());
    }
}

