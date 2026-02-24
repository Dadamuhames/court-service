package com.uzumtech.court.entity;

import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offenses",
    indexes = {@Index(columnList = "externalId"), @Index(columnList = "courtCaseNumber"), @Index(columnList = "user_id"), @Index(columnList = "judge_id")})
public class OffenseEntity extends BaseEntity {

    @Column(nullable = false)
    private Long externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judge_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_judge"))
    private JudgeEntity judge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "external_service_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_penalty_service"))
    private ExternalServiceEntity externalService;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private OffenseStatus status;

    @Column(nullable = false, updatable = false)
    private String offenseLocation;

    private String offenderExplanation;

    @Column(nullable = false, updatable = false)
    private String description;

    @Column(nullable = false, unique = true, updatable = false)
    private String protocolNumber;

    @Column(nullable = false, unique = true, updatable = false)
    private String courtCaseNumber;

    @Column(nullable = false)
    private String codeArticleReference;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime offenseDateTime;

    @OneToOne(mappedBy = "offense", fetch = FetchType.LAZY)
    private PenaltyEntity penalty;
}