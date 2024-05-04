package org.website.thienan.ricewaterthienan.security.userprincal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.website.thienan.ricewaterthienan.entities.Account;

import java.util.Collection;
import java.util.List;

@Getter
public class AccountService implements UserDetails {
    private String email;
    private String password;
    private String name;
    private List<? extends GrantedAuthority> authorities;

    public AccountService(Account account) {
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.name = account.getName();
        this.authorities = List.of(new SimpleGrantedAuthority(account.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
