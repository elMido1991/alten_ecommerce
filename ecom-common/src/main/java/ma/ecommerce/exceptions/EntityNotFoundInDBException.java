package ma.ecommerce.exceptions;

public class EntityNotFoundInDBException extends AbstractBusinessException {

    public EntityNotFoundInDBException(String code) {
        super(code);
    }

}
