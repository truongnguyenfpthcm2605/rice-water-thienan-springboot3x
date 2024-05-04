package org.website.thienan.ricewaterthienan.security.userprincal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.AccountMapper;
import org.website.thienan.ricewaterthienan.services.Impl.AccountServiceImpl;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {
    private final AccountServiceImpl accountService;
    private final AccountMapper accountMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountResponse account = accountService.findByEmailAndActive(username,true).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
        return new AccountService(accountMapper.account(account));
    }
}
