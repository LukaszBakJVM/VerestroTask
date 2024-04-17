package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountMapper {
    private final int DAT_LIMIT = 3;

    Account dtoToEntity(AccountDto dto) {
        Account account = new Account();
        account.setIdentifier(identifier());
        BigDecimal bigDecimal = enumToBigDecimal(dto.balance());
        account.setBalance(bigDecimal);
        account.setDayLimit(DAT_LIMIT);
        return account;
    }

    AccountResponseDto entityToDto(Account account) {
        return new AccountResponseDto(account.getIdentifier(), account.getBalance(), account.getDayLimit());
    }

    private String identifier() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 20);
    }


    private BigDecimal enumToBigDecimal(Balance balance) {
        int balance1 = balance.getBALANCE();
        return BigDecimal.valueOf(balance1);
    }
}
