package org.example.verestrotask.client.account.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public record AccountResponseDto(BigInteger identifier, BigDecimal balance, int dayLimit) {
}
