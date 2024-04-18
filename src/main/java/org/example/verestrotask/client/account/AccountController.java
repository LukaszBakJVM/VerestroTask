package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.example.verestrotask.client.account.dto.Transfer;
import org.example.verestrotask.client.account.dto.TransferResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    AccountResponseDto setAccount(@RequestBody AccountDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerUsername = authentication.getName();
        return accountService.setAccount(dto, ownerUsername);


    }

    @GetMapping("/promotions")
    List<String> promotions() {
        return accountService.promotions();
    }

    @PostMapping("/transfer")
    TransferResponse bankTransfer(@RequestBody Transfer transfer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerUsername = authentication.getName();
        return accountService.send(transfer, ownerUsername);


    }


}
