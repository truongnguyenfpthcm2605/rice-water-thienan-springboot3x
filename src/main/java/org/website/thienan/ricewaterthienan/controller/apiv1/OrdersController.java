package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Tag(name = "Orders Controller API")
@Slf4j
public class OrdersController {
    private final OrdersService ordersService;
    private final AccountServices accountServices;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;

    @Operation(summary = "find All Page Orders", description = "Page Orders Active")
    @GetMapping("/orders/findAllActive/{page}/{sort}")
    public ResponseEntity<MessageResponse> findAllActive(@Valid @NotNull @RequestParam(defaultValue = "true", required = false) Boolean active,
                                                         @NotNull @PathVariable Optional<Integer> page,
                                                         @NotNull @PathVariable String sort) {
        log.info("findAllActive page: {}, sort: {}", page, sort);
        Page<Orders> pages = ordersService.findByActive(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                        sort != null && sort.equals("up") ?
                                SortAndPage.getSortUp("createAt") :
                                SortAndPage.getSort("createAt")), active);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(pages)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "find All Page Orders Keyword", description = "Page Orders Keyword")
    @GetMapping("/orders/findByKeyWord/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByKeyWord(@Valid @NotNull @RequestParam(required = false, defaultValue = "") String keyword,
                                                         @NotNull @PathVariable Optional<Integer> page,
                                                         @NotNull @PathVariable String sort) {
        log.info("findByKeyWord keyword: {}, page: {}, sort: {}", keyword, page, sort);
        Page<Orders> pages = ordersService.findByKeyword(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                        sort != null && sort.equals("up") ?
                                SortAndPage.getSortUp("createAt") :
                                SortAndPage.getSort("createAt")), keyword
        );
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(pages).build(), HttpStatus.OK);
    }

    @Operation(summary = "find All Page Orders Status", description = "Page Orders Status")
    @GetMapping("/orders/findByStatus/{page}/{sort}")
    public ResponseEntity<MessageResponse> findByStatus(@Valid @NotNull @RequestParam(defaultValue = "", required = false) String status,
                                                        @NotNull @PathVariable Optional<Integer> page,
                                                        @NotNull @PathVariable String sort) {
        log.info("findByStatus status: {}, page: {}, sort: {}", status, page, sort);
        Page<Orders> pages = ordersService.findByStatus(
                SortAndPage.getPage(page.orElse(0), SortAndPage.MAX_PAGE,
                        sort != null && sort.equals("up") ?
                                SortAndPage.getSortUp("createAt") :
                                SortAndPage.getSort("createAt")), status
        );
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Active Orders")
                .data(pages).build(), HttpStatus.OK);
    }

    @Operation(summary = "find Orders By ID", description = "find Orders By ID (String)")
    @GetMapping("/orders/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable String id) {
        log.info("findById: {}", id);
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID " + id));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @Operation(summary = "find Orders By Name", description = "find Orders By Name (String)")
    @GetMapping("/orders/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@Valid @NotNull @PathVariable String name) {
        log.info("findByName: {}", name);
        Orders orders = ordersService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not found Orders Phone " + name));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Name Success!")
                .data(orders).build(), HttpStatus.OK);
    }

    @Operation(summary = "find Orders By Phone", description = "find Orders By Phone (String)")
    @GetMapping("/orders/findByPhone/{phone}")
    public ResponseEntity<MessageResponse> findByPhone(@Valid @NotNull @PathVariable String phone) {
        log.info("findByPhone: {}", phone);
        Orders orders = ordersService.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("Not found Orders Phone " + phone));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Find Orders Phone Success!")
                .data(orders).build(), HttpStatus.OK);
    }
    @Operation(summary = "Save Orders", description = "Save new Orders")
    @PostMapping("/orders/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> save(
           @Valid @NotNull @RequestBody OrdersRequest ordersRequest,
           @NotNull @RequestBody List<OrdersDetailRequest> ordersDetailRequests) {
        log.info("save: {}", ordersRequest.getName());
        // save orders
        Orders orders = new Orders();
        orders.setPhone(ordersRequest.getPhone());
        orders.setName(ordersRequest.getName());
        orders.setAddress(ordersRequest.getAddress());
        orders.setStatus(ordersRequest.getStatus());
        orders.setNotes(ordersRequest.getNotes());
        orders.setAccount(accountServices.findById(ordersRequest.getAccountId()).orElseThrow(() -> new ResourceNotFoundException("Not found Account ID " + ordersRequest.getAccountId())));
        orders.setActive(true);
        Orders ordersSave = ordersService.save(orders);

        // orders Detail
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrdersDetailRequest e : ordersDetailRequests) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(e.getPrice());
            orderDetail.setQuantity(e.getQuantity());
            orderDetail.setOrder(ordersSave);
            orderDetail.setProduct(productService.findById(e.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Not found Product ID " + e.getProductId())));
            orderDetails.add(orderDetail);
        }
        orderDetailService.saveAll(orderDetails);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save orders Success!")
                .data(ordersSave.getName()).build(), HttpStatus.OK);
    }

    @Operation(summary = "Update Orders", description = "Update Orders by Id (String")
    @PutMapping("/orders/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','USER')")
    public ResponseEntity<MessageResponse> update( @Valid @NotNull @PathVariable String id, @RequestBody OrdersRequest OrdersRequest) {
        log.info("update: {}", OrdersRequest.getName());
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID" + id));
        orders.setPhone(OrdersRequest.getPhone());
        orders.setName(OrdersRequest.getName());
        orders.setAddress(OrdersRequest.getAddress());
        orders.setStatus(OrdersRequest.getStatus());
        orders.setNotes(OrdersRequest.getNotes());
        orders.setUpdateAt(LocalDateTime.now());
        Orders ordersSave = ordersService.save(orders);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Update orders Success!")
                .data(ordersSave.getName()).build(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Orders", description = "Delete Orders by Id (String")
    @DeleteMapping("/orders/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String id) {
        log.info("delete: {}", id);
        Orders orders = ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Orders ID" + id));
        if (orders.getStatus().compareTo(StatusOrderEnum.COMPLETED) > 0) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .timeStamp(LocalDateTime.now())
                    .message("Cannot Cancel Orders , Because orders is completed")
                    .data(orders.getStatus()).build(), HttpStatus.OK);
        }
        orders.setActive(false);
        orders.setStatus(StatusOrderEnum.CANCEL);
        ordersService.update(orders);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Cancel orders Success!")
                .data(orders.getStatus()).build(), HttpStatus.OK);
    }


}
