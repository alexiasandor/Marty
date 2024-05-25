package com.example.martybackend.service;

import com.example.martybackend.dtos.ProductDto;
import com.example.martybackend.dtos.mappers.ProductMapper;
import com.example.martybackend.entity.Product;
import com.example.martybackend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.martybackend.constants.ProductConstants.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method creates a product
     * @param productDto the product which has dto type
     * @return return success messages or error messages
     */
    public String insert(ProductDto productDto){
        try {
            LOGGER.info(START_PROCESSING);
            Product product = ProductMapper.fromDto(productDto);
            product = productRepository.save(product);
            LOGGER.info(PRODUCT_INSERTED_SUCCESSFULLY);
            return ("Product added successfully!");
        }
        catch (Exception e){
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * This method returns all products from database.
     * @return a list of products
     */
    public List<ProductDto> findProduct(){
        try {
            LOGGER.info(START_PROCESSING);
            List<Product> productList = productRepository.findAll();
            LOGGER.info("Products list:" + productList.stream().map(ProductMapper::toEntity).collect(Collectors.toList()));
            return productList.stream().map(ProductMapper::toEntity).collect(Collectors.toList());
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     This method returns only one product.
     * @param productId the product's id which we use to search
     * @return the product which has id = productId
     */
    public ProductDto findProductById(Long productId)
    {   try{
        LOGGER.info(START_PROCESSING);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        LOGGER.info("Product: "+ optionalProduct.get() );
        return ProductMapper.toEntity(optionalProduct.get());
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * This method updates a product
     * @param productDto the product with the updated information
     * @return returns success message and updated order
     */
    public String update (ProductDto productDto){
        try{
            LOGGER.info(START_PROCESSING);
            Product existingProduct = productRepository.findById(productDto.getProductId()).get();

            existingProduct.setProductName(productDto.getProductName()) ;
            existingProduct.setPrice(productDto.getPrice()); ;
            existingProduct.setDescription(productDto.getDescription());

            Product updatedProduct =  productRepository.save(existingProduct);
            LOGGER.info("Product" + ProductMapper.toEntity(updatedProduct)+"successfully updated!");
            return ("Product" + ProductMapper.toEntity(updatedProduct)+"successfully updated!");}
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     This method deletes a product
     *
     * @param productId product's id we want to delete
     * @return success message
     */
    public String delete(Long productId){
        try {
            productRepository.deleteById(productId);

            return (PRODUCT_DELETED_SUCCESSFULLY);
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(PRODUCT_NOT_DELETED_SUCCESSFULLY);
        }
    }


}