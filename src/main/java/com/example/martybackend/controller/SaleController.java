package com.example.martybackend.controller;


import com.example.martybackend.dtos.SaleDto;

import com.example.martybackend.repository.SaleRepository;
import com.example.martybackend.service.SaleService;
import com.example.martybackend.validators.SaleValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.martybackend.constants.SaleConstants.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/sale")
public class SaleController {
    private final SaleService saleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleRepository.class);
    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @RequestMapping("/addOrder")
    public ModelAndView addOrd() {
        ModelAndView modelAndView = new ModelAndView("addOrder");
        modelAndView.addObject("sales", saleService.findAllSales());
        return modelAndView;
    }

    /**
     * This method returns all sales from database.
     * @return a list of products
     */
    @GetMapping("/getAllSales")
    public ModelAndView getSale(){
        ModelAndView modelAndView = new ModelAndView("getAllSales");
        try{
            LOGGER.info(START_PROCESSING);
            List<SaleDto> sales = saleService.findAllSales();
            modelAndView.addObject("sales", sales);
            LOGGER.info("Sales: "+ sales);
            return modelAndView;
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     This method returns only one sale.
     * @param saleId the product's id which we use to search
     * @return the sale which has id = saleId
     */
    @PostMapping("/getAllSales/{id}")
    public ModelAndView getSaleById(@RequestParam("id") Long saleId){
        ModelAndView modelAndView = new ModelAndView("getAllSales");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = SaleValidators.validateIdDto(saleId);
            if(!validationErrors.isEmpty()){
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                return modelAndView;
            }

            SaleDto sale = saleService.findSaleById(saleId);

            modelAndView.addObject("sale", sale);
            LOGGER.info("Sale:" +sale.getSaleName());
            return modelAndView;}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return modelAndView;
        }
    }

    /**
     * This method creates a sale
     * @param saleDto the sale which has dto type
     * @return return success messages or error messages
     */
    @PostMapping("/do")
    public ModelAndView insertSale(@Validated  SaleDto saleDto, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("addSale");
        try{
            LOGGER.info(START_PROCESSING);
            String validationErrors = SaleValidators.validateSaleDto(saleDto);
            if(!validationErrors.isEmpty()){
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);
                modelAndView.addObject("saleDto", saleDto);
                return  modelAndView;
            }

            saleService.insert(saleDto);
            LOGGER.info(SALE_INSERTED_SUCCESSFULLY + saleDto.getSaleName());
            redirectAttributes.addFlashAttribute("success", SALE_INSERTED_SUCCESSFULLY);
            return new ModelAndView("redirect:/addSale");}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/addSale");
        }
    }


    /**
         This method updates a sale
     * @param saleDto the sale with the updated information
     * @return returns success message and updated sale
     */
    @PostMapping("/updateSale")
    public ModelAndView updateSale( @ModelAttribute("userDto") SaleDto saleDto, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("updateSale");
        try{
            LOGGER.info(START_PROCESSING);
            Long saleId = saleDto.getSaleId();
            String validationErrors =  SaleValidators.validateSaleDto(saleDto);
            String validationErrors1= SaleValidators.validateIdDto(saleId);

            if(!validationErrors.isEmpty() || !validationErrors1.isEmpty()){
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors1);
                modelAndView.addObject("errors",validationErrors);
                modelAndView.addObject("saleId", saleId);
                modelAndView.addObject("saleDto", saleDto);

                return modelAndView;

            }

            saleService.update(saleDto);
            redirectAttributes.addFlashAttribute("success", SALE_UPDATED_SUCCESSFULLY);

            //saleService.update(saleDto);
            LOGGER.info("The sale" + saleDto.getSaleId() + " has been updated");
            return new ModelAndView("redirect:/updateSale");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/updateSale");
        }
    }

    /**
     * This method deletes a sale
     * @param saleId sale's id we want to delete
     * @return success message
     */
    @PostMapping("/deleteSale/{id}")
    public ModelAndView deleteSale(@RequestParam("id") Long saleId, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("deleteSale");
        try {
            LOGGER.info(START_PROCESSING);
            String validationErrors = SaleValidators.validateIdDto(saleId);
            if (!validationErrors.isEmpty()) {
                LOGGER.info(INVALID_INPUT_FIELDS);
                modelAndView.addObject("errors", validationErrors);

                return modelAndView;
            }
            saleService.delete(saleId);
            LOGGER.info(SALE_DELETED_SUCCESSFULLY);
            redirectAttributes.addFlashAttribute("success", SALE_DELETED_SUCCESSFULLY);

            return new ModelAndView("redirect:/deleteSale");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return new ModelAndView("redirect:/deleteSale");
        }
    }

}
