package com.ecommerce.business.repositories;

import com.ecommerce.business.entities.UserLocale;
import ma.ecommerce.abstractions.reactive.AbstractReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserLocaleRepository extends AbstractReactiveCrudRepository<UserLocale, Long> {

}
