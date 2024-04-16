package org.example.verestrotask.client;

import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/registrarion")
    ResponseEntity<ClientRegistrationResponse> registration(@RequestBody ClientRegistration clientRegistration) {
        ClientRegistrationResponse registration = clientService.registration(clientRegistration);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(registration).toUri();
        return ResponseEntity.created(uri).body(registration);
    }

    @GetMapping("/chanelNotification")
    List<String> allChanel() {
        return clientService.PreferredNotificationChannel();
    }

}
