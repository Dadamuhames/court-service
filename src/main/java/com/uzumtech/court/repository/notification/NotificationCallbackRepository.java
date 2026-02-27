package com.uzumtech.court.repository.notification;

import com.uzumtech.court.entity.notification.NotificationCallbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationCallbackRepository extends JpaRepository<NotificationCallbackEntity, Long> {

}
