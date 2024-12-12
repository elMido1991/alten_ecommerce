package com.ecommerce.business.exceptions;

public class ProductRatingOutOfRangeException extends RuntimeException {
    public ProductRatingOutOfRangeException(String message) {
        super(message);
    }
}