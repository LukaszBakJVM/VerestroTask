package org.example.verestrotask.client.dto;

public record ClientRegistrationResponse(String username, String phoneNumber, String email,
                                         String channelNotification) {
}
