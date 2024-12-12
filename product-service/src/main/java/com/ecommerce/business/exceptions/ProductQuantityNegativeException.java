package com.ecommerce.business.exceptions;

public class ProductQuantityNegativeException extends RuntimeException {
    public ProductQuantityNegativeException(String message) {
        super(message);
    }
}