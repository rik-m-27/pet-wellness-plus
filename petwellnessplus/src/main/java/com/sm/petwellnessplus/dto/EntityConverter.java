package com.sm.petwellnessplus.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntityConverter<T, D> {

    private final ModelMapper modelmapper;

    public D mapEntityToDto(T entity, Class<D> dtoClass) {
        return modelmapper.map(entity, dtoClass);
    }

    public T mapDtoToEntity(D dto, Class<T> entityClass) {
        return modelmapper.map(dto, entityClass);
    }
}
