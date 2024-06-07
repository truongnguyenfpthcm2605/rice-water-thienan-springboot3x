package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.request.OrdersDetailRequest;
import org.website.thienan.ricewaterthienan.dto.request.OrdersRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.OrderDetail;
import org.website.thienan.ricewaterthienan.entities.Orders;
import org.website.thienan.ricewaterthienan.enums.StatusOrderEnum;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.AccountServices;
import org.website.thienan.ricewaterthienan.services.OrderDetailService;
import org.website.thienan.ricewaterthienan.services.OrdersService;
import org.website.thienan.ricewaterthienan.services.ProductService;
import org.website.thienan.ricewaterthienan.utils.SortAndPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    private final AccountServices accountServices;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;

    @GetMapping("/orders/findAllActive/{page}/{sort}")
    public ResponseEntity<MessageResponse> findAllActive(@RequestParam("active") Boolean active,
                                                         @PathVariable("page") Optional<Integer> page,
                                                         @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(ordersService.findByActive(
                        SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                sort != null && sort.equals("up") ?
                                        SortAndPage.getSortUp("createAt") :
                                        SortAndPage.getSort("createAt")), active
                )
                ).build(), HttpStatus.OK);
    }
    @GetMapping("/orders/findByKeyWord/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByKeyWord(@RequestParam("keyword") String  keyword,
                                                         @PathVariable("page") Optional<Integer> page,
                                                         @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(ordersService.findByKeyword(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort != null && sort.equals("up") ?
                                                SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")), keyword
                        )
                ).build(), HttpStatus.OK);
    }
    @GetMapping("/orders/findByStatus/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByStatus(@RequestParam("status") String  status,
                                                         @PathVariable("page") Optional<Integer> page,
                                                         @PathVariable("sort") String sort) {
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(ordersService.findByStatus(
                                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                                        sort != null && sort.equals("up") ?
                                                SortAndPage.getSortUp("createAt") :
                                                SortAndPage.getSort("createAt")), status
                        )
                ).build(), HttpStatus.OK);
    }

    @GetMapping("/orders/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") String id){
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID "+ id));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @GetMapping("/orders/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@PathVariable("name") String name){
        Orders orders = ordersService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Orders Phone "+ name));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Name Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @GetMapping("/orders/findByPhone/{phone}")
    public ResponseEntity<MessageResponse> findByPhone(@PathVariable("phone") String phone){
        Orders orders = ordersService.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("Not found Orders Phone "+ phone));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Phone Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @PostMapping("/orders/save")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> save(
            @RequestBody OrdersRequest ordersRequest,
            @RequestBody List<OrdersDetailRequest> ordersDetailRequests){
        // save orders
        Orders orders = new Orders();
        orders.setPhone(ordersRequest.getPhone());
        orders.setName(ordersRequest.getName());
        orders.setAddress(ordersRequest.getAddress());
        orders.setStatus(getStatusOrderEnum(ordersRequest.getStatus()));
        orders.setNotes(ordersRequest.getNotes());
        orders.setAccount(accountServices.findById(ordersRequest.getAccountId()).orElseThrow());
        orders.setActive(true);
        Orders ordersSave = ordersService.save(orders);

        // orders Detail
        List<OrderDetail> orderDetails = new ArrayList<>();
        ordersDetailRequests.forEach(e -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(e.getPrice());
            orderDetail.setQuantity(e.getQuantity());
            orderDetail.setOrder(ordersSave);
            orderDetail.setProduct(productService.findById(e.getProductId()).orElseThrow());
            orderDetails.add(orderDetail);
        });

        orderDetailService.saveAll(orderDetails);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save orders Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @PutMapping("/orders/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Staff','User')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") String id, @RequestBody OrdersRequest OrdersRequest){
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID"+ id));
        orders.setPhone(OrdersRequest.getPhone());
        orders.setName(OrdersRequest.getName());
        orders.setAddress(OrdersRequest.getAddress());
        orders.setStatus(getStatusOrderEnum(OrdersRequest.getStatus()));
        orders.setNotes(OrdersRequest.getNotes());
        orders.setUpdateAt(LocalDateTime.now());
        Orders ordersSave = ordersService.save(orders);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Update orders Success!")
                .data(ordersSave.getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/orders/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String id){
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID"+ id));
        if(orders.getStatus().compareTo(StatusOrderEnum.Completed) > 0){
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(405)
                    .timeStamp(LocalDateTime.now())
                    .message("Cannot Cancel Orders , Because orders is completed")
                    .data(orders.getStatus()).build(), HttpStatus.OK);
        }
        orders.setActive(false);
        orders.setStatus(StatusOrderEnum.Cancel);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Cancel orders Success!")
                .data(ordersService.update(orders).getStatus()).build(), HttpStatus.OK);
    }


    private StatusOrderEnum getStatusOrderEnum(String status){
        return switch (status) {
            case "Delivery" -> StatusOrderEnum.Delivery;
            case "Completed" -> StatusOrderEnum.Completed;
            case "Cancel" -> StatusOrderEnum.Cancel;
            default -> StatusOrderEnum.WaitConfirm;
        };
    }

}
