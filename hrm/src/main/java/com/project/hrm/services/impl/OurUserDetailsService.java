package com.project.hrm.services.impl;

import com.project.hrm.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OurUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Account not found with username: " + username));
    }
}
