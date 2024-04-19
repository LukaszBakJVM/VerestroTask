package org.example.verestrotask.client;


import org.example.verestrotask.client.dto.ClientRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientServiceTest {

    @Autowired
    private WebTestClient webTestClient;


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

}



