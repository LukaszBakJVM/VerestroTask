package org.example.verestrotask.client;

import jakarta.transaction.Transactional;
import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.springframework.stereotype.Service;

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
    ClientRegistrationResponse registration(ClientRegistration clientRegistration){
        Client client = clientMapper.registrationDtoToEntity(clientRegistration);
        Client save = clientRepository.save(client);
        return clientMapper.RegistrationResponse(save);

    }
  public   Optional<ClientRegistrationResponse>findByUsername(String username){
        return clientRepository.findByUsername(username).map(client -> clientMapper.RegistrationResponse(client));
    }

}
