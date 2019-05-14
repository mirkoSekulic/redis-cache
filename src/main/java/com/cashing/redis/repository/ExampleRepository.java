package com.cashing.redis.repository;

import com.cashing.redis.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
}
