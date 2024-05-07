package org.website.thienan.ricewaterthienan.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.website.thienan.ricewaterthienan.dto.request.TypeRequest;
import org.website.thienan.ricewaterthienan.dto.response.TypeResponse;
import org.website.thienan.ricewaterthienan.entities.Type;
import org.website.thienan.ricewaterthienan.exceptions.ResourceNotFoundException;
import org.website.thienan.ricewaterthienan.mapper.TypeMapper;
import org.website.thienan.ricewaterthienan.repositories.TypeRepository;
import org.website.thienan.ricewaterthienan.services.TypeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class TypeServiceImpl implements TypeService<TypeResponse,TypeRequest> {
    private  final TypeRepository typeRepository;
    private final TypeMapper typeMapper;
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "typeActive" ,allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle" , allEntries = true),
            }
    )
    public TypeResponse save(TypeRequest postRequest) {
        Type type = typeMapper.type(postRequest);
        return typeMapper.typeResponse(typeRepository.save(type));
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "type", key = "#TypeRequest.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "typeActive" ,allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle" , allEntries = true),
            }
    )
    public TypeResponse update(TypeRequest postRequest) {
        Type type = typeMapper.type(postRequest);
        return typeMapper.typeResponse(typeRepository.saveAndFlush(type));
    }

    @Override
    @Cacheable(cacheNames = "type", key="#id", unless = "#result==null")
    public Optional<TypeResponse> findById(Integer id) {
        Type type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Type not found with id " + id));
        return Optional.of(typeMapper.typeResponse(type));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "type" , key = "#id"),
                    @CacheEvict(cacheNames = "typeActive" ,allEntries = true),
                    @CacheEvict(cacheNames = "typeTitle" , allEntries = true),
            }
    )
    public void deleteById(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "typeActive")
    public Page<TypeResponse> findByActive(Pageable pageable, Boolean active) {
        Page<Type> typePage = typeRepository.findByActive(pageable, active);

        List<TypeResponse> typeResponses = typePage.getContent().stream()
                .map(post -> {
                    TypeResponse postResponse = typeMapper.typeResponse(post);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(typeResponses, pageable, typePage.getTotalElements());
    }

    @Override
    @Cacheable(cacheNames = "typeTitle")
    public Page<TypeResponse> findByTitle(Pageable pageable, String title, boolean Active) {
        Page<Type> typePage = typeRepository.findByTitle(pageable, "%"+title+"%", Active);

        List<TypeResponse> typeResponses = typePage.getContent().stream()
                .map(post -> {
                    TypeResponse postResponse = typeMapper.typeResponse(post);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(typeResponses, pageable, typePage.getTotalElements());
    }
}
