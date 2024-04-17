package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    public AccountService(AccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    AccountResponseDto setAccount(AccountDto dto,String username){
        Account account = mapper.dtoToEntity(dto);
        Account save = repository.save(account);
        return mapper.entityToDto(save);
    }
}
