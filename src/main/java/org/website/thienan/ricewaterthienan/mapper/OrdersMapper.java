package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.OrdersRequest;
import org.website.thienan.ricewaterthienan.entities.Account;
import org.website.thienan.ricewaterthienan.entities.Orders;
import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.AccountRepository;

@Component
@RequiredArgsConstructor
public class OrdersMapper {
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

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

    private StatusOrderEnum getOrderEnum(String status) {
        return switch (status) {
            case "WaitConfirm" -> StatusOrderEnum.WaitConfirm;
            case "Delivery" -> StatusOrderEnum.Delivery;
            case "Completed" -> StatusOrderEnum.Completed;
            default -> null;
        };
    }
}
