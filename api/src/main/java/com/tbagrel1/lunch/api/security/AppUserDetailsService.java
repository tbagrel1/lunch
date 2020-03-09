package com.tbagrel1.lunch.api.security;

import com.tbagrel1.lunch.api.db.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }
}
