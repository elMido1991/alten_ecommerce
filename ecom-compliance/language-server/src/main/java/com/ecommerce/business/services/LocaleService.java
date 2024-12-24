package com.ecommerce.business.services;

import com.ecommerce.business.entities.UserLocale;
import com.ecommerce.business.repositories.UserLocaleRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
public class LocaleService {

    private final MessageSource messageSource;
    private final UserLocaleRepository userLocaleRepository;
    private static final Locale locale = Locale.FRENCH;

    public LocaleService(MessageSource messageSource, UserLocaleRepository userLocaleRepository) {
        this.messageSource = messageSource;
        this.userLocaleRepository = userLocaleRepository;
    }


    public Mono<UserLocale> getUserLocaleById(Long id) {
        return userLocaleRepository.findById(id);
    }

    public Mono<UserLocale> saveUserLocale(Long userId, Locale locale) {
        UserLocale userLocal = new UserLocale(userId, locale);
        return userLocaleRepository.save(userLocal);
    }


    public Mono<String> getResourceBundle(Long userId, String msgCode) {
        return userLocaleRepository.findById(userId)
                .map(UserLocale::getLocale)  // Extract the locale from the user locale
                .defaultIfEmpty(locale)  // If the user has no locale, fallback to the default locale
                .flatMap(usrLocale -> {
                    // Use the locale to get the message
                    return Mono.just(messageSource.getMessage(msgCode, null, usrLocale));
                });
    }
}