package org.website.thienan.ricewaterthienan.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.AccountResponse;
import org.website.thienan.ricewaterthienan.dto.response.RoleDetailResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.Impl.RoleDetailServiceImpl;


import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final RoleDetailServiceImpl roleDetailService;
    private final RoleDetailMapper roleDetailMapper;
    public Account account(AccountRequest accountRequest){
        Account account = new Account();
        account.setAvatar(accountRequest.getAvatar());
        account.setEmail(accountRequest.getEmail());
        account.setName(accountRequest.getName());
        account.setPassword(accountRequest.getPassword());
        account.setViews(1L);
        account.setRole(accountRequest.getRole());

        Set<RoleDetail> roleDetailSet = new HashSet<>();
        accountRequest.getRoleDetail().forEach(e -> {
            RoleDetailResponse roleDetailResponse = roleDetailService.findByName(e).orElseThrow(() -> new ResourceNotFoundException("RoleDetail Notfound "));
            roleDetailSet.add(roleDetailMapper.roleDetail(roleDetailResponse));
        });
        account.setRoles(roleDetailSet);
        return  account;

    }

    public AccountResponse accountResponse(Account account){
        Set<RoleDetailResponse> roleDetailResponses = new HashSet<>();
        account.getRoles().forEach(e -> {
            roleDetailResponses.add(RoleDetailResponse.builder().id(e.getId()).name(e.getName()).build());
        });
        return AccountResponse.builder()
                .id(account.getId())
                .avatar(account.getAvatar())
                .email(account.getEmail())
                .name(account.getName())
                .views(account.getViews())
                .role(account.getRole().name())
                .roleDetails(roleDetailResponses).build();

    }
}
