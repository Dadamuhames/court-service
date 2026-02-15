package com.uzumtech.court.entity;

import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.entity.base.BaseDeactivatableEntity;
import com.uzumtech.court.entity.base.CustomUserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "judges", indexes = {@Index(columnList = "email")})
public class JudgeEntity extends BaseDeactivatableEntity implements CustomUserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Override
    public Role getUserRole() {
        return Role.JUDGE;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
