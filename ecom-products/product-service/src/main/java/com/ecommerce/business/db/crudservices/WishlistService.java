package com.ecommerce.business.db.crudservices;

import com.ecommerce.business.db.entity.Product;
import com.ecommerce.business.db.entity.Wishlist;
import com.ecommerce.business.db.repository.ProductRepository;
import com.ecommerce.business.db.repository.WishlistRepository;
import com.ecommerce.business.exceptions.EntityNotFoundException;
import com.ecommerce.business.exceptions.ExceptionCodes;
import com.ecommerce.business.exceptions.WishListNotBelongToUserException;
import com.ecommerce.gui.dto.WishListItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    /**
     * add Product From Wishlist
     */
    @Transactional
    public void addProductToWishlist(WishListItem wishListItem) {
        Wishlist wishlist = wishlistRepository.findById(wishListItem.getWishListId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.WISHLIST_NOT_FOUND));
        if (!Objects.equals(wishlist.getUserId(), wishListItem.getUserId())) {
            throw new WishListNotBelongToUserException(ExceptionCodes.USER_DOESNT_HAVE_WISH_LIST);
        }

        Product product = productRepository.findById(wishListItem.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.PRODUCT_NOT_FOUND));
        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }

    /**
     * remove Product From Wishlist
     */
    @Transactional
    public void removeProductFromWishlist(WishListItem wishListItem) {
        Wishlist wishlist = wishlistRepository.findById(wishListItem.getWishListId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.WISHLIST_NOT_FOUND));
        if (!wishlist.getUserId().equals(wishListItem.getUserId())) {
            throw new WishListNotBelongToUserException(ExceptionCodes.USER_DOESNT_HAVE_WISH_LIST);
        }

        Product product = productRepository
                .findById(wishListItem.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.PRODUCT_NOT_FOUND));
        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }
}
