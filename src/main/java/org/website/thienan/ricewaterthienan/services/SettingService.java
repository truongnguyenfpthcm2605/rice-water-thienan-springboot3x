package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Optional;

import org.website.thienan.ricewaterthienan.entities.Setting;

public interface SettingService {

    Setting save(Setting setting);

    Setting update(Setting setting);

    Optional<Setting> findById(String id);

    void deleteById(String id);

    List<Setting> findAll();
}
