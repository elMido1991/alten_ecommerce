package com.ecommerce.business.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductId implements Serializable {
    private Long basket;
    private Long product;
}