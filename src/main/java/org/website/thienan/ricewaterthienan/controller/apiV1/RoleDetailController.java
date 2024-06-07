package org.website.thienan.ricewaterthienan.controller.apiv1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class RoleDetailController {
    private final RoleDetailService roleDetailService;

    @GetMapping("/role_detail/findAll")
    public ResponseEntity<MessageResponse> findAll(){
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Get All Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetailService.findAll()).build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/role_detail/findAllActive")
    public ResponseEntity<MessageResponse> findAll(@RequestParam("active") Boolean active){
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Get All Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetailService.findByActive(active)).build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/role_detail/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") Integer id){
        RoleDetail roleDetail = roleDetailService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found Role Detail"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Get Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetail).build(),
                HttpStatus.OK
        );

    }

    @GetMapping("/role_detail/findByName/{name}")
    public ResponseEntity<MessageResponse> findByName(@PathVariable("name") String name){
        RoleDetail roleDetail = roleDetailService.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Not Found Role Detail"));
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Get Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetail).build(),
                HttpStatus.OK
        );

    }

    @PostMapping("/role_detail/save")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> save(@RequestParam("name") String name){
        RoleDetail roleDetail = new RoleDetail();
        roleDetail.setName(name);
        roleDetail.setActive(true);
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Save Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetailService.save(roleDetail).getName()).build(),
                HttpStatus.OK
        );
    }
    @PutMapping("/role_detail/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") Integer id,@RequestParam("name") String name,@RequestParam("active") Boolean active){
        RoleDetail roleDetail = roleDetailService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Role Detail"));
        roleDetail.setName(name);
        roleDetail.setActive(active);
        roleDetail.setUpdateAt(LocalDateTime.now());
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .code(200)
                        .message("Update Role Detail")
                        .timeStamp(LocalDateTime.now())
                        .data(roleDetailService.save(roleDetail).getName()).build(),
                HttpStatus.OK
        );
    }

}
