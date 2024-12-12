package com.ecommerce.business.exceptions;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ExceptionCodes exceptionCodes) {
        super(exceptionCodes);
    }
}