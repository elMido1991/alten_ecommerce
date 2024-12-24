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

    @GetMapping("/usr/{userId}/locales")
    public Mono<UserLocale> getLocale(@PathVariable("id") Long idUser) {
        return localeService.getUserLocaleById(idUser);
    }

    @PatchMapping("/usr/{userId}/locales")
    public Mono<UserLocale> setLocale(@PathVariable("userId") Long userId, @RequestParam("locale") Locale locale) {
        return localeService.saveUserLocale(userId, locale);
    }

    @GetMapping("/usr/{userId}/locales/messages")
    public Mono<String> getMessageResource(@PathVariable("userId") Long userId, @RequestParam("msg_code") String msgCode) {
        return localeService.getResourceBundle(userId, msgCode);
    }
}