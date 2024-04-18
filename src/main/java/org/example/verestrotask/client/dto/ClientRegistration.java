package org.example.verestrotask.client.dto;

public record ClientRegistration(String username, String password, String phoneNumber, String email,
                                 String channelNotification) {
}
