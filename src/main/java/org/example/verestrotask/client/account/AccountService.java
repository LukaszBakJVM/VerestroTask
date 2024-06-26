package org.example.verestrotask.client.account;

import jakarta.transaction.Transactional;
import org.example.verestrotask.client.Client;
import org.example.verestrotask.client.ClientRepository;
import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.example.verestrotask.client.account.dto.Transfer;
import org.example.verestrotask.client.account.dto.TransferResponse;
import org.example.verestrotask.exception.AccountExistException;
import org.example.verestrotask.exception.LimitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final AccountMapper mapper;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository, AccountMapper mapper) {

        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    @Transactional
    AccountResponseDto setAccount(AccountDto dto, String username) {
        Client client = clientRepository.findByUsername(username).orElseThrow();
        Account accountExist = client.getAccount();
        if (accountExist != null) {
            throw new AccountExistException("You can  have only 1 account");
        }
        Account account = mapper.dtoToEntity(dto);
        Account save = accountRepository.save(account);
        client.setAccount(save);
        clientRepository.save(client);
        return mapper.entityToDto(save);
    }

    List<String> promotions() {
        return Arrays.stream(PromotionalCode.values()).map(PromotionalCode::name).toList();
    }

    @Transactional
    TransferResponse send(Transfer transfer, String username) {
        Optional<Account> byIdentifier = accountRepository.findByIdentifier(transfer.identifier());
        if (byIdentifier.isEmpty()) {
            throw new AccountExistException("Account  " + transfer.identifier() + " does not exist");
        }
        Client client = clientRepository.findByUsername(username).orElseThrow();
        String channelNotification = client.getPreferredNotificationChannel().getCHANNEL_NOTIFICATION();
        Account account = client.getAccount();
        if (account.getDayLimit() < 1) {
            throw new LimitException("Operation limit exceeded");
        } else if (account.getBalance().compareTo(transfer.amount()) < 0) {
            throw new LimitException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(transfer.amount()));
        int countTransfer = 1;
        account.setDayLimit(account.getDayLimit() - countTransfer);
        Account transferAccount = byIdentifier.get();
        transferAccount.setBalance(transferAccount.getBalance().add(transfer.amount()));
        Account save = accountRepository.save(account);
        accountRepository.save(transferAccount);
        sendMessage(channelNotification, client, transfer, transferAccount);
        return new TransferResponse(save.getBalance());
    }

    @Scheduled(cron = "0 0 0 * * *")

    public void resetDayLimit() {
        List<Account> differentThan3 = accountRepository.findAll().stream().filter(account -> account.getDayLimit() != 3).toList();
        for (Account a : differentThan3) {
            int dayLimit = 3;
            a.setDayLimit(dayLimit);
            accountRepository.save(a);
        }


    }


    @Override
    public void sendMessage(String preferredChannel, Client client, Transfer transfer, Account account) {
        String content = "Your transfer in the amount of " + transfer.amount() + " to bank account " + account.getIdentifier() + " has been executed.";
        if (preferredChannel.equals("SMS")) {
            logger.info("Sending sms to phone number: {}, content: {}", client.getPhoneNumber(), content);
        } else {
            logger.info("Sending email to email address: {}, content: {}", client.getEmail(), content);

        }

    }
}
