package com.uzumtech.court.entity;

import com.uzumtech.court.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offenses", indexes = {@Index(columnList = "externalId"), @Index(columnList = "courtCaseNumber"), @Index(columnList = "user_id")})
public class OffenseEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_service_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_penalty_service"))
    private ExternalServiceEntity externalService;

    @Column(nullable = false)
    private String offenseLocation;

    private String offenderExplanation;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true)
    private String protocolNumber;

    @Column(nullable = false, unique = true)
    private String courtCaseNumber;

    @Column(nullable = false)
    private Integer codeArticleNumber;

    @Column(nullable = false)
    private OffsetDateTime offenseDateTime;

    @OneToOne(mappedBy = "offense")
    private PenaltyEntity penalty;
}