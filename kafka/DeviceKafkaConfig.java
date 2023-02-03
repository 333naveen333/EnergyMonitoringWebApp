package com.energyms.energyms.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DeviceKafkaConfig {	

    @Bean
    public NewTopic javaguidesJsonTopic(){
        return TopicBuilder.name("devicedata_topic")//*
                .build();
    }
    //devicedata_topic-1
}
