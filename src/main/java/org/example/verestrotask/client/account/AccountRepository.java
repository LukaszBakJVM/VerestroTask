package org.example.verestrotask.client.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdentifier(BigInteger identifier);

    List<Account> findAll();

}
