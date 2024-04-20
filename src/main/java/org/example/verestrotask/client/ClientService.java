package org.example.verestrotask.client;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.example.verestrotask.client.dto.ClientLogin;
import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.example.verestrotask.exception.ClientExistException;
import org.example.verestrotask.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final LocalValidatorFactoryBean validation;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, LocalValidatorFactoryBean validation) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;

        this.validation = validation;
    }

    @Transactional
    ClientRegistrationResponse registration(ClientRegistration clientRegistration) {
        Client client = clientMapper.registrationDtoToEntity(clientRegistration);
        validationClient(client);
        Optional<Client> byUsername = clientRepository.findByUsername(client.getUsername());
        byUsername.ifPresent(p -> {
            throw new ClientExistException("User with  username " + client.getUsername() + " exist");
        });
        Client save = clientRepository.save(client);

        return clientMapper.registrationResponse(save);

    }

    public Optional<ClientLogin> findByUsername(String username) {
        return clientRepository.findByUsername(username).map(clientMapper::loginMapper);
    }

    List<String> PreferredNotificationChannel() {
        return Arrays.stream(PreferredNotificationChannel.values()).map(PreferredNotificationChannel::getCHANNEL_NOTIFICATION).toList();

    }

    private void validationClient(Client client) {
        Set<ConstraintViolation<Client>> violations = validation.validate(client);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation error occurred: ");
            for (ConstraintViolation<Client> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }

            throw new ValidationException(errorMessage.toString());

        }
    }
}


