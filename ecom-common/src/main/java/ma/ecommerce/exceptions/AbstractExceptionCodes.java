package ma.ecommerce.exceptions;

import lombok.Getter;

@Getter
public enum AbstractExceptionCodes implements Describable{
    ;

    private final String code;
    private final String message;

    AbstractExceptionCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getDescription() {
        return message;
    }
}
