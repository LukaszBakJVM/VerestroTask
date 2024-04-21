package org.example.verestrotask.client;


import org.example.verestrotask.client.account.Account;
import org.example.verestrotask.client.account.AccountRepository;
import org.example.verestrotask.client.dto.ClientRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientServiceTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @DirtiesContext
    void testRegistrationSuccess() {
        ClientRegistration clientRegistration = new ClientRegistration("username", "password", "123456789", "ww@w", "Sms");
        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isOk().expectBody().json(ResponseData.registrationSuccess());
    }

    @Test
    @DirtiesContext
    void testValidation() {

        ClientRegistration clientRegistration = new ClientRegistration("1", "password", "12345678", "www", "Sms");
        webTestClient.post().uri("/client/register").contentType(MediaType.APPLICATION_JSON).bodyValue(clientRegistration).exchange().expectStatus().isBadRequest();
    }

    @Test
    @DirtiesContext
    void testAccountCreation() {

        setupClientLogin();
        String username = "lukasz";
        String password = "lukasz";

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        String balance = "{\"balance\": \"KOD_2\"}";


        webTestClient.post().uri("/account").headers(httpHeaders -> {
            httpHeaders.putAll(headers);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).accept(MediaType.APPLICATION_JSON).bodyValue(balance).exchange().expectStatus().isOk().expectBody().jsonPath("$.identifier").isNumber().jsonPath("$.balance").isEqualTo(200).jsonPath("$.dayLimit").isEqualTo(3);
    }

    @Test
    @DirtiesContext
    void testSecondAccountFotClientCreation() {
        setupClientLoginAndAccount();
        String username = "lukasz";
        String password = "lukasz";

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        String balance = "{\"balance\": \"KOD_2\"}";
        String response = "{\"message\": \"You can  have only 1 account\"}";

        webTestClient.post().uri("/account").headers(httpHeaders -> {
            httpHeaders.putAll(headers);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).accept(MediaType.APPLICATION_JSON).bodyValue(balance).exchange().expectStatus().isEqualTo(409).expectBody().json(response);
    }

    @Test
    @DirtiesContext
    void testTransferOk() {

        setupClientLoginAndAccount();
        makeTransfer();

        String username = "lukasz";
        String password = "lukasz";
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);
        String balance = "{\"identifier\": \"12345678901234567890\", \"amount\": 10.00}";

        webTestClient.post().uri("/account/transfer").headers(httpHeaders -> {
            httpHeaders.putAll(headers);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).accept(MediaType.APPLICATION_JSON).bodyValue(balance).exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.accountBalance").isEqualTo(90.00);




        BigInteger accountIdentifier = new BigInteger("12345678901234567890");
        Account account = accountRepository.findByIdentifier(accountIdentifier).orElseThrow();
        BigDecimal accountBalanceAfterTransfer = account.getBalance();
        BigDecimal result = new BigDecimal("110.00");




        assertEquals(accountBalanceAfterTransfer, result);


    }

    @Test
    @DirtiesContext
    void testAccountNotExist() {
        setupClientLogin();
        String username = "lukasz";
        String password = "lukasz";
        String balance = "{\"identifier\": \"12345678901234567890\", \"amount\": 10}";
        String response = "{\"message\": \"Account  12345678901234567890 does not exist\"}";

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth);

        webTestClient.post().uri("/account/transfer").headers(httpHeaders -> {
            httpHeaders.putAll(headers);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).accept(MediaType.APPLICATION_JSON).bodyValue(balance).exchange().expectStatus().isEqualTo(409).expectBody().json(response);


    }


    private void setupClientLogin() {


        Client client = new Client();
        client.setUsername("lukasz");
        client.setPassword(passwordEncoder.encode("lukasz"));
        client.setPhoneNumber("987654321");
        client.setEmail("interia@pl");
        PreferredNotificationChannel preferredNotificationChannel = PreferredNotificationChannel.SMS;
        client.setPreferredNotificationChannel(preferredNotificationChannel);

        clientRepository.save(client);
    }

    private void setupClientLoginAndAccount() {
        BigDecimal balance = new BigDecimal("100.00");

        BigInteger accountIdentifier = new BigInteger("09876543210987654321");
        Account account = new Account();
        account.setIdentifier(accountIdentifier);
        account.setBalance(balance);
        account.setDayLimit(3);
        accountRepository.save(account);


        Client client = new Client();
        client.setUsername("lukasz");
        client.setPassword(passwordEncoder.encode("lukasz"));
        client.setPhoneNumber("987654321");
        client.setEmail("interia@pl");
        PreferredNotificationChannel preferredNotificationChannel = PreferredNotificationChannel.SMS;
        client.setPreferredNotificationChannel(preferredNotificationChannel);
        client.setAccount(account);
        clientRepository.save(client);
    }

    private void makeTransfer() {
        BigDecimal balance = new BigDecimal("100.00");
        BigInteger bigInteger = new BigInteger("12345678901234567890");
        Client client = new Client();
        Account account = new Account();
        account.setIdentifier(bigInteger);
        account.setBalance(balance);
        account.setDayLimit(3);
        accountRepository.save(account);
        client.setUsername("transfer");
        client.setPassword(passwordEncoder.encode("transfer"));
        client.setAccount(account);
        clientRepository.save(client);
        clientRepository.save(client);


    }

}



