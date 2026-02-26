package com.uzumtech.court.component.adapter;


import com.uzumtech.court.dto.request.notification.NotificationRequest;
import com.uzumtech.court.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NotificationAdapter {
    private final RestClient notificationRestClient;

    public NotificationResponse sendNotification(final NotificationRequest request) {
        var response = notificationRestClient.post().uri("/sending").body(request).retrieve().toEntity(NotificationResponse.class);

        return response.getBody();
    }
}
