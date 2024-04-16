package org.example.verestrotask.client;

import jakarta.transaction.Transactional;
import org.example.verestrotask.client.dto.ClientLogin;
import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.example.verestrotask.exception.ClientExistException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    ClientRegistrationResponse registration(ClientRegistration clientRegistration) {
        Client client = clientMapper.registrationDtoToEntity(clientRegistration);
        Optional<Client> byUsername = clientRepository.findByUsername(client.getUsername());
        byUsername.ifPresent(p -> {
            throw new ClientExistException("User with  login " + client.getUsername() + " exist");
        });
        Client save = clientRepository.save(client);
        return clientMapper.RegistrationResponse(save);

    }

    public Optional<ClientLogin> findByUsername(String username) {
        return clientRepository.findByUsername(username).map(clientMapper::loginMapper);
    }

    List<String> PreferredNotificationChannel() {
        return Arrays.stream(PreferredNotificationChannel.values()).map(PreferredNotificationChannel::getChanelNotyfication).toList();

    }

}
