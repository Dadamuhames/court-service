package com.uzumtech.court.service.impl.notification;

import com.uzumtech.court.constant.enums.NotificationRequestStatus;
import com.uzumtech.court.dto.event.NotificationEvent;
import com.uzumtech.court.entity.notification.NotificationRequestEntity;
import com.uzumtech.court.repository.notification.NotificationRequestRepository;
import com.uzumtech.court.service.NotificationRequestStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationRequestStoreServiceImpl implements NotificationRequestStoreService {
    private final NotificationRequestRepository notificationRequestRepository;

    @Transactional
    public int claimForProcessing(final UUID requestId) {
        return notificationRequestRepository.claimForProcessing(requestId);
    }

    @Transactional
    public void createNotificationRequest(final NotificationEvent event) {
        NotificationRequestEntity entity = NotificationRequestEntity.builder().notificationType(event.type()).notificationText(event.text()).requestId(event.requestId()).requestStatus(NotificationRequestStatus.NEW).notificationReceiver(event.receiver()).build();

        notificationRequestRepository.save(entity);
    }

    @Transactional
    public void markAsDelivered(final UUID requestId, final Long notificationServiceId) {
        notificationRequestRepository.markAsDelivered(requestId, notificationServiceId);
    }

    @Transactional
    public void changeStatus(final UUID requestId, final NotificationRequestStatus status) {
        notificationRequestRepository.updateRequestStatus(requestId, status);
    }
}
