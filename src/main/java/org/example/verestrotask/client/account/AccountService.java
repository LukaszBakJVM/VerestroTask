package org.example.verestrotask.client.account;

import org.example.verestrotask.client.Client;
import org.example.verestrotask.client.ClientRepository;
import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.example.verestrotask.exception.AccountExistException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final ClientRepository clientRepository;
    private final AccountMapper mapper;

    public AccountService(AccountRepository repository, ClientRepository clientRepository, AccountMapper mapper) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }
    AccountResponseDto setAccount(AccountDto dto,String username){
        Client client = clientRepository.findByUsername(username).orElseThrow();
        Account accountExist = client.getAccount();
        if (accountExist!=null){
            throw  new  AccountExistException("You can  have only 1 account");
        }
        Account account = mapper.dtoToEntity(dto);
        Account save = repository.save(account);
        client.setAccount(save);
        clientRepository.save(client);
        return mapper.entityToDto(save);
    }
    List<String>promotions(){
        return Arrays.stream(PromotionalCode.values()).map(PromotionalCode::name).toList();
    }
}
