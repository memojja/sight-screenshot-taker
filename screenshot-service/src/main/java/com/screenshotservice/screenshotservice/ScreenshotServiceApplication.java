package com.screenshotservice.screenshotservice;

import com.screenshotservice.screenshotservice.service.WebPageScreenShotTaker;
import com.screenshotservice.screenshotservice.utils.ScreenShotUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScreenshotServiceApplication {

    @Autowired
    private static SimpleMessageListenerContainer container;


    @Bean
    Queue queue() {
        return new Queue(ScreenShotUtil.queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(ScreenShotUtil.topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ScreenShotUtil.routingkey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ScreenShotUtil.queueName);
        container.setMessageListener(listenerAdapter);
        container.setConcurrentConsumers(10);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(WebPageScreenShotTaker receiver) {
        return new MessageListenerAdapter(receiver, "capture");
    }

    public static void main(String[] args) {
        SpringApplication.run(ScreenshotServiceApplication.class, args);
    }

}
