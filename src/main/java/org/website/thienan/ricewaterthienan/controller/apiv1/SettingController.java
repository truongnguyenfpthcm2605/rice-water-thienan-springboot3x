package org.website.thienan.ricewaterthienan.controller.apiv1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(name = "Setting Controller API")
@Slf4j
public class SettingController {
    private final SettingService settingRepository;

    @Operation(summary = "Find All Setting", description = "Find All Setting")
    @GetMapping("/setting/findAll")
    public ResponseEntity<MessageResponse> findAll(){
        log.info("Find All Setting");
        Iterable<Setting> settings = settingRepository.findAll();
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get All Setting")
                .data(settings).build(), HttpStatus.OK);
    }

    @Operation(summary = "Find Setting by ID", description = "Find Setting by ID")
    @GetMapping("/setting/findById/{id}")
    public ResponseEntity<MessageResponse> findById(@Valid @NotNull @PathVariable String id){
        log.info("Find Setting by ID");
        Setting setting = settingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Setting not found"));
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Get Setting")
                .data(setting).build(), HttpStatus.OK);
    }

    @Operation(summary = "Save setting", description = "Save new setting")
    @PostMapping("/setting/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody Setting setting){
        log.info("Save setting");
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save Setting")
                .data(settingRepository.save(setting)).build(), HttpStatus.OK);
    }

    @Operation(summary = "Update setting", description = "Update setting")
    @PutMapping("/setting/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<MessageResponse> update(@Valid @RequestBody Setting settingR){
        log.info("Update setting");
        Setting setting = settingRepository.findById(settingR.getId()).orElseThrow(() -> new ResourceNotFoundException("Not found Setting"));
        setting.setActive(settingR.getActive());
        setting.setCustom(settingR.getCustom());
        settingRepository.update(setting);
        return new ResponseEntity<>(MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message("Save Setting")
                .data("success").build(), HttpStatus.OK);
    }


}
