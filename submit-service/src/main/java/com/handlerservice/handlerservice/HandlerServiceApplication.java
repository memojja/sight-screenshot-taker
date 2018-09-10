package com.handlerservice.handlerservice;

import com.handlerservice.handlerservice.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})public class HandlerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandlerServiceApplication.class, args);
    }
}
