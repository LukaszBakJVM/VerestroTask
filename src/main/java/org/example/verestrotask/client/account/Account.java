package org.example.verestrotask.client.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigInteger identifier;
    private BigDecimal balance;
    private int dayLimit;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigInteger getIdentifier() {
        return identifier;
    }

    public void setIdentifier(BigInteger identifier) {
        this.identifier = identifier;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public int getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(int dayLimit) {
        this.dayLimit = dayLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && dayLimit == account.dayLimit && Objects.equals(identifier, account.identifier) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identifier, balance, dayLimit);
    }
}
