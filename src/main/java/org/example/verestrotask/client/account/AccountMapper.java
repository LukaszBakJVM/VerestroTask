package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private BigInteger identifier() {
        Random random = new Random();
        String collect = IntStream.range(0, 20).mapToObj(i -> Integer.toString(random.nextInt(10))).collect(Collectors.joining());
        return new BigInteger(collect);
    }


    private BigDecimal enumToBigDecimal(Balance balance) {
        int balance1 = balance.getBALANCE();
        return BigDecimal.valueOf(balance1);
    }

}
