package com.example.martybackend.dtos.mappers;

import com.example.martybackend.dtos.ProductDto;
import com.example.martybackend.entity.Product;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductMapper {

    public static Product fromDto(ProductDto productDto){
        return Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .build();
    }

    public static ProductDto toEntity(Product product){
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
