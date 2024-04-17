package org.example.verestrotask.client.account;

import org.example.verestrotask.client.account.dto.AccountDto;
import org.example.verestrotask.client.account.dto.AccountResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping
   ResponseEntity< AccountResponseDto> setAccount(@RequestBody AccountDto dto, @RequestParam String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        AccountResponseDto accountResponseDto = accountService.setAccount(dto,username);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(accountResponseDto).toUri();
        return ResponseEntity.created(uri).body(accountResponseDto);

    }
    @GetMapping("/promotions")
    List<String>promotions(){
        return accountService.promotions();
    }


}
