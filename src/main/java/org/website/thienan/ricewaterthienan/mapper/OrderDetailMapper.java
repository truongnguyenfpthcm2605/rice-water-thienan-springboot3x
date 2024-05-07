package org.website.thienan.ricewaterthienan.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.response.OrderdetailResponse;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;

@Component
@RequiredArgsConstructor
public class OrderDetailMapper {
    private final  ProductMapper productMapper;
    private final OrdersMapper ordersMapper;
    public OrderdetailResponse orderdetailResponse(OrderDetail orderDetail){
        return OrderdetailResponse.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .productResponse(productMapper.productResponse(orderDetail.getProduct()))
                .ordersResponse(ordersMapper.ordersResponse(orderDetail.getOrder()))
                .build();
    }
}
