package com.ecommerce.gui.controllers;
 
import com.ecommerce.business.db.crudservices.ProductService;
import com.ecommerce.business.db.entity.Product;
import com.ecommerce.gui.dto.ProductDto;
import com.ecommerce.mappers.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully created",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody
            @Parameter(description = "Product to create", required = true)
            ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all available products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of products",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productMapper.toDtos(productService.getAllProducts()));
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Retrieves a specific product using its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved product",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable
            @Parameter(description = "ID of the product to retrieve", required = true)
            Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with the provided information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully updated",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable
            @Parameter(description = "ID of the product to update", required = true)
            Long id,
            @RequestBody
            @Parameter(description = "Updated product information", required = true)
            ProductDto productDto) {
        Product product = productService.getProduct(id);
        productMapper.refreshEntity(productDto, product);
        product = productService.updateProduct(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes a product using its ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable
            @Parameter(description = "ID of the product to delete", required = true)
            Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}