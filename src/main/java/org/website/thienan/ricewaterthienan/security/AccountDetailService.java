package org.website.thienan.ricewaterthienan.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {
    private final AccountServices accountServices;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountServices.findByEmailAndActive(username,true).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
        return new AccountService(account);
    }
}
