package com.ecommerce.mappers;

import com.ecommerce.business.db.entity.User;
import com.ecommerce.gui.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {


    User toEntity(UserDto dto);
    UserDto toDto(User entity);
}