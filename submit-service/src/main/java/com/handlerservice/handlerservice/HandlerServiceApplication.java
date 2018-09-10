package com.handlerservice.handlerservice;

import com.handlerservice.handlerservice.property.FileStorageProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})public class HandlerServiceApplication {

    static final String topicExchangeName = "jsa.direct1";
    static final String queueName = "jsa.queue1";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("jsa.routingkey1");
    }

    public static void main(String[] args) {
        SpringApplication.run(HandlerServiceApplication.class, args);
    }
}
