package org.website.thienan.ricewaterthienan.services;

import org.website.thienan.ricewaterthienan.dto.request.SettingRequest;
import org.website.thienan.ricewaterthienan.entities.Setting;

import java.util.List;
import java.util.Optional;

public interface SettingService {

    Setting save(SettingRequest setting);
    Setting update(SettingRequest setting);

    Optional<Setting> findById(String id);
    void deleteById(String id);

    List<Setting> findAll();
}
