package com.uzumtech.court.entity.notification;

import com.uzumtech.court.constant.enums.NotificationRequestStatus;
import com.uzumtech.court.constant.enums.NotificationType;
import com.uzumtech.court.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification_requests", indexes = {@Index(columnList = "requestId"), @Index(columnList = "notificationServiceId")})
public class NotificationRequestEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID requestId;

    @Column(unique = true)
    private Long notificationServiceId;

    @Column(nullable = false)
    private String notificationReceiver;

    @Column(nullable = false)
    private String notificationText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "notification_type")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "request_status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private NotificationRequestStatus requestStatus;
}
