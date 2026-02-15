package com.uzumtech.court.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JudgeLoginRequest(@NotBlank(message = "email required") String email,
                                @NotBlank(message = "password required") String password) {}
