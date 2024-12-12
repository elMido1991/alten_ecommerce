package com.ecommerce.gui.dto;

import com.ecommerce.business.db.entity.Product;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WishListDto {
    private Long id;
    private String name;
    private Set<Product> products = new HashSet<>();
    private Long userId;
}
