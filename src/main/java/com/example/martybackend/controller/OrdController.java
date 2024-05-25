package com.example.martybackend.controller;


import com.example.martybackend.dtos.OrdDto;
import com.example.martybackend.entity.Product;
import com.example.martybackend.repository.OrdRepository;
import com.example.martybackend.service.OrdService;
import com.example.martybackend.validators.OrdValidators;
import com.example.martybackend.validators.UserValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static com.example.martybackend.constants.OrdConstants.*;
import static com.example.martybackend.constants.SaleConstants.INVALID_INPUT_FIELDS;

@Controller
@CrossOrigin
@RequestMapping(value = "/ord")
public class OrdController {
    private final OrdService ordService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdRepository.class);
    @Autowired
    public OrdController(OrdService ordService){
        this.ordService = ordService;

    }

    /**
     * This method returns all orders from database.
     * @return a list of orders
     */
    @GetMapping("/getAllOrds")
    public ModelAndView getOrder() {
        ModelAndView modelAndView = new ModelAndView("getAllOrds");
        List<OrdDto> ords;
        Map<OrdDto, Map<Product, Long>> listMap = new HashMap<>();
        try{
            LOGGER.info(STARTING_TO_RETRIEVE_ORDERS);
            ords = ordService.findOrd();
            for (OrdDto ord : ords) {
                Map<Product, Long> map = ordService.findOrdAndQ(ord);
                listMap.put(ord, map);
                LOGGER.info(SUCCESSFULLY_PROCESSED_ORDER);
            }
            modelAndView.addObject("ords", ords);
            modelAndView.addObject("listMap", listMap);
            return modelAndView;
        }catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all orders sorted in ascending order by price.
     *
     * @return  sorted orders and their details
     */
    @GetMapping("/getAllSortedAscOrds")
    public ModelAndView getOrders() {
        ModelAndView modelAndView = new ModelAndView("getAllOrds");
        List<OrdDto> ords;
        Map<OrdDto, Map<Product, Long>> listMap = new HashMap<>();
        try{
            LOGGER.info(STARTING_TO_RETRIEVE_ORDERS);
            ords = ordService.findOrd();

            // Sortăm comenzile în ordine crescătoare după preț
            Collections.sort(ords, Comparator.comparing(OrdDto::getPrice));

            for (OrdDto ord : ords) {
                Map<Product, Long> map = ordService.findOrdAndQ(ord);
                listMap.put(ord, map);
                LOGGER.info(SUCCESSFULLY_PROCESSED_ORDER);
            }
            modelAndView.addObject("ords", ords);
            modelAndView.addObject("listMap", listMap);
            return modelAndView;
        } catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }
    /**
     * Retrieves all orders sorted in descending order by price.
     *
     * @return  sorted orders and their details
     */
    @GetMapping("/getAllSortedDescOrds")
    public ModelAndView getOrdersDesc() {
        ModelAndView modelAndView = new ModelAndView("getAllOrds");
        List<OrdDto> ords;
        Map<OrdDto, Map<Product, Long>> listMap = new HashMap<>();
        try {
            LOGGER.info(STARTING_TO_RETRIEVE_ORDERS);
            ords = ordService.findOrd();

            // Sortăm comenzile în ordine descrescătoare după preț
            Collections.sort(ords, Comparator.comparing(OrdDto::getPrice).reversed());

            for (OrdDto ord : ords) {
                Map<Product, Long> map = ordService.findOrdAndQ(ord);
                listMap.put(ord, map);
                LOGGER.info(SUCCESSFULLY_PROCESSED_ORDER);
            }
            modelAndView.addObject("ords", ords);
            modelAndView.addObject("listMap", listMap);
            return modelAndView;
        } catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }


    /**
     * This method returns only one order
     * @param ordId the order's id which we use to search
     * @return the order which has id = ordId
     */
    @PostMapping("/getOrdById/{id}")
    public ModelAndView getOrdById(@RequestParam("id") Long ordId) {
        ModelAndView modelAndView = new ModelAndView("getAllOrds");
        // create a map
        Map<OrdDto, Map<Product, Long>> listMap = new HashMap<>();

        try{
            String validationErrors = OrdValidators.validateIdDto(ordId);
            if (!validationErrors.isEmpty()) {

                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }
                OrdDto ord = ordService.findOrdById(ordId);
                Map<Product, Long> map = ordService.findOrdAndQ(ord);
                listMap.put(ord, map);
                LOGGER.info(LOGGER_ORD + ord.getOrd_Id() +LOGGER_ORD_FOUND);
                modelAndView.addObject("ord", ord);
                modelAndView.addObject("listMap", listMap);
                return  modelAndView;
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * This method get all orders which are taken by a waiter
     * @param userId the waiter's id
     * @return all the orders
     */
    @PostMapping("/getOrdByUserId/{id}")
    public ModelAndView getOrdByUserId(@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("viewUserActivity");

        try{
            String validationErrors = UserValidators.validateIdDto(userId);

            if (!ordService.isUserWaiter(userId)) {
                modelAndView.addObject("errors", THE_USER_IS_NOT_WAITER );
                LOGGER.info(THE_USER_IS_NOT_WAITER );
                return modelAndView;
            }

            if (!validationErrors.isEmpty()) {
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }
                List<OrdDto> ord  = ordService.findOrdByUserId(userId);
                modelAndView.addObject("ords", ord);
                redirectAttributes.addFlashAttribute("success", THE_WAITER_S_ORDER_WAS_FOUND );
                LOGGER.info(THE_WAITER_S_ORDER_WAS_FOUND );
                return modelAndView;
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * This method creates an order
     * @param ordDto the order which has dto type
     * @return return success messages or error messages
     */
    @PostMapping("/do")
    public ModelAndView insertOrd(@ModelAttribute("ordDto") @Validated OrdDto ordDto, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView("addOrder");
        try{
        String validationErrors = OrdValidators.validateOrdDto(ordDto);
            if (!validationErrors.isEmpty() ) {

                modelAndView.addObject("errors", validationErrors );
                LOGGER.info(INVALID_PARAMETER);
                return modelAndView;

            }

                ordService.insert(ordDto);

                redirectAttributes.addFlashAttribute("success", ORD_INSERTED_SUCCESSFULLY);
                LOGGER.info(ORD_INSERTED_SUCCESSFULLY);
                return new ModelAndView("redirect:/sale/addOrder");
        }

        catch (Exception e ){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/sale/addOrder");
        }
    }


    /**
     * This method updates an order
     *
     * @param ordDto the order with the updated information
     * @param result the result of parameter's validation
     * @return  returns success message and updated order
     */
    @PostMapping("/updateOrd")
    public ModelAndView updateOrd(@Validated @ModelAttribute OrdDto ordDto, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("updateOrder");

        try {
            Long ordId = ordDto.getOrd_Id();
            String validationErrors = OrdValidators.validateIdDto(ordId);
            String validationErrors1 = OrdValidators.validateOrdDto(ordDto);

            if (!validationErrors.isEmpty()) {
                LOGGER.info(INVALID_PARAMETER);
                modelAndView.addObject("errors", validationErrors);
                modelAndView.addObject("ordDto", ordDto);
                modelAndView.addObject("ordId", ordId);
                return modelAndView;
            }
            else if (!validationErrors1.isEmpty()) {
                LOGGER.info(INVALID_PARAMETER);
                modelAndView.addObject("errors", validationErrors1);
                modelAndView.addObject("ordDto", ordDto);
                modelAndView.addObject("ordId", ordId);
                return modelAndView;
            }else{

            ordDto.setOrd_Id(ordId);
            ordService.update(ordDto);
            LOGGER.info(ORD_UPDATED_SUCCESSFULLY);
            modelAndView.addObject("success", ORD_UPDATED_SUCCESSFULLY);
            return modelAndView;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return modelAndView;
        }
    }


    /**
     * This method deletes an order
     * @param ordId order's id we want to delete
     * @return success message
     */
    @PostMapping("/deleteOrder/{id}")
    public ModelAndView deleteOrd(@RequestParam("id") Long ordId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("deleteOrder");
        try{
            String validationErrors = OrdValidators.validateIdDto(ordId);

            if (!validationErrors.isEmpty()) {
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }
            ordService.delete(ordId);
            redirectAttributes.addFlashAttribute("success", ORD_DELETED_SUCCESSFULLY);
            return modelAndView;
       }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/deleteOrder");
        }
    }

    /**
     * This method calculates the order's price
     * @return returns the price
     */
    @PostMapping("/calculateTotalPrice")
    public ModelAndView calculateAndUpdateTotalPrice(@RequestParam("tableNr") int tableNr,
                                                     RedirectAttributes redirectAttributes) {
        try {

            float totalPrice = ordService.calculateAndUpdateTotalPrice(tableNr);
            redirectAttributes.addFlashAttribute("success",  TOTAL_PRICE + totalPrice);
            LOGGER.info(TOTAL_PRICE + totalPrice);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errors", e.getMessage());
            LOGGER.error(e.getMessage());
        }

        return new ModelAndView("redirect:/orderPrice");
    }

    /**
     * This method adds products to order
     * @param tableNr the table number
     * @param productList the productList
     * @return returns success message
     */
    @PostMapping("/addProductsToOrd")
    public ModelAndView addProductsToOrd(@RequestParam("tableNr") int tableNr,
                                        @RequestParam List<String> productList,RedirectAttributes redirectAttributes) {

        try {
            ordService.addProductToOrd(tableNr,productList);


            redirectAttributes.addFlashAttribute("success",   PRODUCT_ADDED_TO_ORD_SUCCESSFULLY);
            LOGGER.info(PRODUCT_ADDED_TO_ORD_SUCCESSFULLY);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", ORD_NOT_FOUND);
            LOGGER.error(e.getMessage());
        }

        return new ModelAndView("redirect:/addProductsToOrder");
    }

}