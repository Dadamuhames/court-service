package com.uzumtech.court.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiContextLoader {
    private final VectorStore vectorStore;

    @Value("classpath:/ai/data/law_code.txt")
    private Resource resource;

    @EventListener(ApplicationReadyEvent.class)
    public void loadAiContext() {
        DocumentReader reader = new TextReader(resource);
        List<Document> loadedDocuments = reader.get();

        TokenTextSplitter textSplitter = new TokenTextSplitter();
        List<Document> chunks = textSplitter.split(loadedDocuments);

        vectorStore.add(chunks);
    }
}
