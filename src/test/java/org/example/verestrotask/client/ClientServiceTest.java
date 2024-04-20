package org.example.verestrotask.client;


import org.example.verestrotask.client.dto.ClientRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientServiceTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        Client user = new Client();
        user.setUsername("lukasz");
        user.setPassword(passwordEncoder.encode("lukasz"));
        clientRepository.save(user);
    }


    @Test
    void testRegistration_Success() {


        ClientRegistration clientRegistration = new ClientRegistration("username", "password", "123456789", "ww@w", "Sms");
        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isOk().expectBody().json(ResponseData.registrationSuccess());
    }

    @Test
    void testValidation() {

        ClientRegistration clientRegistration = new ClientRegistration("1", "password", "12345678", "www", "Sms");
        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isBadRequest();
    }

    @Test
    void testAuthenticatedRequest() {
        String username = "lukasz";
        String password = "lukasz";

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);

        webTestClient.get().uri("/account/promotions").headers(httpHeaders -> httpHeaders.putAll(headers)).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

    }

}



