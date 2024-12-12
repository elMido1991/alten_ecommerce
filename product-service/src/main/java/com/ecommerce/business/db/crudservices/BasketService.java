package com.ecommerce.business.db.crudservices;

import com.ecommerce.business.db.entity.Basket;
import com.ecommerce.business.db.entity.InventoryStatus;
import com.ecommerce.business.db.entity.Product;
import com.ecommerce.business.db.repository.BasketRepository;
import com.ecommerce.business.db.repository.ProductRepository;
import com.ecommerce.business.exceptions.EntityNotFoundException;
import com.ecommerce.business.exceptions.ExceptionCodes;
import com.ecommerce.business.exceptions.ProductOutOfStockException;
import com.ecommerce.gui.dto.BasketItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    /**
     * add Product To Basket
     */
    @Transactional
    public void addProductToBasket(BasketItem basketItem) {
        Basket basket = basketRepository.findBasketByUserId(basketItem.getUserId()).orElseGet(() -> {
            Basket newBasket = new Basket();
            newBasket.setUserId(basketItem.getUserId());
            return newBasket;
        });
        Product product = productRepository.findById(basketItem.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.PRODUCT_NOT_FOUND));


        if (product.getInventoryStatus() == InventoryStatus.OUTOFSTOCK || product.getQuantity() < basketItem.getQuantity()) {
            throw new ProductOutOfStockException(ExceptionCodes.PRODUCT_STOCK_BELOW_DEMANDED_QUANTITY);
        }

        basket.addProduct(product, basketItem.getQuantity());
        basketRepository.save(basket);
    }
}
