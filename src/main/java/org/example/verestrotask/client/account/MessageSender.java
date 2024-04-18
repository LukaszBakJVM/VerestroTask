package org.example.verestrotask.client.account;

import org.example.verestrotask.client.Client;
import org.example.verestrotask.client.account.dto.Transfer;

public interface MessageSender {
    void sendMessage(String preferredChannel, Client client, Transfer transfer, Account account);
}
