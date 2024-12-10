package nl.hu.inno.hulp.grading.presentation;

import nl.hu.inno.hulp.grading.producer.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/grading")
public class RabbitMQContoller {

    private final RabbitMQProducer rabbitMQProducer;

    public RabbitMQContoller(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to the RabbitMQ JavaInUse Successfully");
    }

}
