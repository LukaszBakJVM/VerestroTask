package org.example.verestrotask.client.security;

import org.example.verestrotask.client.ClientService;
import org.example.verestrotask.client.dto.ClientLogin;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientService clientService;

    public CustomUserDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientService.findByUsername(username).map(this::createUserDetails).orElseThrow();
    }

    private UserDetails createUserDetails(ClientLogin login) {
        return User.builder().username(login.username()).password(login.password()).build();
    }
}
