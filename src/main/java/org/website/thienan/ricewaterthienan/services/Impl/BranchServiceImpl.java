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
import org.website.thienan.ricewaterthienan.entities.Branch;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.repositories.BranchRepository;
import org.website.thienan.ricewaterthienan.services.BranchService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class BranchServiceImpl implements BranchService{
    private final BranchRepository branchRepository;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "branchs", allEntries = true),
                    @CacheEvict(cacheNames = "branchsActive", allEntries = true)
            }
    )
    public Branch save(Branch branchRequest) {
        return branchRepository.save(branchRequest);
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
    public Branch update(Branch branchRequest) {
        return  branchRepository.save(branchRequest);
    }

    @Override
    @Cacheable(cacheNames = "branch", key = "#id", unless = "#result==null")
    public Optional<Branch> findById(Integer id) {
        Branch branch = branchRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found Branch"));
        return Optional.of(branch);
    }

    @Override
    @Cacheable(cacheNames = "branch", key = "#name", unless = "#result==null")
    public Optional<Branch> findByName(String name) {
        Branch branch = branchRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Not found Branch"));
        return Optional.of(branch);
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
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "branchsActive")
    public List<Branch> findByActive(Boolean active) {
        return branchRepository.findByActive(active);
    }
}
