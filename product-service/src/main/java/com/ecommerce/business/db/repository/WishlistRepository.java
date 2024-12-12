package com.ecommerce.business.db.repository;

import com.ecommerce.business.db.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}