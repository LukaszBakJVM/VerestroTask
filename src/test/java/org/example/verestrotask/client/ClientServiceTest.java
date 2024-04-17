package org.example.verestrotask.client;

import org.example.verestrotask.client.dto.ClientRegistration;
import org.example.verestrotask.client.dto.ClientRegistrationResponse;
import org.example.verestrotask.exception.ClientExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void registration_NewClient_Success() {
        // Arrange
        ClientRegistration clientRegistration = new ClientRegistration("a","a","a","a","a");
        Client client = new Client();
        client.setUsername("user123");
        client.setPassword("pass");
        client.setPhoneNumber("123456789");
        client.setEmail("user@example.com");
        ClientRegistrationResponse expectedResponse = new ClientRegistrationResponse("a","a","a","a");

        when(clientMapper.registrationDtoToEntity(clientRegistration)).thenReturn(client);
        when(clientRepository.findByUsername(client.getUsername())).thenReturn(Optional.empty());
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.RegistrationResponse(client)).thenReturn(expectedResponse);

        // Act
        ClientRegistrationResponse actualResponse = clientService.registration(clientRegistration);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(clientMapper, times(1)).registrationDtoToEntity(clientRegistration);
        verify(clientRepository, times(1)).findByUsername(client.getUsername());
        verify(clientRepository, times(1)).save(client);
        verify(clientMapper, times(1)).RegistrationResponse(client);
    }

    @Test
    void registration_ExistingClient_ClientExistExceptionThrown() {
        // Arrange
        ClientRegistration clientRegistration = new ClientRegistration("a","a","a","a","a");
        Client client = new Client(/* dane klienta */);

        when(clientMapper.registrationDtoToEntity(clientRegistration)).thenReturn(client);
        when(clientRepository.findByUsername(client.getUsername())).thenReturn(Optional.of(client));

        // Act & Assert
        assertThrows(ClientExistException.class, () -> clientService.registration(clientRegistration));
        verify(clientMapper, times(1)).registrationDtoToEntity(clientRegistration);
        verify(clientRepository, times(1)).findByUsername(client.getUsername());
        verify(clientRepository, never()).save(client);
        verify(clientMapper, never()).RegistrationResponse(client);
    }

}