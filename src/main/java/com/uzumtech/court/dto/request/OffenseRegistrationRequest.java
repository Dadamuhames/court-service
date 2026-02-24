package com.uzumtech.court.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record OffenseRegistrationRequest(@NotNull(message = "legalOffenseId required") Long legalOffenseId,
                                         @NotBlank(message = "offenderPinfl required") String offenderPinfl,
                                         @NotBlank(message = "offenseLocation required") String offenseLocation,
                                         @NotBlank(message = "protocolNumber required") String protocolNumber,
                                         @NotBlank(message = "offenderExplanation required") String offenderExplanation,
                                         @NotBlank(message = "description required") String description,
                                         @NotNull OffsetDateTime offenseDateTime,
                                         @NotBlank(message = "codeArticleReference required") String codeArticleReference) {}
