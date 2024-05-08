package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.OrdersRequest;
import org.website.thienan.ricewaterthienan.dto.response.OrdersResponse;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Orders;
import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrdersMapper {
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final OrderDetailMapper orderDetailMapper;

    public Orders orders(OrdersRequest ordersRequest){
        Orders orders = new Orders();
        if(ordersRequest.getId()!=null){
            orders.setId(ordersRequest.getId());
        }
        orders.setPhone(ordersRequest.getPhone());
        orders.setName(ordersRequest.getName());
        orders.setAddress(ordersRequest.getAddress());
        orders.setNotes(ordersRequest.getNotes()!=null ? ordersRequest.getNotes() : "Nothing");
        orders.setStatus(getOrderEnum(ordersRequest.getStatus()));
        Account account = accountRepository.findById(ordersRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not Found Order Id: " + ordersRequest.getAccountId()));
        orders.setAccount(account);
        orders.setActive(true);
        return  orders;

    }

    public OrdersResponse ordersResponse(Orders orders){
        return OrdersResponse.builder().id(orders.getId())
                .phone(orders.getPhone())
                .name(orders.getName())
                .address(orders.getAddress())
                .notes(orders.getNotes())
                .status(orders.getStatus().name())
                .active(orders.getActive())
                .createAt(orders.getCreateAt())
                .updateAt(orders.getUpdateAt())
                .accountResponse(accountMapper.accountResponse(orders.getAccount()))
                .orderdetailResponses(orders.getOrderDetails().stream().map(e -> orderDetailMapper.orderdetailResponse(e)).collect(Collectors.toList()))
                .build();
    }

    private StatusOrderEnum getOrderEnum(String status) {
        return switch (status) {
            case "WaitConfirm" -> StatusOrderEnum.WaitConfirm;
            case "Delivery" -> StatusOrderEnum.Delivery;
            case "Completed" -> StatusOrderEnum.Completed;
            default -> null;
        };
    }
}
