package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.RoleDetailRequest;
import org.website.thienan.ricewaterthienan.dto.response.RoleDetailResponse;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.RoleDetailMapper;
import org.website.thienan.ricewaterthienan.repositories.RoleDetailRepository;
import org.website.thienan.ricewaterthienan.services.RoleDetailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class RoleDetailServiceImpl implements RoleDetailService<RoleDetailRequest, RoleDetailResponse> {
    private final RoleDetailRepository roleDetailRepository;
    private final RoleDetailMapper roleDetailMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "roleDetails", allEntries = true),
                    @CacheEvict(cacheNames = "roleDetailsActive", allEntries = true)
            }
    )
    public RoleDetailResponse save(RoleDetailRequest roleDetail) {
        RoleDetail roleDetail1 = roleDetailMapper.roleDetail(roleDetail);
        return  roleDetailMapper.roleDetailResponse(roleDetailRepository.save(roleDetail1));
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
    public RoleDetailResponse update(RoleDetailRequest roleDetail) {
        RoleDetail roleDetail1 = roleDetailMapper.roleDetail(roleDetail);
        return  roleDetailMapper.roleDetailResponse(roleDetailRepository.save(roleDetail1));
    }

    @Override
    @Cacheable(cacheNames = "roleDetail", key = "#id", unless = "#result==null")
    public Optional<RoleDetailResponse> findById(Integer id) {
        RoleDetail roleDetail = roleDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RoleDetail ID Not Found"));
        return Optional.of(roleDetailMapper.roleDetailResponse(roleDetail));
    }

    @Override
    @Cacheable(cacheNames = "roleDetailName", key = "#name", unless = "#result==null")
    public Optional<RoleDetailResponse> findByName(String name) {
        RoleDetail roleDetail = roleDetailRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("RoleDetail Name Not Found"));
        return Optional.of(roleDetailMapper.roleDetailResponse(roleDetail));
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
    public List<RoleDetailResponse> findAll() {
        return  roleDetailRepository.findAll().stream().map(e -> {
            RoleDetailResponse roleDetailResponse = roleDetailMapper.roleDetailResponse(e);
            return roleDetailResponse;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "roleDetailsActive")
    public List<RoleDetailResponse> findByActive(Boolean active) {
        return  roleDetailRepository.findByActive(active).stream().map(e -> {
            RoleDetailResponse roleDetailResponse = roleDetailMapper.roleDetailResponse(e);
            return roleDetailResponse;
        }).collect(Collectors.toList());
    }
}
