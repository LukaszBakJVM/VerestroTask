package org.example.verestrotask.client;

public enum PreferredNotificationChannel {


    SMS("Sms"),EMAIL("Email");
    private final String channelNotification;

    PreferredNotificationChannel(String chanelNotyfication) {
        this.channelNotification = chanelNotyfication;
    }

    public String getChanelNotyfication() {
        return channelNotification;
    }

}
