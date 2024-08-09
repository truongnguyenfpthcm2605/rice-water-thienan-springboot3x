package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.SettingRequest;
import org.website.thienan.ricewaterthienan.entities.Setting;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.SettingRepository;
import org.website.thienan.ricewaterthienan.services.SettingService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {
    private final SettingRepository settingRepository;

    @Override
    public Setting save(SettingRequest setting) {
        return settingRepository.save(Setting.builder()
                .id(setting.getId())
                .custom(setting.getCustom())
                .active(Boolean.TRUE)
                .build());
    }

    @Override
    public Setting update(SettingRequest setting) {
        return settingRepository.save(Setting.builder()
                .id(setting.getId())
                .custom(setting.getCustom())
                .active(Boolean.TRUE)
                .build());
    }

    @Override
    public Optional<Setting> findById(String id) {
        Setting setting = settingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Setting ID : " + id));
        return Optional.of(setting);
    }

    @Override
    public void deleteById(String id) {
        settingRepository.deleteById(id);
    }

    @Override
    public List<Setting> findAll() {
        return settingRepository.findAll();
    }
}
