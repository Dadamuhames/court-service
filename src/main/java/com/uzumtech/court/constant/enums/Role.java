package com.uzumtech.court.constant.enums;

import lombok.Getter;

@Getter
public enum Role {
    JUDGE("ROLE_JUDGE"), ADMIN("ROLE_ADMIN"), SERVICE("ROLE_SERVICE");

    final String role;

    Role(String role) {
        this.role = role;
    }
}
