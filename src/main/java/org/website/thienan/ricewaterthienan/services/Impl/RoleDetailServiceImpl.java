package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.RoleDetailRepository;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleDetailServiceImpl implements RoleDetailService {
    private final RoleDetailRepository roleDetailRepository;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "roleDetails", allEntries = true),
                    @CacheEvict(cacheNames = "roleDetailsActive", allEntries = true)
            }
    )
    public RoleDetail save(RoleDetail roleDetail) {
        return roleDetailRepository.save(roleDetail);
    }

    @Override

    @Caching(
            put = {
                    @CachePut(cacheNames = "roleDetail", key = "#RoleDetailRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "roleDetails", allEntries = true),
                    @CacheEvict(cacheNames = "roleDetailsActive", allEntries = true)
            }
    )
    public RoleDetail update(RoleDetail roleDetail) {
        return roleDetailRepository.save(roleDetail);
    }

    @Override
    @Cacheable(cacheNames = "roleDetail", key = "#id", unless = "#result==null")
    public Optional<RoleDetail> findById(Integer id) {
        RoleDetail roleDetail = roleDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RoleDetail ID Not Found"));
        return Optional.of(roleDetail);
    }

    @Override
    @Cacheable(cacheNames = "roleDetailName", key = "#name", unless = "#result==null")
    public Optional<RoleDetail> findByName(String name) {
        RoleDetail roleDetail = roleDetailRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("RoleDetail Name Not Found"));
        return Optional.of(roleDetail);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "roleDetail", key = "#id", allEntries = true),
                    @CacheEvict(cacheNames = "roleDetails", allEntries = true),
                    @CacheEvict(cacheNames = "roleDetailsActive", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        roleDetailRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "roleDetails")
    public List<RoleDetail> findAll() {
        return  roleDetailRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "roleDetailsActive")
    public List<RoleDetail> findByActive(Boolean active) {
        return  roleDetailRepository.findByActive(active);
    }
}
