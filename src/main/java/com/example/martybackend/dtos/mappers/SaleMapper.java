package com.example.martybackend.dtos.mappers;

import com.example.martybackend.dtos.SaleDto;
import com.example.martybackend.entity.Sale;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SaleMapper {

    public static Sale fromDto(SaleDto saleDto){

        return Sale.builder()
                .saleId(saleDto.getSaleId())
                .saleName(saleDto.getSaleName())
                .percent(saleDto.getPercent())
                .ordersList(saleDto.getOrdersList())
                .build();

    }

    public static SaleDto toEntity(Sale sale){
        return SaleDto.builder()
                .saleId(sale.getSaleId())
                .saleName(sale.getSaleName())
                .percent(sale.getPercent())
                .ordersList(sale.getOrdersList())
                .build();
    }
}
