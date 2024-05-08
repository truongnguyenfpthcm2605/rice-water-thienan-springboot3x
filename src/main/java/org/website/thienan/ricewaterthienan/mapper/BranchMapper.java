package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.BranchRequest;
import org.website.thienan.ricewaterthienan.dto.response.BranchResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Branch;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BranchMapper {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ProductMapper productMapper;
    public Branch branch(BranchRequest branchRequest){
        Branch branch = new Branch();
        if(branchRequest.getAccountId() != null){
            branch.setId(branchRequest.getId());
        }
        branch.setName(branchRequest.getName());
        branch.setViews(branchRequest.getViews());
        branch.setLink(branchRequest.getLink());
        branch.setActive(branchRequest.getActive());

        Account account = accountRepository.findById(branchRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Account - Branch Id: " + branchRequest.getAccountId()));
        branch.setAccount(account);
        return branch;
    }

    public BranchResponse branchResponse(Branch branch){
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .link(branch.getLink())
                .views(branch.getViews())
                .accountResponse(accountMapper.accountResponse(branch.getAccount()))
                .active(branch.getActive())
                .createAt(branch.getCreateAt())
                .updateAt(branch.getUpdateAt())
                .productResponses(branch.getProducts().stream().map(e -> productMapper.productResponse(e)).collect(Collectors.toList()))
                .build();
    }


}
