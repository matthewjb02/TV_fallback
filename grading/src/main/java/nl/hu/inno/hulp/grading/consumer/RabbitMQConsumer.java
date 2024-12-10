package nl.hu.inno.hulp.grading.consumer;



import nl.hu.inno.hulp.grading.application.SubmissionService;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer  {

    private final SubmissionService submissionService;
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RabbitMQConsumer.class);

    public RabbitMQConsumer(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

//
//    @RabbitListener(queues = "${rabbit.monoliet.demo.queue}")
//    public void consumeNoteMessage(String string) {
//        LOGGER.info("Message received: {}", string);
//    }

//    @RabbitListener(queues = "${rabbitmq.examsession.grading.queue}")
//    public void consumeAddSubmissionMessage(Long submissionId) {
//        LOGGER.info("Message received: {}", submissionId);
//        submissionService.saveSubmission(submissionId);
//    }
}


