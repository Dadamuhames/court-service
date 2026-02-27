package com.uzumtech.court.repository;

import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
class OffenseRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("pgvector/pgvector:pg16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private OffenseRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private JudgeEntity judge;
    private UserEntity user;
    private ExternalServiceEntity service;
    private OffenseEntity offense;

    private final String PROTOCOL_NUMBER = "FP-20260202-0001";
    private final String COUR_CASE_NUMBER = "JC-20260202-0001";

    @BeforeEach
    void setUp() {
        judge = JudgeEntity.builder()
            .email("junsnow@wall.nrth")
            .password("hash")
            .fullName("Jon Snow")
            .build();
        entityManager.persist(judge);

        user = UserEntity.builder()
            .pinfl("12345678901234")
            .fullName("Citizen Smith")
            .age(30)
            .build();
        entityManager.persist(user);

        service = ExternalServiceEntity.builder()
            .login("test_fines")
            .password("hash")
            .webhookSecret("secret")
            .webhookUrl("https://webhook.com")
            .build();

        entityManager.persist(service);

        // 2. Setup Primary Offense
        offense = OffenseEntity.builder()
            .externalId(100L)
            .judge(judge)
            .user(user)
            .externalService(service)
            .status(OffenseStatus.PENDING)
            .offenseLocation("Tashkent")
            .description("Speeding")
            .protocolNumber(PROTOCOL_NUMBER)
            .courtCaseNumber(COUR_CASE_NUMBER)
            .codeArticleReference("Art. 128")
            .offenseDateTime(OffsetDateTime.now())
            .build();

        entityManager.persist(offense);
        flushAndClear();
    }

    @Test
    void testFindByUserIdAndIdNot() {
        final String PROTOCOL_NUMBER_NEW = "FP-20260202-0002";
        final String CASE_NUMBER_NEW = "JC-20260202-0002";


        OffenseEntity secondOffense = OffenseEntity.builder()
            .externalId(101L)
            .judge(judge)
            .user(user)
            .externalService(service)
            .status(OffenseStatus.PENDING)
            .offenseLocation("Tashkent")
            .description("Parking")
            .protocolNumber(PROTOCOL_NUMBER_NEW)
            .courtCaseNumber(CASE_NUMBER_NEW)
            .codeArticleReference("Art. 129")
            .offenseDateTime(OffsetDateTime.now())
            .build();

        entityManager.persist(secondOffense);

        flushAndClear();

        List<OffenseEntity> results = repository.findByUserIdAndIdNot(user.getId(), offense.getId());

        assertEquals(1, results.size());
        assertEquals(PROTOCOL_NUMBER_NEW, results.getFirst().getProtocolNumber());
    }

    @Test
    void testExistsByExternalIdAndExternalServiceId() {
        boolean exists = repository.existsByExternalIdAndExternalServiceId(100L, service.getId());
        assertTrue(exists);
    }

    @Test
    void testMarkForAiDecisionProcessing() {
        int updatedRows = repository.markForAiDecisionProcessing(offense.getId(), judge.getId());

        flushAndClear();

        OffenseEntity updated = entityManager.find(OffenseEntity.class, offense.getId());

        assertEquals(1, updatedRows);
        assertNotNull(updated);
        assertEquals(OffenseStatus.PROCESSING_AI_DECISION, updated.getStatus());
        assertNotNull(updated.getUpdatedAt());
    }

    @Test
    void testExistsByIdAndProcessing() {
        assertFalse(repository.existsByIdAndProcessing(offense.getId()));

        repository.updateStatus(offense.getId(), OffenseStatus.PROCESSING_AI_DECISION);

        flushAndClear();

        assertTrue(repository.existsByIdAndProcessing(offense.getId()));
    }

    @Test
    void testFindByIdAndJudgeId() {
        Optional<OffenseEntity> offenseEntity = repository.findByIdAndJudgeId(offense.getId(), judge.getId());
        assertTrue(offenseEntity.isPresent());
        assertEquals(PROTOCOL_NUMBER, offenseEntity.get().getProtocolNumber());
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}