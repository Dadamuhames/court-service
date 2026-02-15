package com.uzumtech.court.configuration.property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KafkaConsumerProperties {
    private String bootstrapServers;
}
