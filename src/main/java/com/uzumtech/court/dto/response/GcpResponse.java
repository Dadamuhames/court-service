package com.uzumtech.court.dto.response;

import java.time.LocalDate;

public record GcpResponse(Long id, String fullName, String address, String phone, String email, String pinfl,
                          LocalDate dateOfBirth) {}
