package ma.ecommerce.exceptions;

import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
public abstract class AbstractBusinessException extends RuntimeException {

    private final String code;

    protected AbstractBusinessException(AbstractExceptionCodes exceptionCodes, MessageSource messageSource) {
        super(messageSource.getMessage(exceptionCodes.getMessage(), null, LocaleContextHolder.getLocale()));
        this.code = exceptionCodes.getCode();
    }

}