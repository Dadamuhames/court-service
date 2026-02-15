package com.uzumtech.court.dto.request.admin;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record JudgeRequest(@NotBlank(message = "email required") String email,
                           @NotBlank(message = "fullName required") String fullName,
                           @NotBlank(message = "password required") String password,
                           @NotBlank(message = "passwordConfirm required") String passwordConfirm) {

    @AssertTrue(message = "passwords should match")
    private boolean isPasswordsMatch() {
        return this.password.matches(this.passwordConfirm);
    }
}
