package com.uzumtech.court.entity;

import com.uzumtech.court.entity.base.BaseDeactivatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "bhm_amounts")
public class BhmAmountEntity extends BaseDeactivatableEntity {

    @Column(nullable = false)
    private Long amount;
}
