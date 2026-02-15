package com.uzumtech.court.dto.request.admin;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ExternalServiceCreateRequest(@NotBlank(message = "login required") String login,
                                           @NotBlank(message = "password") String password,
                                           @NotBlank(message = "passwordConfirm") String passwordConfirm,
                                           @NotBlank(message = "webhookUrl required") @URL(protocol = "https") String webhookUrl,
                                           @NotBlank(message = "webhookSecret required") String webhookSecret) {

    @AssertTrue(message = "passwords should match")
    private boolean isPasswordsMatch() {
        return this.password.matches(this.passwordConfirm);
    }

}
