package nl.hu.inno.hulp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class ExaminationConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

        return http.build();
    }

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.examsession.exam.queue}")
    private String examSessionQueue;

    @Value("${rabbitmq.examsession.grading.queue}")
    private String examSessionGradingQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value ("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public Queue examSessionQueue() {
        return new Queue(examSessionQueue);
    }

    @Bean
    public Queue examSessionGradingQueue() {
        return new Queue(examSessionGradingQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding examSessionBinding() {
        return BindingBuilder
                .bind(examSessionQueue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding examSessionGradingBinding() {
        return BindingBuilder
                .bind(examSessionGradingQueue())
                .to(exchange())
                .with(routingKey);
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}