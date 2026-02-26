package com.uzumtech.court.service.impl.notification;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.notification.NotificationCallbackRequest;
import com.uzumtech.court.entity.notification.NotificationCallbackEntity;
import com.uzumtech.court.entity.notification.NotificationRequestEntity;
import com.uzumtech.court.exception.NotificationIdInvalidException;
import com.uzumtech.court.mapper.NotificationMapper;
import com.uzumtech.court.repository.notification.NotificationCallbackRepository;
import com.uzumtech.court.repository.notification.NotificationRequestRepository;
import com.uzumtech.court.service.NotificationCallbackStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationCallbackStoreServiceImpl implements NotificationCallbackStoreService {

    private final NotificationCallbackRepository callbackRepository;
    private final NotificationRequestRepository notificationRequestRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void receiveNotificationWebhook(NotificationCallbackRequest callbackRequest) {

        NotificationRequestEntity notificationRequest = getRequestByNotificationId(callbackRequest.content().notificationId());

        NotificationCallbackEntity callbackEntity = notificationMapper.requestToEntity(callbackRequest, notificationRequest);

        callbackRepository.save(callbackEntity);
    }


    private NotificationRequestEntity getRequestByNotificationId(Long notificationId) {
        return notificationRequestRepository.findByNotificationServiceId(notificationId).orElseThrow(() -> new NotificationIdInvalidException(ErrorCode.NOTIFICATION_ID_INVALID_CODE));
    }
}
