package com.ecommerce.business.exceptions;

public class ProductQuantityNegativeException extends BusinessException {
    public ProductQuantityNegativeException(ExceptionCodes exceptionCodes) {
        super(exceptionCodes);
    }
}