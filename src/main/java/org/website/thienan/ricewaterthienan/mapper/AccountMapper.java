package org.website.thienan.ricewaterthienan.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.*;
import org.website.thienan.ricewaterthienan.entities.*;
import org.website.thienan.ricewaterthienan.enums.RoleEnum;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.Impl.RoleDetailServiceImpl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final RoleDetailServiceImpl roleDetailService;
    private final RoleDetailMapper roleDetailMapper;
    private final BranchMapper branchMapper;
    private final BrandMapper brandMapper;
    private final ProductMapper productMapper;
    private final  OrdersMapper ordersMapper;
    private final PostMapper postMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    public Account account(AccountRequest accountRequest){
        Account account = new Account();
        if(accountRequest.getId() != null){
            account.setId(accountRequest.getId());
        }
        account.setAvatar(accountRequest.getAvatar());
        account.setEmail(accountRequest.getEmail());
        account.setName(accountRequest.getName());
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setViews(account.getViews());
        account.setRole(getRole(accountRequest.getRole()));

        Set<RoleDetail> roleDetailSet = new HashSet<>();
        accountRequest.getRoleDetail().forEach(e -> {
            RoleDetailResponse roleDetailResponse = roleDetailService.findByName(e).orElseThrow(() -> new ResourceNotFoundException("RoleDetail Notfound "));
            roleDetailSet.add(roleDetailMapper.roleDetail(roleDetailResponse));
        });
        account.setRoles(roleDetailSet);
        account.setActive(accountRequest.getActive());
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
                .roleDetails(roleDetailResponses)
                .active(account.getActive())
                .createAt(account.getCreateAt())
                .updateAt(account.getUpdateAt())
                .branches(getBranchResponses(account.getBranch()))
                .brands(getBrandResponses(account.getBrands()))
                .products(getProductResponses(account.getProducts()))
                .orders(getOrdersResponses(account.getOrders()))
                .posts(getPostResponses(account.getPosts()))
                .build();

    }

    public Account account(AccountResponse accountResponse){
        Account account = new Account();
        account.setEmail(accountResponse.getEmail());
        account.setName(accountResponse.getName());
        account.setPassword(accountResponse.getPassword());
        account.setRole(getRole(accountResponse.getRole()));
        return account;
    }

    private RoleEnum getRole(String role) {
        return switch (role) {
            case "Admin" -> RoleEnum.Admin;
            case "User" -> RoleEnum.User;
            case "Staff" -> RoleEnum.Staff;
            default -> null;
        };
    }

    private List<BranchResponse> getBranchResponses(List<Branch> branches){
        return branches.stream().map(e -> branchMapper.branchResponse(e)).collect(Collectors.toList());
    }

    private List<BrandResponse> getBrandResponses(List<Brand> brands){
        return  brands.stream().map(e -> brandMapper.brandResponse(e)).collect(Collectors.toList());
    }

    private List<ProductResponse> getProductResponses(List<Product> products){
        return  products.stream().map(e -> productMapper.productResponse(e)).collect(Collectors.toList());
    }

    private List<OrdersResponse> getOrdersResponses(List<Orders> orders){
        return  orders.stream().map(e -> ordersMapper.ordersResponse(e)).collect(Collectors.toList());
    }

    private List<PostResponse> getPostResponses(List<Post> posts){
        return  posts.stream().map(e -> postMapper.postResponse(e)).collect(Collectors.toList());
    }

}
