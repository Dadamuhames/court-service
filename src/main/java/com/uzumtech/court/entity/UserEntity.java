package com.uzumtech.court.entity;

import com.uzumtech.court.entity.base.BaseEntity;
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
@Table(name = "users", indexes = @Index(columnList = "pinfl"))
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String pinfl;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Integer age;
}
