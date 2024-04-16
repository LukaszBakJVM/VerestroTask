package org.example.verestrotask.client.account;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
   private String identifier;
   private BigDecimal balance;
   @Enumerated(EnumType.STRING)
    private PromotionalCode promotionalCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public PromotionalCode getPromotionalCode() {
        return promotionalCode;
    }

    public void setPromotionalCode(PromotionalCode promotionalCode) {
        this.promotionalCode = promotionalCode;
    }
}
