package com.ecommerce.controllers;

import com.ecommerce.business.entities.UserLocale;
import com.ecommerce.business.services.LocaleService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
public class LocaleConfigController {

    private final LocaleService localeService;

    public LocaleConfigController(LocaleService localeService) {
        this.localeService = localeService;
    }

    @GetMapping("/usr/{user_id}/locales")
    public Mono<UserLocale> getLocale(@PathVariable("user_id") Long idUser) {
        return localeService.getUserLocaleById(idUser);
    }

    @PatchMapping("/usr/{user_id}/locales/{user_locale}")
    public Mono<UserLocale> updateLocale(@PathVariable("user_id") Long userId, @PathVariable("user_locale") Locale locale) {
        return localeService.saveUserLocale(userId, locale);
    }

    @PostMapping("/usr/{user_id}/locales/{user_locale}")
    public Mono<UserLocale> createLocale(@PathVariable("user_id") Long userId, @PathVariable("user_locale") Locale locale) {
        return localeService.saveUserLocale(userId, locale);
    }

    @GetMapping("/usr/{user_id}/locales/messages")
    public Mono<String> getMessageResource(@PathVariable("user_id") Long userId, @RequestParam("msg_code") String msgCode) {
        return localeService.getResourceBundle(userId, msgCode);
    }
}