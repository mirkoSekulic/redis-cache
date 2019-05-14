package com.cashing.redis.service.implementation;

import com.cashing.redis.domain.Example;
import com.cashing.redis.error.exception.ExampleNotFoundException;
import com.cashing.redis.error.exception.entity.EntityMustHaveIDException;
import com.cashing.redis.error.exception.entity.NewEntityCannotHaveIDException;
import com.cashing.redis.repository.ExampleRepository;
import com.cashing.redis.service.ExampleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExapmleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;

    public ExapmleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Page<Example> findAll(Pageable pageable) {
        return exampleRepository.findAll(pageable);
    }

    @Override
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
    public void delete(Long id) {
        Example example = findById(id);
        exampleRepository.delete(example);
    }
}
