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
import org.website.thienan.ricewaterthienan.dto.request.BranchRequest;
import org.website.thienan.ricewaterthienan.dto.response.BranchResponse;
import org.website.thienan.ricewaterthienan.entities.Branch;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.BranchMapper;
import org.website.thienan.ricewaterthienan.repositories.BranchRepository;
import org.website.thienan.ricewaterthienan.services.BranchService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class BranchServiceImpl implements BranchService<BranchResponse, BranchRequest> {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "branchs", allEntries = true),
                    @CacheEvict(cacheNames = "branchsActive", allEntries = true)
            }
    )
    public BranchResponse save(BranchRequest branchRequest) {
        Branch branch = branchMapper.branch(branchRequest);
        return branchMapper.branchResponse(branchRepository.save(branch));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "branch", key = "#BranchRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "branchs", allEntries = true),
                    @CacheEvict(cacheNames = "branchsActive", allEntries = true)
            }
    )
    public BranchResponse update(BranchRequest branchRequest) {
        Branch branch = branchMapper.branch(branchRequest);
        return branchMapper.branchResponse(branchRepository.save(branch));
    }

    @Override
    @Cacheable(cacheNames = "branch", key = "#id", unless = "#result==null")
    public Optional<BranchResponse> findById(Integer id) {
        Branch branch = branchRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found Branch"));
        return Optional.of(branchMapper.branchResponse(branch));
    }

    @Override
    @Cacheable(cacheNames = "branch", key = "#name", unless = "#result==null")
    public Optional<BranchResponse> findByName(String name) {
        Branch branch = branchRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Not found Branch"));
        return Optional.of(branchMapper.branchResponse(branch));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "branchs", allEntries = true),
                    @CacheEvict(cacheNames = "branchsActive", allEntries = true)
            }
    )
    public void deleteById(Integer id) {
        branchRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "branchs")
    public List<BranchResponse> findAll() {
        return branchRepository.findAll().stream().map( branch -> {
            BranchResponse branch1 = branchMapper.branchResponse(branch);
            return branch1;
        }).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "branchsActive")
    public List<BranchResponse> findByActive(Boolean active) {
        return branchRepository.findByActive(active).stream().map( branch -> {
            BranchResponse branch1 = branchMapper.branchResponse(branch);
            return branch1;
        }).collect(Collectors.toList());
    }
}
