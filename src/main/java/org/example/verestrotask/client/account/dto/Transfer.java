package org.example.verestrotask.client.account.dto;

import java.math.BigDecimal;

public record Transfer(String identifier, BigDecimal amount) {
}
