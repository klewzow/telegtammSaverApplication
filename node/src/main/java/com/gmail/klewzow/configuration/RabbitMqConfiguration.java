package com.gmail.klewzow.configuration;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gmail.klewzow.StatusMessageQueue.*;


@Log4j
@Configuration
public class RabbitMqConfiguration {

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
