package org.example.verestrotask.client.account.dto;

import java.math.BigDecimal;

public record AccountResponseDto(int identifier, BigDecimal balance, int dayLimit) {
}
