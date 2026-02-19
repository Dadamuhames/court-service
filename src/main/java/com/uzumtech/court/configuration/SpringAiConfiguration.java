package com.uzumtech.court.configuration;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SpringAiConfiguration {

    @Bean
    public PgVectorStore vectorStore(JdbcTemplate jdbcTemplate, @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
            .dimensions(768)
            .build();
    }

    @Bean
    public OpenAiChatOptions chatOptions() {
       Double OPEN_AI_CHAT_TEMPERATURE = 0.1;
       Double OPEN_AI_TOP_PROBABILITY_MASS = 0.1;

       return
            OpenAiChatOptions.builder()
                .temperature(OPEN_AI_CHAT_TEMPERATURE)
                .topP(OPEN_AI_TOP_PROBABILITY_MASS)
                .responseFormat(ResponseFormat.builder().type(ResponseFormat.Type.JSON_OBJECT).build())
                .build();
    }
}
