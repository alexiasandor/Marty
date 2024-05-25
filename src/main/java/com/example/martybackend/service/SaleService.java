package com.example.martybackend.service;


import com.example.martybackend.dtos.SaleDto;
import com.example.martybackend.dtos.mappers.SaleMapper;
import com.example.martybackend.entity.Sale;
import com.example.martybackend.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.martybackend.constants.SaleConstants.*;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleRepository.class);
    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * This method creates a sale
     * @param saleDto the sale which has dto type
     * @return return success messages or error messages
     */
    public String insert(SaleDto saleDto){
        try{
            LOGGER.info(START_PROCESSING);
            Sale sale = SaleMapper.fromDto(saleDto);
            sale = saleRepository.save(sale);
            LOGGER.info(SALE_INSERTED_SUCCESSFULLY);
            return(SALE_INSERTED_SUCCESSFULLY);}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(SALE_NOT_INSERTED_SUCCESSFULLY);
        }
    }

    /**
     * This method returns all sales from database.
     * @return a list of products
     */
    public List<SaleDto> findAllSales(){
        try{
            LOGGER.info(START_PROCESSING);
            List<Sale> saleList = saleRepository.findAll();
            LOGGER.info(SALE_FOUND+ saleList.stream().map(SaleMapper::toEntity).collect(Collectors.toList()));
            return saleList.stream().map(SaleMapper::toEntity).collect(Collectors.toList());}
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     This method returns only one sale.
     * @param saleId the product's id which we use to search
     * @return the sale which has id = saleId
     */
    public SaleDto findSaleById(Long saleId)
    {
        try {
            LOGGER.info(START_PROCESSING);
            Optional<Sale> optionalSale = saleRepository.findById(saleId);
            LOGGER.info(SALE_FOUND + optionalSale.get());
            return SaleMapper.toEntity(optionalSale.get());
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     This method updates a sale
     * @param saleDto the sale with the updated information
     * @return returns success message and updated sale
     */
    public String update (SaleDto saleDto){
        try {
            LOGGER.info(START_PROCESSING);
            Sale existingSale = saleRepository.findById(saleDto.getSaleId()).get();

            existingSale.setSaleName(saleDto.getSaleName());
            existingSale.setPercent(saleDto.getPercent());
            Sale updatedSale = saleRepository.save(existingSale);
            LOGGER.info("Reducere" + SaleMapper.toEntity(updatedSale) + "updatat cu succes!");
            return ("Sale" + SaleMapper.toEntity(updatedSale) + "updated successfully!");
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(SALE_NOT_UPDATED);
        }
        }

    /**
     This method deletes a sale
     * @param saleId sale's id we want to delete
     * @return success message
     */
    public String delete(Long saleId){
        try {
            LOGGER.info(START_PROCESSING);
            saleRepository.deleteById(saleId);
            LOGGER.info(SALE_UPDATED_SUCCESSFULLY);
            return (SALE_UPDATED_SUCCESSFULLY);
        }
        catch(Exception e){
            LOGGER.error(e.getMessage());
            return(SALE_NOT_DELETED);
        }
    }
}
