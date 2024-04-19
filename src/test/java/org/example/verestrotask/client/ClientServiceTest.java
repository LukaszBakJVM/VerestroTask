package org.example.verestrotask.client;

import org.example.verestrotask.client.dto.ClientRegistration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
//@Testcontainers

public class ClientServiceTest {
    @Autowired
    private WebTestClient webTestClient;
    @Mock
    private ClientRepository clientRepository;
  /*  @LocalServerPort
    private static int port;
    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("verestrotask")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(port);
    @BeforeAll
    static void beforeAll() {
        mysqlContainer.start();
    }
    @AfterAll
    static void afterAll() {
        mysqlContainer.stop();
    }*/




    @Test
    void testRegistration_Success() {
        ClientRegistration clientRegistration = new ClientRegistration("username111", "password", "123456789", "ww@w", "Sms");
        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isOk().expectBody()
                .json(ResponseData.registrationSuccess());
    }

    @Test
    void testRegistration_ClientExists() {
        String existingUsername = "existingUsername11";

        when(clientRepository.findByUsername(existingUsername)).thenReturn(Optional.of(new Client()));

        ClientRegistration clientRegistration = new ClientRegistration(existingUsername, "password", "3456789", "ww@w", "Sms");

        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isEqualTo(409).expectBody()

                .jsonPath("$.message").isEqualTo("User with  login " + existingUsername + " exist");
    }

}