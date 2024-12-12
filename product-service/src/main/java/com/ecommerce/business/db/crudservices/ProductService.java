package com.ecommerce.business.db.crudservices;


import com.ecommerce.business.db.entity.Product;
import com.ecommerce.business.db.repository.ProductRepository;
import com.ecommerce.business.exceptions.ProductNotFoundException;
import com.ecommerce.business.exceptions.ProductPriceNegativeException;
import com.ecommerce.business.exceptions.ProductQuantityNegativeException;
import com.ecommerce.business.exceptions.ProductRatingOutOfRangeException;
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
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
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
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Validate product data
     */
    private void validateProduct(Product product) {
        if (product.getPrice() != null && product.getPrice() < 0) {
            throw new ProductPriceNegativeException("Price cannot be negative");
        }
        if (product.getQuantity() != null && product.getQuantity() < 0) {
            throw new ProductQuantityNegativeException("Quantity cannot be negative");
        }
        if (product.getRating() != null && (product.getRating() < 0 || product.getRating() > 5)) {
            throw new ProductRatingOutOfRangeException("Rating cannot be under 0 or above 5");
        }
    }

}