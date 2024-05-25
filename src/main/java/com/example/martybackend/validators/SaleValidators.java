package com.example.martybackend.validators;

import com.example.martybackend.dtos.SaleDto;
import com.example.martybackend.entity.Sale;
import com.example.martybackend.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.martybackend.constants.SaleConstants.*;

@Component
public class SaleValidators {
    private static SaleRepository saleRepository;

    @Autowired
    public SaleValidators(SaleRepository saleRepository) {
        SaleValidators.saleRepository = saleRepository;
    }
    public static String validateSaleDto(SaleDto saleDto) {
        StringBuilder errors = new StringBuilder();

        // sale name validation
        if (Validator.isNullOrEmpty(saleDto.getSaleName())) {
            errors.append(NAME_CAN_NOT_BE_EMPTY);
        }
        //percentage validation
        if(saleDto.getPercent() <=0){
            errors.append(PERCENT_MUST_BE_POSITIVE);
        }
        return errors.toString();
    }
    public static String validateIdDto( Long saleId) {
        StringBuilder errors = new StringBuilder();
        // id validation
        if (saleId <= 0) {
            errors.append(ID_ERROR);
        }

        Optional<Sale> existingSale = saleRepository.findById(saleId);
        if (!existingSale.isPresent()) {
            errors.append(SALE_NOT_FOUND);
        }
        return errors.toString();
    }
}
