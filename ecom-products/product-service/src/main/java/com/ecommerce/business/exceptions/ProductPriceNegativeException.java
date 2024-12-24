package com.ecommerce.business.exceptions;

public class ProductPriceNegativeException extends BusinessException {
    public ProductPriceNegativeException(ExceptionCodes exceptionCodes) {
        super(exceptionCodes);
    }
}