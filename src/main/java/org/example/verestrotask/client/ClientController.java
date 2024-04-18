package org.example.verestrotask.client;

import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    ClientRegistrationResponse registration(@RequestBody ClientRegistration clientRegistration) {
        return clientService.registration(clientRegistration);

    }

    @GetMapping("/chanelNotification")
    List<String> allChanel() {
        return clientService.PreferredNotificationChannel();
    }

}
