package org.example.verestrotask.client;

public enum PreferredNotificationChannel {


    SMS("Sms"),  EMAIL("Email");
    private final String CHANNEL_NOTIFICATION ;

    PreferredNotificationChannel(String CHANNEL_NOTIFICATION) {
        this.CHANNEL_NOTIFICATION = CHANNEL_NOTIFICATION;
    }

    public String getCHANNEL_NOTIFICATION() {
        return CHANNEL_NOTIFICATION;
    }
}
