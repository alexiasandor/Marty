package com.example.martybackend.validators;


import com.example.martybackend.dtos.ProductDto;
import com.example.martybackend.entity.Product;
import com.example.martybackend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.martybackend.constants.ProductConstants.*;

@Component
public class ProductValidator {
    private static ProductRepository productRepository;

    @Autowired
    public ProductValidator(ProductRepository productRepository) {
        ProductValidator.productRepository = productRepository;
    }
    public static String validateProductDto(ProductDto productDto) {
        StringBuilder errors = new StringBuilder();

        // product name validation
        if (Validator.isNullOrEmpty(productDto.getProductName())) {
            errors.append( NAME_CAN_NOT_BE_EMPTY);
        }


        // price validation

        if(productDto.getPrice() <=0){
            errors.append(PRICE_MUST_BE_POSITIVE);
        }

        // description validation
        if(Validator.isNullOrEmpty(productDto.getDescription())){
            errors.append(DESCRIPTION_MUST_BE_POSITIVE);
        }

        return errors.toString();
    }
    public static String validateIdDto( Long productId) {
        StringBuilder errors = new StringBuilder();
        // id validation
        if (productId <= 0) {
            errors.append(ID_ERROR);
        }

        Optional<Product> existingProduct = productRepository.findById(productId);
        if (!existingProduct.isPresent()) {
            errors.append(USER_ID_DOES_NOT_EXIST);
        }
        return errors.toString();
    }
}
