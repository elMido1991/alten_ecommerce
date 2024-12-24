package com.ecommerce.business.db.crudservices;


import com.ecommerce.business.db.entity.Product;
import com.ecommerce.business.db.repository.ProductRepository;
import com.ecommerce.business.exceptions.*;
import com.ecommerce.gui.dto.BasketItem;
import com.ecommerce.gui.dto.WishListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Create a new product
     */
    @Transactional
    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    /**
     * Retrieve all products
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get a product by its ID
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCodes.PRODUCT_NOT_FOUND));
    }

    /**
     * Update a product
     */
    @Transactional
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Delete a product
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionCodes.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    /**
     * Validate product data
     */
    private void validateProduct(Product product) {
        if (product.getPrice() != null && product.getPrice() < 0) {
            throw new ProductPriceNegativeException(ExceptionCodes.PRICE_CANNOT_BE_NEGATIVE);
        }
        if (product.getQuantity() != null && product.getQuantity() < 0) {
            throw new ProductQuantityNegativeException(ExceptionCodes.QUANTITY_CANNOT_BE_NEGATIVE);
        }
        if (product.getRating() != null && (product.getRating() < 0 || product.getRating() > 5)) {
            throw new ProductRatingOutOfRangeException(ExceptionCodes.RATING_CANNOT_BE_UNDER_0_OR_ABOVE_5);
        }
    }

}