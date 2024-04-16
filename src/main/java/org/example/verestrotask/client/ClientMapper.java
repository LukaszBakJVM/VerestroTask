package org.example.verestrotask.client;

import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ClientMapper {
    private final PasswordEncoder passwordEncoder;

    public ClientMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    Client registrationDtoToEntity(ClientRegistration clientRegistration) {
        Client client = new Client();
        client.setUsername(clientRegistration.username());
        String password = passwordEncoder.encode(clientRegistration.password());
        client.setPassword(password);
        client.setPhoneNumber(clientRegistration.phoneNumber());
        client.setEmail(clientRegistration.email());
        PreferredNotificationChannel preferredNotificationChannel = stringToEnum(clientRegistration.channelNotification());
        client.setPreferredNotificationChannel(preferredNotificationChannel);
        return client;
    }

    ClientRegistrationResponse RegistrationResponse(Client client) {
        String chanel = enumToString(client.getPreferredNotificationChannel());
        return new ClientRegistrationResponse(client.getUsername(), client.getPhoneNumber(), client.getEmail(), chanel);

    }

    private PreferredNotificationChannel stringToEnum(String s) {
        return Arrays.stream(PreferredNotificationChannel.values()).filter(ch -> ch.getChanelNotyfication().equals(s)).findAny().get();

    }

    private String enumToString(PreferredNotificationChannel channel) {
        return channel.getChanelNotyfication();
    }

}
