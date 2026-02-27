package com.uzumtech.court.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CourtCaseNumberUtils {

    public String generateProtocolNumber() {
        var today = LocalDate.now();

        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String sequence = String.valueOf(Instant.now().toEpochMilli());

        return "JC-" + datePart + "-" + sequence;
    }

}
