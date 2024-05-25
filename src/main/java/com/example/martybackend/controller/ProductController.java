package com.example.martybackend.controller;

import com.example.martybackend.dtos.ProductDto;
import com.example.martybackend.repository.ProductRepository;
import com.example.martybackend.service.ProductService;
import com.example.martybackend.validators.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.martybackend.constants.ProductConstants.*;
import static com.example.martybackend.constants.SaleConstants.INVALID_INPUT_FIELDS;
import static com.example.martybackend.constants.SaleConstants.START_PROCESSING;

@Controller
@CrossOrigin
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * This method returns all products from database.
     * @return a list of products
     */
    @GetMapping("/getAllProducts")
    public ModelAndView getProduct() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try{
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct();
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:"+ dtos);
        return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * This method returns all products from database.
     * @return a list of products
     */
    @GetMapping("/getAllProductsForCsv")
    public ModelAndView getProducts() {
        List<ProductDto> dtos = productService.findProduct();
        ModelAndView modelAndView = new ModelAndView("generateCsv");
        modelAndView.addObject("products", dtos);
        return modelAndView;
    }
    /**
     * This method returns all products from database.
     * @return a list of products
     */
    @GetMapping("/getAllProductsForMenu")
    public ModelAndView getProductsMenu() {
        List<ProductDto> dtos = productService.findProduct();
        ModelAndView modelAndView = new ModelAndView("addOrder");
        modelAndView.addObject("products", dtos);
        return modelAndView;
    }

    /**
     * This method returns only one product.
     * @param productId the product's id which we use to search
     * @return the product which has id = productId
     */
    @PostMapping("/getProductById/{id}")
    public ModelAndView getProductById(@RequestParam("id") Long productId) {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try{
        String validationErrors = ProductValidator.validateIdDto(productId);
        if (!validationErrors.isEmpty()) {

            modelAndView.addObject("errors", validationErrors);
            return modelAndView;
        }
        ProductDto product = productService.findProductById(productId);

        modelAndView.addObject("product", product);
        LOGGER.info(PRODUCT_FOUND+ product.getProductName());
        return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }

    }

    /**
     * This method creates a product
     * @param productDto the product which has dto type
     * @return return success messages or error messages
     */
    @PostMapping("/do")
    public ModelAndView insertProduct(@Validated ProductDto productDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("addProduct");

        try{
            String validationErrors = ProductValidator.validateProductDto(productDto);
            LOGGER.info(START_PROCESSING);
            if (!validationErrors.isEmpty()) {
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                modelAndView.addObject("productDto", productDto); // to keep the data in fields
                return modelAndView;
            }

            productService.insert(productDto);
            redirectAttributes.addFlashAttribute("success", PRODUCT_INSERTED_SUCCESSFULLY);
            LOGGER.info(PRODUCT_INSERTED_SUCCESSFULLY);
            return new ModelAndView("redirect:/addProduct");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/addProduct");
        }

    }

    /**
     * This method updates a product
     * @param productDto the product with the updated information
     * @return returns success message and updated order
     */
    @PostMapping("/updateProduct")
    public ModelAndView updateProduct(@Validated @ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("updateProduct");
        try{
            LOGGER.info(START_PROCESSING);
            Long productId = productDto.getProductId();
            String validationErrors = ProductValidator.validateProductDto(productDto);
            String validationErrors1 = ProductValidator.validateIdDto(productId);

            if (!validationErrors.isEmpty() || !validationErrors1.isEmpty()) {
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                modelAndView.addObject("errors", validationErrors1);
                modelAndView.addObject("productDto", productDto);
                modelAndView.addObject("productId", productId);
                return modelAndView;
            }
            productService.update(productDto);
            LOGGER.info(PRODUCT_UPDATED_SUCCESSFULLY);
            redirectAttributes.addFlashAttribute("success", PRODUCT_UPDATED_SUCCESSFULLY);
            return new ModelAndView("redirect:/updateProduct");}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/updateProduct");
        }
    }

    /**
     * This method deletes a product
     *
     * @param productId product's id we want to delete
     * @return success message
     */
    @PostMapping("/deleteProduct/{id}")
    public ModelAndView deleteProduct(@RequestParam("id") Long productId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("deleteProduct");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = ProductValidator.validateIdDto(productId);
        if (!validationErrors.isEmpty()) {
            LOGGER.info(INVALID_INPUT_FIELDS);
            modelAndView.addObject("errors", validationErrors);
            return modelAndView;
        }
        productService.delete(productId);
        LOGGER.info(PRODUCT_DELETED_SUCCESSFULLY);
        redirectAttributes.addFlashAttribute("success", PRODUCT_DELETED_SUCCESSFULLY);
        return new ModelAndView("redirect:/deleteProduct");}

        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/deleteProduct");
        }
    }

    /**
     * This method return product's price
     * @param productId the product's price
     * @return returns product's price
     */
    @PostMapping("/getProductPrice/{id}")
    public ModelAndView getProductPrice(@PathVariable("id") Long productId) {
        ModelAndView modelAndView = new ModelAndView();
        ProductDto productDto = productService.findProductById(productId);
        if (productDto != null) {
            modelAndView.addObject("productPrice", productDto.getPrice());
        } else {
            modelAndView.addObject("error", PRODUCT_NOT_FOUND);
        }
        return modelAndView;
    }

    /**
     * Retrieves all products with description "Bautura".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionB")
    public ModelAndView getProductB() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Bauturi".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * Retrieves all products with description "Carne".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionC")
    public ModelAndView getProductC() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Carne".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all products with description "Vegan".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionV")
    public ModelAndView getProductV() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Vegan".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all products with description "Garnitura".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionG")
    public ModelAndView getProductG() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Garnitura".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * Retrieves all products with description "Paste".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionP")
    public ModelAndView getProductP() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Paste".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all products with description "Fructe de mare".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionF")
    public ModelAndView getProductF() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Fructe de mare".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all products with description "Supe".
     *
     * @return ModelAndView containing the view "getAllProducts" with products having description "Bautura"
     */
    @GetMapping("/getAllProductsbyDescriptionS")
    public ModelAndView getProductS() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        try {
            LOGGER.info(START_PROCESSING);
            List<ProductDto> dtos = productService.findProduct()
                    .stream()
                    .filter(productDto -> "Supe".equals(productDto.getDescription()))
                    .collect(Collectors.toList());
            modelAndView.addObject("products", dtos);
            LOGGER.info("Orders:" + dtos);
            return modelAndView;
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
}
