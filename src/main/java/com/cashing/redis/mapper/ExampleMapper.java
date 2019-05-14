package com.cashing.redis.mapper;

import com.cashing.redis.domain.Example;
import com.cashing.redis.dto.ExampleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ExampleMapper {
    ExampleDTO exampleToExampleDTO(Example example);

    Example exampleDTOToExample(ExampleDTO exampleDTO);
}
