package nl.hu.inno.hulp.grading.producer;

import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPoints;
import nl.hu.inno.hulp.commons.request.UpdateOpenQuestionPointsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void sendMessage(String string) {

        LOGGER.info("Sending message: {}", string);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, string);
    }

    public void sendUpdateOpenQuestionPoints(String examSessionIdFromSubmission, int questionNr, UpdateOpenQuestionPointsRequest request) {
        UpdateOpenQuestionPoints updateOpenQuestionPoints = new UpdateOpenQuestionPoints(examSessionIdFromSubmission, questionNr, request);
        LOGGER.info("Sending message: {}", updateOpenQuestionPoints);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, updateOpenQuestionPoints);
    }

    public void sendUpdateExamStatistics(String examId) {

        LOGGER.info("Sending message: {}", examId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, examId);
    }
}