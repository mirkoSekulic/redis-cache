package com.caching.redis.service.implementation;

import com.caching.redis.domain.Example;
import com.caching.redis.error.exception.ExampleNotFoundException;
import com.caching.redis.error.exception.entity.EntityMustHaveIDException;
import com.caching.redis.error.exception.entity.NewEntityCannotHaveIDException;
import com.caching.redis.repository.ExampleRepository;
import com.caching.redis.service.ExampleService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExapmleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;
    private final static String EXAMPLE_SINGLE = "example-single";

    public ExapmleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Page<Example> findAll(Pageable pageable) {
        return exampleRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = EXAMPLE_SINGLE, key = "#id")
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() ->
                new ExampleNotFoundException(id));
    }

    @Override
    public Example create(Example example) {
        if(example.getId()!= null) {
            throw new NewEntityCannotHaveIDException("Example");
        }
        Example forInsert = new Example();
        BeanUtils.copyProperties(example, forInsert);
        forInsert.setCreatedDate(Instant.now());

        return exampleRepository.save(forInsert);
    }

    @Override
    @CachePut(value = EXAMPLE_SINGLE, key = "#example.id")
    public Example update(Example example) {
        if(example.getId() == null) {
            throw new EntityMustHaveIDException("Example");
        }

        Example fromDb = findById(example.getId());
        Example forUpdate = new Example();
        BeanUtils.copyProperties(example, forUpdate);
        forUpdate.setCreatedDate(fromDb.getCreatedDate());

        return exampleRepository.save(forUpdate);
    }

    @Override
    @CacheEvict(value = EXAMPLE_SINGLE, key = "#id")
    public void delete(Long id) {
        Example example = findById(id);
        exampleRepository.delete(example);
    }
}
