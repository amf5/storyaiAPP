package com.storyAi.story_AI.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicConfiguration {
@Bean
public NewTopic createBookTopic() {
	return TopicBuilder.name("new-books")
            .partitions(1)
            .replicas(1)
            .config("retention.ms", "2592000000")
            .build();
}

@Bean
public NewTopic createVideoTopic() {
	return TopicBuilder.name("new-videos")
            .partitions(1)
            .replicas(1)
            .config("retention.ms", "2592000000")
            .build();
}
}
