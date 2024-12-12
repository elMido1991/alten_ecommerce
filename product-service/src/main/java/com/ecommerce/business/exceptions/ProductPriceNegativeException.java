package com.ecommerce.business.exceptions;

public class ProductPriceNegativeException extends RuntimeException {
    public ProductPriceNegativeException(String message) {
        super(message);
    }
}