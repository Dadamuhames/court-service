package com.uzumtech.court.component.adapter;

import com.uzumtech.court.dto.response.GcpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GcpAdapter {
    private final RestClient gcpRestClient;

    public GcpResponse fetchUserInfoByPinfl(final String pinfl) {
        String url = String.format("/users/by-pinfl/%s", pinfl);

        var response = gcpRestClient.get().uri(url).retrieve().toEntity(GcpResponse.class);

        return response.getBody();
    }
}
