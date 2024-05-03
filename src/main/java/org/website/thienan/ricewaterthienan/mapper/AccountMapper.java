package org.website.thienan.ricewaterthienan.mapper;


import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;

import java.util.HashSet;
import java.util.Set;

public class AccountMapper {
    public Account account(AccountRequest accountRequest){
        Account account = new Account();
        account.setAvatar(accountRequest.getAvatar());
        account.setEmail(accountRequest.getEmail());
        account.setName(accountRequest.getName());
        account.setPassword(accountRequest.getPassword());
        account.setViews(accountRequest.getViews());
        account.setRole(accountRequest.getRole());

        Set<RoleDetail> roleDetailSet = new HashSet<>();
        return null;
    }
}
