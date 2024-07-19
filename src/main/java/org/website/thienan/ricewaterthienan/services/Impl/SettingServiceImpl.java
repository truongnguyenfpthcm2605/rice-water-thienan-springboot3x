package org.website.thienan.ricewaterthienan.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.entities.Setting;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.SettingRepository;
import org.website.thienan.ricewaterthienan.services.SettingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class SettingServiceImpl implements SettingService {
    private final SettingRepository settingRepository;

    @Override
    public Setting save(Setting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public Setting update(Setting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public Optional<Setting> findById(String id) {
        Setting setting = settingRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Setting ID : " + id));
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
