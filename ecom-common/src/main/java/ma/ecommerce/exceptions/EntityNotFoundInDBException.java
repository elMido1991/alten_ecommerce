package ma.ecommerce.exceptions;

public class EntityNotFoundInDBException extends RuntimeException {
    public EntityNotFoundInDBException(String message) {
        super(message);
    }
}
