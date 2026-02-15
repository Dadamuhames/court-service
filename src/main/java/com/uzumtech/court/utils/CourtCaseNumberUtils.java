package com.uzumtech.court.utils;

import com.uzumtech.court.repository.OffenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CourtCaseNumberUtils {
    private final OffenseRepository legalOffenceRepository;

    public String generateProtocolNumber() {
        var today = LocalDate.now();

        int countTodayOffenses = legalOffenceRepository.countByCreatedAt(today);

        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String sequence = String.format("%05d", countTodayOffenses + 1);

        return "JC-" + datePart + "-" + sequence;
    }

}
