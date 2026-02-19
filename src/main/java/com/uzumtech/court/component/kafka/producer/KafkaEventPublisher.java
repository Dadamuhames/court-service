package com.uzumtech.court.component.kafka.producer;

public interface KafkaEventPublisher<E> {
    void publish(final E event);
}
