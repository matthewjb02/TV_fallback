package nl.hu.inno.hulp.exam;

import nl.hu.inno.hulp.commons.request.TeacherRequest;
import nl.hu.inno.hulp.commons.response.CourseResponse;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.commons.response.StudentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExamProducer {
    @Value("examination-exchange")
    private String exchange;

    @Value("examination-routing")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamProducer.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ExamProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendExamResponse(ExamResponse examResponse) {
        LOGGER.info(String.format("Message sent -> %s", examResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, examResponse);
    }
    public void sendTeacherRequest(long teacherId) {
        LOGGER.info(String.format("Message sent -> %s", teacherId + ""));
        rabbitTemplate.convertAndSend(exchange, routingKey, teacherId);

    }
    public void sendCourseResponse(CourseResponse courseResponse) {
        LOGGER.info(String.format("Message sent -> %s", courseResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, courseResponse);
    }
}
