package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountMapper {

    Account dtoToEntity(AccountDto dto) {
        Account account = new Account();
        account.setIdentifier(identifier());
        BigDecimal bigDecimal = enumToBigDecimal(dto.balance());
        account.setBalance(bigDecimal);
        int DAT_LIMIT = 3;
        account.setDayLimit(DAT_LIMIT);
        return account;
    }

    AccountResponseDto entityToDto(Account account) {
        return new AccountResponseDto(account.getIdentifier(), account.getBalance(), account.getDayLimit());
    }

    private int identifier() {
        Random random = new Random();
        int part1 = random.nextInt(100000);
        int part2 = random.nextInt(100000);
        int part3 = random.nextInt(100000);
        int part4 = random.nextInt(100000);
        return Integer.parseInt(String.format("%05d%05d", part1, part2));
    }


    private BigDecimal enumToBigDecimal(Balance balance) {
        int balance1 = balance.getBALANCE();
        return BigDecimal.valueOf(balance1);
    }

}
