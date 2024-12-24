package com.ecommerce.business.repositories;

import com.ecommerce.business.entities.UserLocale;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserLocaleRepository extends ReactiveCrudRepository<UserLocale, Long> {
    Mono<UserLocale> findByUserId(Long userId);
    Mono<UserLocale> save(UserLocale userLocale);
    Mono<Void> deleteByUserId(Long userId);
}
