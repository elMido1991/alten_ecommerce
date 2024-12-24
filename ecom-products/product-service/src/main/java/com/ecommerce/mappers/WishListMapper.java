package com.ecommerce.mappers;

import com.ecommerce.business.db.entity.Wishlist;
import com.ecommerce.gui.dto.UserDto;
import com.ecommerce.gui.dto.WishListDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WishListMapper {


    Wishlist toEntity(WishListDto dto);
    WishListDto toDto(Wishlist entity);
}