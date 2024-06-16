package org.website.thienan.ricewaterthienan.controller.apiV1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.website.thienan.ricewaterthienan.controller.UrlApi;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.entities.Setting;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.services.SettingService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = UrlApi.API_V1)
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingRepository;

    @GetMapping("/setting/findAll")
    public ResponseEntity<MessageResponse> findAll(){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get All Setting")
                .data(settingRepository.findAll()).build(), HttpStatus.OK);
    }

    @GetMapping("/setting/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@PathVariable("id") String id){
        Setting setting = settingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Setting not found"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Get Setting")
                .data(setting).build(), HttpStatus.OK);
    }

    @PostMapping("/setting/save")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> save(@RequestBody Setting setting){
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save Setting")
                .data(settingRepository.save(setting).getId()).build(), HttpStatus.OK);
    }

    @PutMapping("/setting/update")
    @PreAuthorize("hasAnyRole('Admin','Staff')")
    public ResponseEntity<MessageResponse> update(@RequestBody Setting settingR){
        Setting setting = settingRepository.findById(settingR.getId()).orElseThrow(() -> new ResourceNotFoundException("Not found Setting"));
        setting.setUpdateAt(LocalDateTime.now());
        setting.setActive(settingR.getActive());
        setting.setCustom(settingR.getCustom());
        return new ResponseEntity<>(MessageResponse.builder()
                .code(200)
                .timeStamp(LocalDateTime.now())
                .message("Save Setting")
                .data(settingRepository.update(setting).getId()).build(), HttpStatus.OK);
    }


}
