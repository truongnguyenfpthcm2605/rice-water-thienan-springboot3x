package org.website.thienan.ricewaterthienan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.entities.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {
}
