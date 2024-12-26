package ma.ecommerce.exceptions;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Getter
public abstract class AbstractBusinessException extends RuntimeException implements Describable {


    @Autowired
    private MessageSource messageSource;

    private final String code;


    protected AbstractBusinessException(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

}