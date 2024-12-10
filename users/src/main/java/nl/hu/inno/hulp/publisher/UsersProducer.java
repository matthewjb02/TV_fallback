package nl.hu.inno.hulp.publisher;

import nl.hu.inno.hulp.commons.response.StudentResponse;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UsersProducer {
    @Value("examination-exchange")
    private String exchange;

    @Value("examination-routing")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersProducer.class);
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UsersProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentResponse(StudentResponse studentResponse) {
        LOGGER.info(String.format("Message sent -> %s", studentResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, studentResponse);
    }
    public void sendTeacherResponse(TeacherResponse teacherResponse) {
        LOGGER.info(String.format("Message sent -> %s", teacherResponse.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, teacherResponse);
    }
}