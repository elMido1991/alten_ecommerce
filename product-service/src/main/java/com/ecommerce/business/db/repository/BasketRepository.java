package com.ecommerce.business.db.repository;

import com.ecommerce.business.db.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {


    Optional<Basket> findBasketByUserId(Long userId);
}